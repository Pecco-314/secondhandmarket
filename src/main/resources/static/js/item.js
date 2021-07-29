let itemApp = new Vue({
    el: "#item-app",
    methods: {
        buy() {
            window.open(`../checkout?id=${this.item.id}&cnt=${this.cnt}&type=single`)
        },

        addToCart() {
            if ($.cookie('id')) {
                let purchaseData = {
                    userID: parseInt($.cookie('id')),
                    token: $.cookie('token'),
                    itemID: parseInt(getURLVariable('id')),
                    quantity: this.cnt,
                    accumulate: true,
                };
                addToCart(purchaseData, response => {
                    if (response.status === 60200) {
                        this.$message({
                            message: '加入购物车成功',
                            type: 'success'
                        });
                        this.dialogVisibleForCart = false;
                        setTimeout(pageHeader.updateCart, 500);
                        callback(response);
                    } else {
                        this.$message.error('操作失败');
                    }
                })
            } else {
                window.open("../login", "_self");
            }
        },
        openCollectionDialog() {
            if ($.cookie('id')) {
                this.dialogVisibleForCollection = true;
            } else {
                window.open("../login", "_self");
            }
        },

        openContactDialog(item) {
            this.dialogVisibleForContact = true;
            getUserInfoByAdmin(this.item.seller, response => {

                this.sellerEmail = response.data.emailAddress;
                if (response.data.phoneNumber === null) this.sellerPhone = '暂无电话信息';
                else
                    this.sellerPhone = response.data.phoneNumber;
                this.sellerName = response.data.nickname;

            })
        },

        openCartDialog(item) {
            if ($.cookie('id')) {
                this.dialogVisibleForCart = true;
                this.currentItem = item.id;
                this.max = item.quantity;
            } else {
                window.open("../login", "_self");
            }
        },

        addToCollection() {
            modifyCollection(this, this.item.id, true, response => {
                this.dialogVisibleForCollection = false;
                this.updateCollectionState();
            })
        },

        cancelCollection() {
            modifyCollection(this, this.item.id, false, response => {
                this.dialogVisibleForCancelCollection = false;
                this.updateCollectionState();
            })
        },

        updateCollectionState() {
            this.isCollected = this.isCollected ? false : true;
        },

    },
    data: {
        cnt: 1,
        min: 1,
        max: 1,
        item: {
            id: null,
            seller: null,
            name: '',
            type: '',
            quantity: null,
            price: null,
            originalPrice: null,
            introduction: '',
            coverPath: '',
            releaseTime: '',
            itemTags: [],
            itemImages: [],
        },
        sellerName: '',
        sellerPhone: '暂无电话信息',
        sellerEmail: '',
        isCollected: false,
        imageList: [],
        dialogVisibleForCollection: false,
        dialogVisibleForCancelCollection: false,
        dialogVisibleForContact: false,
    },
});

$(async function () {
        await getItemInfoById(getURLVariable("id"), response => {
            itemApp.item = response.data;
            itemApp.max = response.data.quantity;
            if (itemApp.max === 0) {
                itemApp.min = 0;
                itemApp.cnt = 0;
            }
            let date = new Date(itemApp.item.releaseTime);
            itemApp.item.releaseDate = date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate();
            if (itemApp.item.introduction === null) {
                itemApp.item.introduction = "本物品暂无简介";
            }
            if (itemApp.item.itemImages.length === 0) {
                itemApp.imageList.push({
                    url: `../img/null2.png`
                })
            }
            for (let image of itemApp.item.itemImages) {
                itemApp.imageList.push({
                    name: image,
                    url: `http://1.15.220.157:8088/requests/image/${image}`
                });
            }
        });
        getItemCollectedInfo(getURLVariable("id"), response => {
            itemApp.isCollected = response.data;
        });

        console.log(itemApp);
    }
)