const url = "http://localhost:8088/";

function getURLVariable(variable) {
    const query = window.location.search.substring(1);
    const vars = query.split("&");
    for (let i = 0; i < vars.length; i++) {
        const pair = vars[i].split("=");
        if (pair[0] == variable) {
            return pair[1];
        }
    }
    return undefined;
}

let itemApp = new Vue({
    el: "#item-app",
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

$(
    () => {
        let id = getURLVariable("id");
        $.ajax({
            url: `${url}/requests/item/${id}`,
            method: 'get',
            contentType: "application/json;charset=utf-8",
            success: (responseStr) => {
                let response = JSON.parse(responseStr);
                if (response.status === 30200) {
                    itemApp.item = response.data;
                    let date = new Date(itemApp.item.releaseTime);
                    itemApp.item.releaseDate = date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate();
                    if (itemApp.item.introduction === null) {
                        itemApp.item.introduction = "本物品暂无简介";
                    }
                    for (let image of itemApp.item.itemImages) {
                        itemApp.imageList.push({
                            name: image,
                            url: `http://localhost:8088/requests/image/${image}`
                        });
                    }
                } else {
                    alert(`${response.message}（状态码：${response.status}）`);
                }
            }
        });
    }
);