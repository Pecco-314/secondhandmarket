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
    },
    methods: {
        select(selection) {
            this.options.forEach(e => e.selectState = (e.value === selection ? 'active' : ''))
            this.itemFilter.type = selection;
            this.loading = true;
            this.getSearchResult();
        },
        getSearchResult() {
            let searchData = {
                itemFilter: this.itemFilter,
                keyword: this.keyword,
            }
            console.log(searchData);
            $.ajax({
                url: `${url}/requests/product/search`,
                method: 'post',
                data: JSON.stringify(searchData),
                contentType: "application/json;charset=utf-8",
                success: (responseStr) => {
                    console.log(responseStr);
                    let response = JSON.parse(responseStr);
                    if (response.status === 30200) {
                        if (this.loading) {
                            this.items = response.data;
                            for (let i = 0; i < this.items.length; i++) {
                                this.items[i].imageurl = `http://1.15.220.157:8088/requests/image/${this.items[i].coverPath}`;
                                this.items[i].url = `${url}/item?id=${this.items[i].id}`;
                            }
                            this.loading = false;
                            this.notFound = false;
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
    mounted() {
        this.select(this.itemFilter.type);
    },
    computed: {
        title: () => hasURLVariables() ? '搜索结果' : '全部商品',
    }
})

$(shopApp.getSearchResult());