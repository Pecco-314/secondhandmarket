const url = "http://localhost:8088";

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
            introduction: '本物品暂无简介',
            coverPath: '',
            releaseTime: '',
            itemTags: [],
            itemImages: [],
        },
        imageList: []
    }
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
                } else {
                    alert(`${response.message}（状态码：${response.status}）`);
                }
            }
        });
    }
);