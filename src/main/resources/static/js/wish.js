let wishList = new Vue({
    el: '#myWishes',
    data: {
        wishes: [],
        page: 1,
        dialogVisibleForCart: false,
        dialogVisibleForCancelCollection: false,
        dialogVisibleForFALSE: false,
        dialogVisibleForZero: false,
        cnt: 1,
        max: 1,
        currentId: '',
        loading: false,
        cntSuccess: 0,
        countWish: 0,
    },
    mounted() {
        let identification = {
            userID: $.cookie("id"),
            token: $.cookie("token"),
        };
        $.ajax({
            url: `${url}/requests/wishlist/count`,
            method: 'post',
            data: JSON.stringify(identification),
            contentType: "application/json;charset=utf-8",
            success: (responseStr) => {
                let response = JSON.parse(responseStr);
                if (response.status === 10200) {
                    this.countWish = response.data;
                } else {
                    alert(`${response.message}（状态码：${response.status}）`);
                }
            }
        })
    },
    methods: {
        openCartDialog(wish) {
            if (wish.checkCondition === 'FALSE') {
                this.dialogVisibleForFALSE = true;
            } else if (wish.quantity > 0) {
                this.cnt = 1;
                this.dialogVisibleForCart = true;
                this.currentId = wish.itemId;
                this.max = wish.quantity;
            } else {
                this.dialogVisibleForZero = true;
            }
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
                quantity: this.cnt,
                accumulate: true,
            };
            addToCart(purchaseData, response => {
                if (response.status === 60200) {
                    this.dialogVisibleForCart = false
                    this.$message({
                        message: '加入购物车成功',
                        type: 'success'
                    });
                    pageHeader.updateCart();
                } else {
                    this.$message.error('商品加购数已超过库存');
                }
            })
            // $.ajax({
            //     url: `${url}/requests/cart/modifyCart`,
            //     method: 'post',
            //     data: JSON.stringify(purchaseData),
            //     contentType: "application/json;charset=utf-8",
            //     success: (responseStr) => {
            //         let response = JSON.parse(responseStr);
            //         if (response.status === 60200) {
            //             this.dialogVisibleForCart = false
            //             this.$message({
            //                 message: '加入购物车成功',
            //                 type: 'success'
            //             });
            //             pageHeader.updateCart();
            //         } else {
            //             this.$message.error('操作失败');
            //         }
            //     }
            // })
        },

        cancelCollection() {
            modifyCollection(this, this.currentId, false, response => {
                this.dialogVisibleForCancelCollection = false;
                this.countWish--;
                if (this.wishes.length == 1 && this.page != 1) {
                    this.page--;
                }
                this.getWishList();
            });
        },

        // updateCollectionState() {
        //     for (let i = 0; i < this.wishes.length; i++) {
        //         if (this.wishes[i].itemId === this.currentId) {
        //             this.wishes.splice(i, 1);
        //         }
        //     }
        // },

        getWishList() {
            this.cntSuccess = 0;
            let identification = {
                userId: $.cookie("id"),
                token: $.cookie("token"),
                page: this.page,
            };
            $.ajax({
                url: `${url}/requests/user/wishlist`,
                method: 'post',
                data: JSON.stringify(identification),
                contentType: "application/json;charset=utf-8",
                success: async (responseStr) => {
                    let response = JSON.parse(responseStr);
                    if (response.status === 10200) {
                        this.loading = true;
                        this.wishes = response.data;
                        let promises = [];
                        for (let i = 0; i < this.wishes.length; i++) {
                            promises.push(handleItemInfo(this, i));
                        }
                        await Promise.all(promises);
                        if (this.cntSuccess === this.wishes.length) {
                            this.loading = false;
                        }
                    } else {
                        this.wishes = [];
                        this.loading = false;
                    }
                }
            })
        }
    }
})

async function handleItemInfo(th, i) {

    await getItemInfoById(th.wishes[i].itemId, response => {

        th.$set(th.wishes[i], 'name', response.data.name);
        th.$set(th.wishes[i], 'quantity', response.data.quantity);
        th.$set(th.wishes[i], 'price', response.data.price);
        th.$set(th.wishes[i], 'checkCondition', response.data.checkCondition);

        if (response.data.coverPath === null)
            th.$set(th.wishes[i], 'imageUrl', `../img/null2.png`);
        else
            th.$set(th.wishes[i], 'imageUrl', `http://1.15.220.157:8088/requests/image/${response.data.coverPath}`);
        th.$set(th.wishes[i], 'url', `${url}/item?id=${th.wishes[i].itemId}`);

        th.cntSuccess++;
    })
}

$(wishList.getWishList);