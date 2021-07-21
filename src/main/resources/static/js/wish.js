let wishList = new Vue({
    el: 'myWishes',
    data: {
        wishes: [],
    },
    methods: {
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

$(wishList.getWishList());