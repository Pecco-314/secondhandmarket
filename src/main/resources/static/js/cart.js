let myCartForm = new Vue({
    el: '#myCart',
    data: {
        carts: [],
        countItem: 0,
        page: 1,
        dialogVisibleForCancel: false,
        dialogVisibleForCart: false,
        dialogVisibleForFALSE: false,
        cnt: 1,
        currentId: '',
        cntSuccess: 0,
        loading: false,
    },
    computed: {
        countCart() {
            return pageHeader.countCart;
        }
    },
    methods: {
        returnToShop() {
            window.open('../shop', '_self');
        },
        checkout() {
            window.open('../checkout?type=cart', '_self')
        },
        addToCart() {
            let purchaseData = {
                userID: parseInt($.cookie('id')),
                token: $.cookie('token'),
                itemID: this.currentId,
                quantity: this.cnt,
                accumulate: false,
            };
            addToCart(purchaseData, response => {
                if (response.status === 60200) {
                    this.dialogVisibleForCart = false
                    this.$message({
                        message: '加入购物车成功',
                        type: 'success'
                    });
                    this.getCartList();
                } else {
                    this.$message.error('加入失败');
                }
            })
        },
        getCartList() {
            this.cntSuccess = 0;
            getCartList(this.page, async response => {
                this.carts = response.data;
                this.loading = true;
                let promises = [];
                for (let i = 0; i < this.carts.length; i++) {
                    promises.push(handleItemInfo(this, i));
                }
                await Promise.all(promises);
                if (this.cntSuccess === this.carts.length) {
                    this.loading = false;
                }
            });
        },
        openCartDialog(itemID) {
            this.cnt = 1;
            this.currentId = itemID;
            this.dialogVisibleForCart = true;
        },
        openCancelDialog(itemID) {
            this.currentId = itemID;
            this.dialogVisibleForCancel = true;
        },
        changeQuantity(itemID, quantity) {
            let identification = {
                userID: $.cookie("id"),
                token: $.cookie("token"),

                itemID: itemID,
                quantity: quantity,
                accumulate: false,
            };
            addToCart(identification, response => {
                if (quantity != 0) {
                    for (let i = 0; i < this.carts.length; i++) {
                        if (this.carts[i].itemId === itemID) {
                            this.carts[i].total = this.carts[i].price * quantity;
                            break;
                        }
                    }
                } else {
                    if (this.carts.length == 1 && this.page != 1) {
                        this.page--;
                    }
                    this.getCartList();
                    pageHeader.updateCart();
                }
            })

        },
        removeCartItem() {
            this.changeQuantity(this.currentId, 0);
            this.dialogVisibleForCancel = false;
        }
    }
})

async function handleItemInfo(th, i) {
    await getItemInfoById(th.carts[i].itemId, response => {
        th.$set(th.carts[i], 'max', response.data.quantity);
        th.$set(th.carts[i], 'itemName', response.data.name);
        th.$set(th.carts[i], 'price', response.data.price);
        th.$set(th.carts[i], 'checkCondition', response.data.checkCondition);
        if (response.data.coverPath === null)
            th.$set(th.carts[i], 'imageUrl', `../img/null2.png`);
        else
            th.$set(th.carts[i], 'imageUrl', `http://1.15.220.157:8088/requests/image/${response.data.coverPath}`);
        th.$set(th.carts[i], 'url', `${url}/item?id=${th.carts[i].itemId}`);
        th.$set(th.carts[i], 'total', th.carts[i].price * th.carts[i].quantity);

        th.cntSuccess++;
    })
}

$(myCartForm.getCartList);