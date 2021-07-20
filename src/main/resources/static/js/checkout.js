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
            }
        },
    }
})

async function handleCheckoutItem(checkoutItem) {
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
    $.ajax({
        url: `${url}/requests/user/insertOrder`,
        method: 'post',
        data: JSON.stringify(orderData),
        contentType: "application/json;charset=utf-8",
        success: (responseStr) => {
            let response = JSON.parse(responseStr);
            if (response.status === 40200) {

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
            for (let i = 0; i < checkoutConfirm.ids.length; ++i) {
                await handleCheckoutItem(checkoutConfirm.$refs[i][0]);
            }
            alert('交易成功！');
            window.open("../", "_self");
        }
    }
})

$(() => {
        getUserInfo((response) => {
            checkoutForm.form.phoneNumber = response.data.phoneNumber;
        })

    }
);