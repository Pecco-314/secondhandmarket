let shopApp = new Vue({
    el: '#search-app',
    data: {
        wishList: [],
        items: [],
        countItem: 0,
        loading: true,
        notFound: false,
        dialogVisibleForCart: false,
        dialogVisibleForCollection: false,
        dialogVisibleForCancelCollection: false,
        cnt: 1,
        max: 1,
        currentId: '',
        itemFilter: {
            page: 1,
            seller: toNull(getURLVariable('seller')),
            keyword: toEmptyString(getURLVariable('keyword')),
            type: toNull(getURLVariable('type')),
            tags: null, // TODO
            priceOrdering: 'DEFAULT',
            quantityOrdering: 'DEFAULT', // TODO
            checkCondition: null,
            inShop: true,
            imagesNeeded: false,
            tagsNeeded: false,
            notEmpty: true,
        },
        options: [
            {text: "所有商品", value: null, selectState: ''},
            {text: "数码产品", value: 'DIGITAL', selectState: ''},
            {text: "书籍教材", value: 'BOOK', selectState: ''},
            {text: "衣鞋帽伞", value: 'CLOTHES', selectState: ''},
            {text: "代步工具", value: 'TRANSPORT', selectState: ''},
            {text: "体育健身", value: 'SPORTS', selectState: ''},
            {text: "家用电器", value: 'ELECTRIC', selectState: ''},
            {text: "日常用品", value: 'DAILY_USE', selectState: ''},
            {text: "票券产品", value: 'TICKET', selectState: ''},
            {text: "其他", value: 'OTHERS', selectState: ''},
        ],
        orderings: [
            {text: "默认排序", value: 'DEFAULT'},
            {text: "价格升序", value: 'ASC'},
            {text: "价格降序", value: 'DESC'},
        ],
        priceOrdering: 'DEFAULT'
    },
    methods: {
        openCartDialog(item) {
            if ($.cookie('id')) {
                this.cnt = 1;
                this.dialogVisibleForCart = true;
                this.currentId = item.id;
                this.max = item.quantity;
            } else {
                window.open("../login", "_self");
            }
        },

        openCollectionDialog(item) {
            if ($.cookie('id')) {
                this.dialogVisibleForCollection = true;
                this.currentId = item.id;
            } else {
                window.open("../login", "_self");
            }
        },

        openCancelCollectionDialog(item) {
            if ($.cookie('id')) {
                this.dialogVisibleForCancelCollection = true;
                this.currentId = item.id;
            } else {
                window.open("../login", "_self");
            }
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
                    setTimeout(pageHeader.updateCart, 500);
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
            //         // elAlert(this, response.message, '', () => {
            //         // });
            //         if (response.status === 60200) {
            //             this.dialogVisibleForCart = false
            //             this.$message({
            //                 message: '加入购物车成功',
            //                 type: 'success'
            //             });
            //             setTimeout(pageHeader.updateCart, 500);
            //         } else {
            //             this.$message.error('操作失败');
            //         }
            //     }
            // })
        },

        addToCollection() {
            modifyCollection(this, this.currentId, true, response => {
                this.dialogVisibleForCollection = false;
                this.updateCollectionState();
            });
        },

        cancelCollection() {
            modifyCollection(this, this.currentId, false, response => {
                this.dialogVisibleForCancelCollection = false;
                this.updateCollectionState();
            });
        },

        updateCollectionState() {
            for (let i = 0; i < this.items.length; i++) {
                if (this.items[i].id === this.currentId) {
                    this.items[i].isCollected = this.items[i].isCollected ? false : true;
                }
            }
        },

        select(selection) {
            this.options.forEach(e => e.selectState = (e.value === selection ? 'active' : ''))
            this.itemFilter.type = selection;
            this.itemFilter.checkCondition = 'TRUE';
            this.getSearchResult();
        },

        getSearchResult(pageChanged) {
            this.loading = true;
            if (!pageChanged) this.itemFilter.page = 1;
            $.ajax({
                url: `${url}/requests/product/search`,
                method: 'post',
                data: JSON.stringify(this.itemFilter),
                contentType: "application/json;charset=utf-8",
                success: (responseStr) => {
                    if (this.loading) {
                        this.loading = false;
                        let response = JSON.parse(responseStr);
                        if (response.status === 30200) {
                            this.items = response.data;
                            //获取用户的收藏情况
                            getWishList(response => {
                                this.wishList = response.data;
                            });
                            for (let i = this.items.length - 1; i >= 0; i--) {
                                //先判断数量是否为0
                                if (this.items[i].quantity === 0) {
                                    this.items.splice(i, 1);
                                    continue;
                                }
                                if (this.items[i].coverPath === null)
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
                            this.notFound = false;
                            this.updateCountItem();
                        } else if (response.status === 30400) {
                            this.notFound = true;
                        } else {
                            alert(`${response.message}（状态码：${response.status}）`);
                        }
                    }
                }
            })
        },
        search() {
            window.history.pushState(null, null, `/shop?filter`);
            this.getSearchResult();
        },
        changeOrdering(ordering) {
            this.itemFilter.priceOrdering = ordering;
            this.search();
        },
        updateCountItem() {
            $.ajax({
                url: `${url}/requests/product/count`,
                method: 'post',
                data: JSON.stringify(this.itemFilter),
                contentType: "application/json;charset=utf-8",
                success: (responseStr) => {
                    let response = JSON.parse(responseStr);
                    if (response.status === 30200) {
                        this.countItem = response.data;
                    } else {
                        alert(`${response.message}（状态码：${response.status}）`);
                    }
                }
            });
        }
    },
    mounted() {
        this.select(this.itemFilter.type);
    },
    computed: {
        title: () => hasURLVariables() ? '搜索结果' : '全部商品',
    }
})

$(function () {
    shopApp.getSearchResult();
});