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
                        elAlert(this, response.message, '', () => {
                        });
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

        addToCollection() {
            modifyCollection(this, this.item.id, true, response => {
                this.dialogVisibleForCollection = false;
                this.updateCollectionState();
                //location.reload();
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
        isCollected: false,
        imageList: [],
        dialogVisibleForCollection: false,
        dialogVisibleForCancelCollection: false,
    },
});

$(function () {
        getItemInfo(getURLVariable("id"), response => {
            itemApp.item = response.data;
            itemApp.max = response.data.quantity;
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