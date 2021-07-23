let shopApp = new Vue({
    el: '#search-app',
    data: {
        wishList: [],
        items: [],
        loading: true,
        notFound: false,
        dialogVisibleForCart: false,
        dialogVisibleForCollection: false,
        dialogVisibleForCancelCollection: false,
        cnt: 1,
        max: 1,
        currentId: '',
        itemFilter: {
            seller: toNull(getURLVariable('seller')),
            keyword: toEmptyString(getURLVariable('keyword')),
            type: toNull(getURLVariable('type')),
            tags: null, // TODO
            priceOrdering: 'DEFAULT',
            quantityOrdering: 'DEFAULT', // TODO
            checkCondition: null, // TODO
            imageNeeded: true,
            tagsNeeded: true,
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
                        elAlert(this, "加入购物车成功", '', () => {
                        });
                    }
                }
            })
        },

        addToCollection() {
            modifyCollection(this.currentId, true, response => {
                this.dialogVisibleForCollection = false;
                this.updateCollectionState();
            });
            // let data = {
            //     userID: parseInt($.cookie('id')),
            //     token: $.cookie('token'),
            //     itemID: this.currentId,
            //     isAdding: true,
            // };
            // $.ajax({
            //     url: `${url}/requests/user/wishlist/modify`,
            //     method: 'post',
            //     data: JSON.stringify(data),
            //     contentType: "application/json;charset=utf-8",
            //     success: (responseStr) => {
            //         let response = JSON.parse(responseStr);
            //         if (response.status === 10200) {
            //             this.dialogVisibleForCollection = false;
            //             this.updateCollectionState();
            //             confirm("收藏成功");
            //         } else {
            //             alert("收藏失败");
            //         }
            //     }
            // })
        },

        cancelCollection() {
            modifyCollection(this.currentId, false, response => {
                this.dialogVisibleForCancelCollection = false;
                this.updateCollectionState();
            });
            // let data = {
            //     userID: parseInt($.cookie('id')),
            //     token: $.cookie('token'),
            //     itemID: this.currentId,
            //     isAdding: false,
            // };
            // $.ajax({
            //     url: `${url}/requests/user/wishlist/modify`,
            //     method: 'post',
            //     data: JSON.stringify(data),
            //     contentType: "application/json;charset=utf-8",
            //     success: (responseStr) => {
            //         let response = JSON.parse(responseStr);
            //         if (response.status === 10200) {
            //             this.dialogVisibleForCancelCollection = false;
            //             this.updateCollectionState();
            //             confirm("取消成功");
            //         } else {
            //             alert("取消失败");
            //         }
            //     }
            // })
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
            this.getSearchResult();
        },
        getSearchResult() {
            this.loading = true;
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
                            console.log(this.items);
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
    },
    mounted() {
        this.select(this.itemFilter.type);
        // $('select').niceSelect();
    },
    computed: {
        title: () => hasURLVariables() ? '搜索结果' : '全部商品',
    }
})

$(function () {
    shopApp.getSearchResult();
});