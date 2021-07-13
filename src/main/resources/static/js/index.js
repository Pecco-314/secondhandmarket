let indexForm = new Vue({
    el: '#NewProducts',
    data: {
        items: [],
    },
    methods: {
        getIndexItems() {
            $.ajax({
                url: `${url}/index/items`,
                method: 'get',
                contentType: "application/json;charset=utf-8",
                success: (responseStr) => {
                    console.log(responseStr);
                    let response = JSON.parse(responseStr);
                    if (response.status === 30200) {
                        this.items = response.data;
                        for (let i = 0; i < this.items.length; i++) {
                            this.items[i].imageurl = `http://1.15.220.157:8088/requests/image/${this.items[i].coverPath}`;
                            this.items[i].url = `${url}/item?id=${this.items[i].id}`;
                        }
                        console.log(this.items);
                    } else {
                        alert(`${response.message}（状态码：${response.status}）`);
                    }

                }
            })
        }
    }
})

$(indexForm.getIndexItems());