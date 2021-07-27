let MyCartForm = new Vue({
    el: '#myCart',
    data: {
        carts: [],
        dialogVisibleForCancel: false,
        currentId: '',
    },
    methods: {
        returnToShop() {
            window.open('../shop', '_self');
        },
        checkout() {
            window.open('../checkout?type=cart', '_self')
        },
        getCartList() {
            getCartList(response => {
                this.carts = response.data;
                for (let i = 0; i < this.carts.length; i++) {
                    getItemInfo(this.carts[i].itemId, response => {
                        this.$set(this.carts[i], 'max', response.data.quantity);
                        this.$set(this.carts[i], 'itemName', response.data.name);
                        this.$set(this.carts[i], 'price', response.data.price);
                        if (response.data.coverPath === null)
                            this.$set(this.carts[i], 'imageUrl', `../img/null2.png`);
                        else
                            this.$set(this.carts[i], 'imageUrl', `http://1.15.220.157:8088/requests/image/${response.data.coverPath}`);
                        this.$set(this.carts[i], 'url', `${url}/item?id=${this.carts[i].itemId}`);
                        this.$set(this.carts[i], 'total', this.carts[i].price * this.carts[i].quantity);
                        // this.carts[i].max = response.data.quantity;
                        // this.carts[i].itemName = response.data.name;
                        // this.carts[i].price = response.data.price;
                        // this.carts[i].imageUrl = `http://1.15.220.157:8088/requests/image/${response.data.coverPath}`;
                        // this.carts[i].url = `${url}/item?id=${this.carts[i].itemId}`;
                        // this.carts[i].total = this.carts[i].price * this.carts[i].quantity;
                    })
                }
            });
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
                quantity: quantity
            };
            console.log(identification);
            $.ajax({
                url: `${url}/requests/cart/modifyCart`,
                method: 'post',
                data: JSON.stringify(identification),
                contentType: "application/json;charset=utf-8",
                success: (responseStr) => {
                    let response = JSON.parse(responseStr);
                    if (response.status === 60200) {
                        if (this.carts)
                            for (let i = 0; i < this.carts.length; i++) {
                                if (this.carts[i].itemId === itemID) {
                                    this.carts[i].total = this.carts[i].price * quantity;
                                    break;
                                }
                            }
                    }
                }
            })
        },
        removeCartItem() {
            this.changeQuantity(this.currentId, 0);
            this.dialogVisibleForCancel = false;
            for (let i = 0; i < this.carts.length; i++) {
                if (this.carts[i].itemId === this.currentId) {
                    this.carts.splice(i, 1);
                    break;
                }
            }
        }
    }
})

$(MyCartForm.getCartList());