Vue.component('page-header', {
    methods: {
        onClickUserCenter() {
            let id = $.cookie("id");
            window.open(`../user/${id}`, "_self");
        }
    },

    props: {
        isMainPage: Boolean,
        pageName: String
    },

    computed: {
        isLogin() {
            return $.cookie("id") !== undefined;
        },
        navButtonClass() {
            return this.isMainPage ? "border-radius-5 btn-bg-one" : "btn-bg-three";
        }
    },

    template: `
<div>
    <!-- Top Header Start -->
    <header v-if="isMainPage" class="top-header top-header-bg">
        <div class="container">
            <div class="row align-items-center">
                <div class="col-lg-7 col-md-7">
                    <div class="top-header-form">
                        <form>
                            <div class="row">
                                <div class="col-lg-4">
                                    <div class="form-group">
                                        <select class="form-control">
                                            <option>商品分类</option>
                                            <option>数码产品</option>
                                            <option>书籍教材</option>
                                            <option>衣鞋帽伞</option>
                                            <option>代步工具</option>
                                            <option>体育健身</option>
                                            <option>家用电器</option>
                                            <option>日常用品</option>
                                            <option>票券产品</option>
                                            <option>其他</option>
                                        </select>
                                    </div>
                                </div>
    
                                <div class="col-lg-8 pl-0">
                                    <div class="form-group search-form">
                                        <input type="search" class="form-control" placeholder="搜索商品">
                                        <button type="submit">
                                            <i class="bx bx-search"></i>
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
    
                <div class="col-lg-5 col-md-5">
                    <div class="top-header-right">
                        <div class="other-option">
                            <div class="option-item">
                                <div class="cart-btn-area">
                                    <a href="#" class="cart-btn"><i class='bx bx-cart'></i></a>
                                    <span>1</span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </header>
    <!-- Top Header End -->

        <!-- Start Navbar Area -->
        <div class="navbar-area">

            <!-- Menu For Desktop Device -->
            <div class="main-nav" :class="this.isMainPage ? '' : 'nav-three'">
                <div class="container" href="../">
                    <nav class="navbar navbar-expand-md navbar-light ">
                        <a class="navbar-brand" href="../">
                            <img src="../img/logo.png" alt="Logo">
                        </a>

                        <div class="collapse navbar-collapse mean-menu" id="navbarSupportedContent">
                            <ul class="navbar-nav m-auto">
                                <li class="nav-item">
                                    <a href="../" class="nav-link">
                                        首页
                                    </a>
                                </li>
                                <li class="nav-item">
                                    <a href="../shop" class="nav-link">
                                        商场
                                    </a>
                                </li>
                                <li class="nav-item">
                                    <a @click="onClickUserCenter" class="nav-link">
                                        个人中心
                                    </a>
                                </li>
                                <li class="nav-item">
                                    <a href="../contact" class="nav-link">
                                        联系我们
                                    </a>
                                </li>
                                
                            </ul>

                            <div v-if="!this.isMainPage" class="nav-other">
                                <div class="nav-other-item">
                                    <div class="cart-btn-area">
                                        <a href="#" class="cart-btn"><i class='bx bx-cart'></i></a>
                                        <span>1</span>
                                    </div>
                                </div>
                            </div>

                            <div class="nav-btn nav-other-btn">
                                <a v-if="this.isLogin" href="../post" class="default-btn" :class="this.navButtonClass">发布商品</a>
                                <a v-else href="../login" class="default-btn" :class="this.navButtonClass">登录/注册</a>
                            </div>
                        </div>
                    </nav>
                </div>
            </div>
        </div>
    </div>`
})

new Vue({
    el: "#page-header"
});