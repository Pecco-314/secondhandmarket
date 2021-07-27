function switchToTab(index) {
    let tab = $(".checkout-tab");
    tab.find('ul.tabs > li').removeClass('current');
    $(`#tab-link-${index}`).addClass('current');
    tab.find('.tab_content').find('div.tabs_item').not('div.tabs_item:eq(' + index + ')').slideUp();
    tab.find('.tab_content').find('div.tabs_item:eq(' + index + ')').slideDown();
}

let checkoutForm = new Vue({
    el: "#checkout-form",
    methods: {
        onCompleteOrderInfo() {
            checkoutForm.$refs.form.validate(valid => {
                if (valid) {
                    switchToTab(1);
                }
            });
        },
        validateField(field) {
            checkoutForm.$refs.form.validateField(field);
        },
    },
    data: {
        form: {
            receiverName: "",
            phoneNumber: "",
            campus: "翔安校区",
            dorm: "",
            detailedAddress: "",
        },
        rules: {
            receiverName: [{required: true, message: "请输入收货人姓名"}],
            phoneNumber: [{
                validator: (rule, value, callback) => {
                    if (isPossiblyPhoneNumber(value)) {
                        callback();
                    } else {
                        callback(new Error('请输入正确的电话号码'));
                    }
                }
            }],
            dorm: [{required: true, message: "请输入公寓园区"}],
        },
        options: [
            '翔安校区',
            '思明校区',
        ],
        id: getURLVariable('id'),
        cntSuccess: 0,
    },
});

Vue.component('checkout-item', {
    props: ["itemId", "quantity"],
    methods: {
        setCheckoutItemData(id) {
            getItemInfo(id, response => {
                this.imagePath = response.data.itemImages[0];
                this.name = response.data.name;
                this.price = response.data.price;
                checkoutConfirm.updateTotalPrice();
            })
        }
    },
    mounted() {
        this.setCheckoutItemData(this.itemId);
    },
    data() {
        return {
            imagePath: '',
            name: '',
            price: 0,
        }
    },
    computed: {
        totalPrice() {
            return this.price * this.quantity;
        },
        imageSrc() {
            console.log(this.imagePath)
            if (this.imagePath === undefined)
                return `../img/null2.png`;
            else
                return `http://1.15.220.157:8088/requests/image/${this.imagePath}`
        }
    },
    template: `
    <li>
        <img :src="imageSrc" alt="Images">
        <h3>{{name}}</h3>
        <span>￥{{price}}</span>
        <span class="quantity-tag">x{{quantity}}</span>
        <div class="price-tag">￥{{totalPrice}}</div>
    </li>
`
})

let checkoutConfirm = new Vue({
    el: '#checkout-confirm',
    data() {
        return {
            totalPrice: 0,
            ids: null,
            isConfirming: false,
            orders: [],
        }
    },
    mounted() {
        this.updateIds();
    },
    methods: {
        switchToTab: switchToTab,
        updateTotalPrice() {
            checkoutConfirm.totalPrice = 0;
            for (let i = 0; i < checkoutConfirm.ids.length; ++i) {
                checkoutConfirm.totalPrice += checkoutConfirm.$refs[i][0].totalPrice;
            }
        },
        updateIds() {
            let type = getURLVariable('type');
            console.log(type);
            if (type === 'single') {
                this.ids = [{
                    id: getURLVariable('id'),
                    quantity: getURLVariable('cnt'),
                    index: 0
                }];
            } else if (type === 'cart') {
                getCartList(response => {
                    let res = [];
                    for (let i = 0; i < response.data.length; ++i) {
                        res.push({
                            id: response.data[i].itemId,
                            quantity: response.data[i].quantity,
                            index: i,
                        });
                    }
                    this.ids = res;
                })
            } else if (type === 'by_id') {
                let orderFilter = {
                    orderId: getURLVariable('order'),
                    buyer: null,
                    seller: null,
                    itemId: null,
                    state: null,
                };
                return $.ajax({
                    url: `${url}/requests/user/orderList/search`,
                    method: 'post',
                    data: JSON.stringify(orderFilter),
                    contentType: "application/json;charset=utf-8",
                    success: (responseStr) => {
                        let response = JSON.parse(responseStr);
                        if (response.status === 40200) {
                            let order = response.data[0];
                            this.ids = [{
                                id: order.item,
                                quantity: order.quantity,
                                index: 0
                            }];
                            checkoutForm.form.receiverName = order.receiverName;
                            checkoutForm.form.phoneNumber = order.phoneNumber;
                            checkoutForm.form.campus = order.campus;
                            checkoutForm.form.dorm = order.dorm;
                            checkoutForm.form.detailedAddress = order.detailedAddress;
                            checkoutConfirm.orders.push(order.id);
                            switchToTab(2);
                        } else {
                            alert(`${response.message}（状态码：${response.status}）`);
                        }
                    }
                })
            }
        },
        onConfirm() {
             checkoutForm.$refs.form.validate(async valid => {
                if (valid) {
                    checkoutForm.cntSuccess = 0;
                    if (!this.isConfirming) {
                        this.isConfirming = true;
                        let promises = [];
                        this.orders = [];
                        for (let i = 0; i < checkoutConfirm.ids.length; ++i) {
                            promises.push(handleCheckoutItem(checkoutConfirm.$refs[i][0], this.orders));
                        }
                        await Promise.all(promises);
                        this.isConfirming = false;
                        if (checkoutForm.cntSuccess === checkoutConfirm.ids.length) {
                            clearCart(() => {
                            });
                            elAlert(this, '订单已提交！', '', () => {
                                switchToTab(2);
                            })
                        } else {
                            elAlert(this, `交易中发生异常（${checkoutForm.cntSuccess}项成功，${checkoutConfirm.ids.length - checkoutForm.cntSuccess}项失败）`, '', () => {
                            });
                        }
                    }
                } else {
                    switchToTab(0);
                }
            })
        },
    }
})

async function handleCheckoutItem(checkoutItem, orders) {
    let orderData = {
        buyer: $.cookie("id"),
        token: $.cookie("token"),

        receiverName: checkoutForm.form.receiverName,
        phoneNumber: checkoutForm.form.phoneNumber,
        campus: checkoutForm.form.campus,
        dorm: checkoutForm.form.dorm,
        detailedAddress: checkoutForm.form.detailedAddress,
        itemID: checkoutItem.itemId,

        quantity: checkoutItem.quantity
    }
    return $.ajax({
        url: `${url}/requests/user/insertOrder`,
        method: 'post',
        data: JSON.stringify(orderData),
        contentType: "application/json;charset=utf-8",
        success: (responseStr) => {
            let response = JSON.parse(responseStr);
            console.log(response);
            if (response.status === 40200) {
                checkoutForm.cntSuccess++;
                orders.push(response.data);
            } else {
                alert(`${response.message}（状态码：${response.status}）`);
            }
        }
    })
}

let paidButton = new Vue({
    el: '#paid-button',
    methods: {
        async onPay() {
            let promises = [];
            for (const order of checkoutConfirm.orders) {
                promises.push(changeOrderState(order, 'UNDELIVERED'));
            }
            console.log(checkoutConfirm.orders);
            await Promise.all(promises);
            elAlert(this, '已确认付款！', '', () => {
                window.open('../', '_self');
            });
        }
    }
});

$(() => {
        getUserInfo((response) => {
            checkoutForm.form.phoneNumber = response.data.phoneNumber;
        })

    }
);