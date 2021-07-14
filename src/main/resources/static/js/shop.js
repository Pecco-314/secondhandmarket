let shopApp = new Vue({
    el: '#search-app',
    data: {
        items: [],
        loading: true,
        notFound: false,
        itemFilter: {
            seller: toNull(getURLVariable('seller')),
            type: toNull(getURLVariable('type')),
            tags: null, // TODO
            priceOrdering: 'DEFAULT', // TODO
            checkCondition: null, // TODO
        },
        keyword: toEmptyString(getURLVariable('keyword')),
        isSearch: hasURLVariables()
    },
    methods: {
        getSearchResult() {
            let searchData = {
                itemFilter: this.itemFilter,
                keyword: this.keyword,
            }
            $.ajax({
                url: `${url}/requests/product/search`,
                method: 'post',
                data: JSON.stringify(searchData),
                contentType: "application/json;charset=utf-8",
                success: (responseStr) => {
                    // console.log(responseStr);
                    let response = JSON.parse(responseStr);
                    if (response.status === 30200) {
                        if (this.loading) {
                            this.items = response.data;
                            for (let i = 0; i < this.items.length; i++) {
                                this.items[i].imageurl = `http://1.15.220.157:8088/requests/image/${this.items[i].coverPath}`;
                                this.items[i].url = `${url}/item?id=${this.items[i].id}`;
                            }
                            this.loading = false;
                        }
                    } else if (response.status === 30400) {
                        this.loading = false;
                        this.notFound = true;
                    } else {
                        alert(`${response.message}（状态码：${response.status}）`);
                    }

                }
            })
        },
        onSearch() {
            window.history.pushState(null, null, `/shop?filter`);
            this.loading = true;
            this.getSearchResult();
        }
    },
    computed: {
        title: () => this.isSearch ? '搜索结果' : '全部商品'
    }
})

$(shopApp.getSearchResult());