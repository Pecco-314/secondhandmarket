Vue.component('ordering-select', {
    data() {
        return {
            priceOrdering: 'DEFAULT',
            orderings: [
                {text: "默认排序", value: 'DEFAULT'},
                {text: "价格升序", value: 'ASC'},
                {text: "价格降序", value: 'DESC'},
            ],
        }
    },
    template: `
        <select class="form-control" v-model="priceOrdering" @change="onSelectionChange">
            <option v-for="ordering in orderings" :value="ordering.value" :key="ordering.value">{{ ordering.text }}</option>
        </select>
    `,
    methods: {
        onSelectionChange() {
            shopApp.itemFilter.priceOrdering = this.priceOrdering;
            shopApp.getSearchResult();
        }
    },
})

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
            priceOrdering: 'DEFAULT',
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
            this.getSearchResult();
        },
        getSearchResult() {
            this.loading = true;
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
                    if (this.loading) {
                        this.loading = false;
                        console.log(responseStr);
                        let response = JSON.parse(responseStr);
                        if (response.status === 30200) {
                            this.items = response.data;
                            for (let i = 0; i < this.items.length; i++) {
                                this.items[i].imageurl = `http://1.15.220.157:8088/requests/image/${this.items[i].coverPath}`;
                                this.items[i].url = `${url}/item?id=${this.items[i].id}`;
                            }
                            this.notFound = false;
                        } else if (response.status === 30400) {
                            this.notFound = true;
                        } else {
                            alert(`${response.message}（状态码：${response.status}）`);
                        }
                    }
                }
            })
        },
        onSearch() {
            window.history.pushState(null, null, `/shop?filter`);
            this.getSearchResult();
        }
    },
    mounted() {
        this.select(this.itemFilter.type);
        $('select').niceSelect();
    },
    computed: {
        title: () => hasURLVariables() ? '搜索结果' : '全部商品',
    }
})

$(function() {
    shopApp.getSearchResult();
});