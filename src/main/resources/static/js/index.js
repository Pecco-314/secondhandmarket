let indexForm = new Vue({
    el: '#NewProducts',
    data: {
        wishList: [],
        items: [],
        loading: true,
        dialogVisibleForCart: false,
        dialogVisibleForCollection: false,
        dialogVisibleForCancelCollection: false,
        cnt: 1,
        max: 1,
        currentItem: ''
    },
    methods: {
        getIndexItems() {
            $.ajax({
                url: `${url}/index/items`,
                method: 'get',
                contentType: "application/json;charset=utf-8",
                success: (responseStr) => {
                    let response = JSON.parse(responseStr);
                    if (response.status === 30200) {
                        this.items = response.data;
                        //获取用户的收藏情况
                        getWishList(response => {
                            this.wishList = response.data;
                        });

                        for (let i = 0; i < this.items.length; i++) {
                            if (this.items[i].coverPath == null)
                                this.items[i].imageurl = `../img/null2.png`;
                            else
                                this.items[i].imageurl = `http://1.15.220.157:8088/requests/image/${this.items[i].coverPath}`;
                            this.items[i].url = `${url}/item?id=${this.items[i].id}`;
                            //添加是否收藏的信息
                            if ((this.wishList.find(element => {
                                return element.itemId == this.items[i].id
                            })) !== undefined) {
                                this.items[i].isCollected = true;
                            } else {
                                this.items[i].isCollected = false;
                            }
                        }

                        this.loading = false;
                    } else {
                        alert(`${response.message}（状态码：${response.status}）`);
                    }
                }
            })
        },

        openCartDialog(item) {
            if ($.cookie('id')) {
                this.cnt = 1;
                this.dialogVisibleForCart = true;
                this.currentItem = item.id;
                this.max = item.quantity;
            } else {
                window.open("../login", "_self");
            }
        },

        openCollectionDialog(item) {
            if ($.cookie('id')) {
                this.dialogVisibleForCollection = true;
                this.currentItem = item.id;
            } else {
                window.open("../login", "_self");
            }
        },

        openCancelCollectionDialog(item) {
            if ($.cookie('id')) {
                this.dialogVisibleForCancelCollection = true;
                this.currentItem = item.id;
            } else {
                window.open("../login", "_self");
            }
        },

        addToCart() {
            let purchaseData = {
                userID: parseInt($.cookie('id')),
                token: $.cookie('token'),
                itemID: this.currentItem,
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
                        setTimeout(pageHeader.updateCart, 500);
                    } else {
                        this.$message.error('操作失败');
                    }
                }
            })
        },

        addToCollection() {
            modifyCollection(this, this.currentItem, true, response => {
                this.dialogVisibleForCollection = false;
                this.updateCollectionState();
            })
        },

        cancelCollection() {
            modifyCollection(this, this.currentItem, false, response => {
                this.dialogVisibleForCancelCollection = false;
                this.updateCollectionState();
            });
        },

        updateCollectionState() {
            for (let i = 0; i < this.items.length; i++) {
                if (this.items[i].id === this.currentItem) {
                    this.items[i].isCollected = this.items[i].isCollected ? false : true;
                }
            }
        },
    }
})

let mainCarousel = new Vue({
    el: "#main-carousel",
    data() {
        return {
            carouselList: [
                {key: '1', src: '../external/olex/images/home-one/home-one1.png', desc: 'xxxx'},
                {key: '2', src: '../external/olex/images/home-two/home-two1.png', desc: 'xxxx'},
                {key: '3', src: '../external/olex/images/home-two/home-two2.png', desc: 'xxxx'},
                {key: '4', src: '../external/olex/images/home-two/home-two3.png', desc: 'xxxx'},
                {key: '5', src: '../external/olex/images/inner-banner/inner-banner1.png', desc: 'xxxx'},
                {key: '6', src: '../external/olex/images/inner-banner/inner-banner2.png', desc: 'xxxx'},
            ],
            screenWidth: 0
        }
    },
    methods: {
        setSize: function () {
            // 通过浏览器宽度(图片宽度)计算高度
            this.bannerHeight = 400 / 1920 * this.screenWidth;
        },
    },
    mounted() {
        // 首次加载时,需要调用一次
        this.screenWidth = window.innerWidth;
        this.setSize();
        // 窗口大小发生改变时,调用一次
        window.onresize = () => {
            this.screenWidth = window.innerWidth;
            this.setSize();
        }
    }
})

$(indexForm.getIndexItems());