let wishList = new Vue({
    el: '#myWishes',
    data: {
        wishes: [],
        dialogVisibleForCart: false,
        dialogVisibleForCollection: false,
        dialogVisibleForCancelCollection: false,
        cnt: 1,
        max: 1,
        currentId: '',
    },
    methods: {
        openCartDialog(item) {
            this.dialogVisibleForCart = true;
            this.currentId = item.id;
            this.max = item.quantity;
        },

        openCollectionDialog(item) {
            this.dialogVisibleForCollection = true;
            this.currentId = item.id;
        },

        openCancelCollectionDialog(item) {
            this.dialogVisibleForCancelCollection = true;
            this.currentId = item.id;
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
                    // elAlert(this, response.message, '', () => {
                    // });
                    if (response.status === 60200) {
                        this.dialogVisibleForCart = false;
                        confirm("加入购物车成功");
                    }
                }
            })
        },

        addToCollection() {
            let data = {
                userID: parseInt($.cookie('id')),
                token: $.cookie('token'),
                itemID: this.currentId,
                isAdding: true,
            };
            $.ajax({
                url: `${url}/requests/user/wishlist/modify`,
                method: 'post',
                data: JSON.stringify(data),
                contentType: "application/json;charset=utf-8",
                success: (responseStr) => {
                    let response = JSON.parse(responseStr);
                    if (response.status === 10200) {
                        this.dialogVisibleForCollection = false;
                        this.updateCollectionState();
                        confirm("收藏成功");
                    } else {
                        alert("收藏失败");
                    }
                }
            })
        },

        cancelCollection() {
            let data = {
                userID: parseInt($.cookie('id')),
                token: $.cookie('token'),
                itemID: this.currentId,
                isAdding: false,
            };
            $.ajax({
                url: `${url}/requests/user/wishlist/modify`,
                method: 'post',
                data: JSON.stringify(data),
                contentType: "application/json;charset=utf-8",
                success: (responseStr) => {
                    let response = JSON.parse(responseStr);
                    if (response.status === 10200) {
                        this.dialogVisibleForCancelCollection = false;
                        this.updateCollectionState();
                        confirm("取消成功");
                    } else {
                        alert("取消失败");
                    }
                }
            })
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
                                this.$set(this.wishes[i], 'price', response.data.price);
                                this.$set(this.wishes[i], 'imageUrl', `http://1.15.220.157:8088/requests/image/${response.data.coverPath}`);
                                this.$set(this.wishes[i], 'url', `${url}/item?id=${this.carts[i].itemId}`);
                            })
                        }
                    }
                }
            })
        }
    }
})

$(wishList.getWishList);