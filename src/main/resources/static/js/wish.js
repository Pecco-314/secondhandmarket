let wishList = new Vue({
    el: '#myWishes',
    data: {
        wishes: [],
        dialogVisibleForCart: false,
        dialogVisibleForCancelCollection: false,
        cnt: 1,
        max: 1,
        currentId: '',
    },
    methods: {
        openCartDialog(wish) {
            this.dialogVisibleForCart = true;
            this.currentId = wish.itemId;
            this.max = wish.quantity;
        },

        openCancelCollectionDialog(wish) {
            this.dialogVisibleForCancelCollection = true;
            this.currentId = wish.itemId;
        },

        addToCart() {
            let purchaseData = {
                userID: parseInt($.cookie('id')),
                token: $.cookie('token'),
                itemID: this.currentId,
                quantity: this.cnt
            };
            console.log(purchaseData);
            $.ajax({
                url: `${url}/requests/cart/modifyCart`,
                method: 'post',
                data: JSON.stringify(purchaseData),
                contentType: "application/json;charset=utf-8",
                success: (responseStr) => {
                    let response = JSON.parse(responseStr);
                    if (response.status === 60200) {
                        this.dialogVisibleForCart = false
                        this.$message({
                            message: '加入购物车成功',
                            type: 'success'
                        });
                        callback(response);
                    } else {
                        this.$message.error('操作失败');
                    }
                }
            })
        },

        cancelCollection() {
            modifyCollection(this,this.currentId, false, response => {
                this.dialogVisibleForCancelCollection = false;
                this.updateCollectionState();
            });
        },

        updateCollectionState() {
            for (let i = 0; i < this.wishes.length; i++) {
                if (this.wishes[i].itemId === this.currentId) {
                    this.wishes.splice(i, 1);
                }
            }
        },

        getWishList() {
            let identification = {
                userID: $.cookie("id"),
                token: $.cookie("token"),
            };
            $.ajax({
                url: `${url}/requests/user/wishlist`,
                method: 'post',
                data: JSON.stringify(identification),
                contentType: "application/json;charset=utf-8",
                success: (responseStr) => {
                    let response = JSON.parse(responseStr);
                    if (response.status === 10200) {
                        this.wishes = response.data;
                        for (let i = 0; i < this.wishes.length; i++) {
                            getItemInfo(this.wishes[i].itemId, response => {
                                this.$set(this.wishes[i], 'name', response.data.name);
                                this.$set(this.wishes[i], 'quantity', response.data.quantity);
                                this.$set(this.wishes[i], 'price', response.data.price);
                                if (response.data.coverPath === null)
                                    this.$set(this.wishes[i], 'imageUrl', `../img/null2.png`);
                                else
                                this.$set(this.wishes[i], 'imageUrl', `http://1.15.220.157:8088/requests/image/${response.data.coverPath}`);
                                this.$set(this.wishes[i], 'url', `${url}/item?id=${this.wishes[i].itemId}`);
                            })
                        }
                    }
                }
            })
        }
    }
})

$(wishList.getWishList);