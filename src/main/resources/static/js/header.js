Vue.component('page-header', {
    data: () => ({
        searchContent: ""
    }),
    methods: {
        openMainPage: () => window.open('../'),
        openPostPage: () => {
            let id = $.cookie('id');
            if (id === undefined) {
                window.open('../login');
            } else {
                window.open('../post');
            }
        }
    },
    template: `
    <div class="page-header-container">
        <div class="page-header">
            <div class="logo-container" v-on:click="openMainPage">
                <img src="../img/logo.png">
            </div>
            <div id="search-box">
                <el-input class="transparency-input" v-model="searchContent" placeholder="搜索商品">
                    <el-button slot="append" icon="el-icon-search"></el-button>
                </el-input>
            </div>
            <div class="sale-button-container">
                <el-button icon="el-icon-sell" class="sale-button" v-on:click="openPostPage">发布商品</el-button>
            </div>
        </div>
    </div>`
})

new Vue({
    el: "#page-header"
});