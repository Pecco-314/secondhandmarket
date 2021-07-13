let itemApp = new Vue({
    el: "#item-app",
    methods: {
        buy() {
            window.open(`../checkout?id=${this.item.id}&type=single`)
        }
    },
    data: {
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
        imageList: [],
    },
});

$(function () {
        getItemInfo(getURLVariable("id"), response => {
            itemApp.item = response.data;
            let date = new Date(itemApp.item.releaseTime);
            itemApp.item.releaseDate = date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate();
            if (itemApp.item.introduction === null) {
                itemApp.item.introduction = "本物品暂无简介";
            }
            for (let image of itemApp.item.itemImages) {
                itemApp.imageList.push({
                    name: image,
                    url: `http://1.15.220.157:8088/requests/image/${image}`
                });
            }
        })
    }
)