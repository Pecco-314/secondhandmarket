Vue.component('page-header', {
    props: {
        isMainPage: Boolean,
        pageName: String,
    },

    data() {
        return {
            options: [
                {text: "商品分类", value: null},
                {text: "数码产品", value: 'DIGITAL'},
                {text: "书籍教材", value: 'BOOK'},
                {text: "衣鞋帽伞", value: 'CLOTHES'},
                {text: "代步工具", value: 'TRANSPORT'},
                {text: "体育健身", value: 'SPORTS'},
                {text: "家用电器", value: 'ELECTRIC'},
                {text: "日常用品", value: 'DAILY_USE'},
                {text: "票券产品", value: 'TICKET'},
                {text: "其他", value: 'OTHERS'},
            ],
            selectedType: null,
            searchContent: "",
            imageUrl: '',
            dialogVisibleForExit: false,
            countCart: 0,
        }
    },

    computed: {
        isLogin() {
            return $.cookie("id") !== undefined;
        },
        navButtonClass() {
            return this.isMainPage ? "border-radius-5 btn-bg-one" : "btn-bg-three";
        }
    },

    mounted() {
        if (this.isLogin) {
            $.ajax({
                url: `${url}/requests/cart/count`,
                method: 'post',
                data: JSON.stringify({
                    userID: $.cookie('id'),
                    token: $.cookie('token'),
                }),
                contentType: "application/json;charset=utf-8",
                success: (responseStr) => {
                    let response = JSON.parse(responseStr);
                    this.countCart = response.data;
                }
            })
        }
    },

    methods: {
        getUserInfo() {
            getUserInfo((response) => {
                if (response.data.imagePath !== null) {
                    this.imageUrl = `http://1.15.220.157:8088/requests/user/${response.data.imagePath}`;
                    console.log(this.imageUrl);
                }
            })
        },
        onSearch() {
            window.open(`../shop?type=${this.selectedType}&keyword=${this.searchContent}`, "_self")
        },
        openExitDialog() {
            this.dialogVisibleForExit = true;
        },
        exit() {
            delCookie();
            window.open("../", "_self");
        }
    },

    template: `
<div>
    <header v-if="isMainPage" class="top-header top-header-bg">
        <div class="container">
            <div class="row align-items-center">
                <div class="col-lg-7 col-md-7">
                    <div class="top-header-form">
                        <form>
                            <div class="row">
                                <div class="col-lg-4">
                                    <div class="form-group">
                                        <el-select class="form-control" v-model="selectedType" style="padding: 0 0">
                                            <el-option v-for="option in options" :value="option.value" :key="option.key" :label="option.text">
<!--                                            {{ option.text }}-->
                                            </el-option>
                                        </el-select>
                                    </div>
                                </div>
    
                                <div class="col-lg-8 pl-0">
                                    <div class="form-group search-form">
                                        <input class="form-control" placeholder="搜索商品" v-model="searchContent" @keydown.enter="onSearch">
                                        <el-button @click="onSearch" class="flex-center" icon="el-icon-search">
                                        </el-button>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
    
                <div class="col-lg-5 col-md-5">
                    <div class="top-header-right">
                        <div class="other-option">
                            <div v-if="this.isLogin" class="option-item">
                                <div class="language-list">
                                    <div class="dropdown language-list-dropdown">
                                        <button class="btn dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                                <img v-if="imageUrl" :src="imageUrl">
                                                <img v-else src="../img/defaulthead.png">
                                                <i  class='bx bx-chevron-down'></i>
                                        </button>
                                        <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                                              <a class="dropdown-item" href="../user">
                                                    <i class='bx bx-user'> 个人中心</i>
                                              </a>
                                              
                                              <a class="dropdown-item" href="../wishlist">
                                                    <i class='bx bx-heart'> 我的收藏</i>
                                              </a>
                                               
                                              <a class="dropdown-item selectable" @click="openExitDialog">
                                                   <i class='bx bx-exit'> 退出登录</i>
                                              </a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="option-item">
                                <div class="cart-btn-area">
                                    <a href="../cart" class="cart-btn"><i class='bx bx-cart'></i></a>
                                    <span>{{ countCart }}</span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </header>
    
          <el-dialog title="退出登录" :visible.sync="dialogVisibleForExit" width="30%" :append-to-body='true'>
        是否确定退出登录？
              <span slot="footer" class="dialog-footer">
                     <el-button
                            @click="dialogVisibleForExit = false">取 消</el-button>
                     <el-button type="primary" @click="exit">确 定</el-button>
              </span>
        </el-dialog>
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
                                    <a href="../user" class="nav-link">
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
                            
                                <div v-if="this.isLogin" class="nav-other-item">
                                    <div class="language-list">
                                        <div class="dropdown language-list-dropdown">
                                        <button class="btn dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                                <img v-if="imageUrl" :src="imageUrl">
                                                <img v-else src="../img/defaulthead.png">
                                                <i class='bx bx-chevron-down'></i>
                                        </button>
                                        <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                                              <a class="dropdown-item" href="../user">
                                                    <i class='bx bx-user'> 个人中心</i>
                                              </a>
                                              
                                               <a class="dropdown-item" href="../wishlist">
                                                    <i class='bx bx-heart'> 我的收藏</i>
                                              </a>
                                               
                                              <a class="dropdown-item selectable" @click="openExitDialog">
                                                   <i class='bx bx-exit'> 退出登录</i>
                                              </a>
                                        </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="nav-other-item">
                                    <div class="cart-btn-area">
                                        <a href="../cart" class="cart-btn"><i class='bx bx-cart'></i></a>
                                        <span>{{ countCart }}</span>
                                    </div>
                                </div>
                                
                            </div>

                            <div class="nav-btn nav-other-btn">
                                <a v-if="this.isLogin" href="../post" class="default-btn" :class="this.navButtonClass" style="border-radius: 5px">发布商品</a>
                                <a v-else href="../login" class="default-btn" :class="this.navButtonClass">登录/注册</a>
                            </div>
                        </div>
                    </nav>
                </div>
            </div>
        </div>
    </div>`
})

let pageHeader = new Vue({
    el: "#page-header"
}).$children[0];

$(pageHeader.getUserInfo);