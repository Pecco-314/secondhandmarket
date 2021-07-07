Vue.component('page-header', {

    template: `
    <div class="preloader">
            <div class="spinner">
                <div class="dot1"></div>
                <div class="dot2"></div>
            </div>
        </div>

        <!-- Start Navbar Area -->
        <div class="navbar-area">
            <!-- Menu For Mobile Device -->
            <div class="mobile-nav">
                <a href="../index.html" class="logo">
                    <img src="../assets/images/logos/logo-1.png" alt="Logo">
                </a>
            </div>

            <!-- Menu For Desktop Device -->
            <div class="main-nav nav-three">
                <div class="container">
                    <nav class="navbar navbar-expand-md navbar-light ">
                        <a class="navbar-brand" href="../index.html">
                            <img src="../img/logo.png" alt="Logo">
                        </a>

                        <div class="collapse navbar-collapse mean-menu" id="navbarSupportedContent">
                            <ul class="navbar-nav m-auto">
                                <li class="nav-item">
                                    <a href="#" class="nav-link">
                                        首页
                                    </a>
                                </li>
                                <li class="nav-item">
                                    <a href="#" class="nav-link active">
                                        Pages 
                                        <i class='bx bx-chevron-down'></i>
                                    </a>
                                    <ul class="dropdown-menu">
                                        <li class="nav-item">
                                            <a href="../about.html" class="nav-link">
                                                About
                                            </a>
                                        </li>

                                        <li class="nav-item">
                                            <a href="#" class="nav-link active">
                                                Shop 
                                                <i class='bx bx-chevron-down'></i>
                                            </a>
                                            <ul class="dropdown-menu">
                                                <li class="nav-item">
                                                    <a href="../shop-details.html" class="nav-link">
                                                        Shop Details
                                                    </a>
                                                </li>
                                                <li class="nav-item">
                                                    <a href="shop-details-left-sidebar.html" class="nav-link active">
                                                        Shop Details Left Sidebar
                                                    </a>
                                                </li>
                                                <li class="nav-item">
                                                    <a href="../shop-details-right-sidebar.html" class="nav-link">
                                                        Shop Details Right Sidebar 
                                                    </a>
                                                </li>
                                                <li class="nav-item">
                                                    <a href="../tracking-order.html" class="nav-link">
                                                        Tracking Order 
                                                    </a>
                                                </li>
                                                <li class="nav-item">
                                                    <a href="../size-guides.html" class="nav-link">
                                                        Size Guides 
                                                    </a>
                                                </li>
                                                <li class="nav-item">
                                                    <a href="../customer-services.html" class="nav-link">
                                                        Customer Services  
                                                    </a>
                                                </li>
                                                <li class="nav-item">
                                                    <a href="../compare.html" class="nav-link">
                                                        Compare  
                                                    </a>
                                                </li>
                                                <li class="nav-item">
                                                    <a href="../cart.html" class="nav-link">
                                                        Cart  
                                                    </a>
                                                </li>
                                                <li class="nav-item">
                                                    <a href="../checkout.html" class="nav-link">
                                                        Checkout  
                                                    </a>
                                                </li>
                                                <li class="nav-item">
                                                    <a href="../wishlist.html" class="nav-link">
                                                        Wishlist  
                                                    </a>
                                                </li>
                                                <li class="nav-item">
                                                    <a href="../my-account.html" class="nav-link">
                                                        My Account  
                                                    </a>
                                                </li>
                                            </ul>
                                        </li>
                                        
                                        <li class="nav-item">
                                            <a href="../faq.html" class="nav-link">
                                                FAQ
                                            </a>
                                        </li>

                                        <li class="nav-item">
                                            <a href="../team.html" class="nav-link">
                                                Team
                                            </a>
                                        </li>

                                        <li class="nav-item">
                                            <a href="../testimonials.html" class="nav-link">
                                                Testimonials
                                            </a>
                                        </li>

                                        <li class="nav-item">
                                            <a href="#" class="nav-link">
                                                User
                                                <i class='bx bx-chevron-down'></i>
                                            </a>
                                            <ul class="dropdown-menu">
                                                <li class="nav-item">
                                                    <a href="../log-in.html" class="nav-link">
                                                        Log In
                                                    </a>
                                                </li>
                                                <li class="nav-item">
                                                    <a href="../register.html" class="nav-link">
                                                        Registration
                                                    </a>
                                                </li>
                                                <li class="nav-item">
                                                    <a href="../forget-password.html" class="nav-link">
                                                        Forget Password
                                                    </a>
                                                </li>
                                            </ul>
                                        </li>

                                        <li class="nav-item">
                                            <a href="../terms-condition.html" class="nav-link">
                                                Terms & Conditions
                                            </a>
                                        </li>

                                        <li class="nav-item">
                                            <a href="../privacy-policy.html" class="nav-link">
                                                Privacy Policy
                                            </a>
                                        </li>

                                        <li class="nav-item">
                                            <a href="../404.html" class="nav-link">
                                                404 page
                                            </a>
                                        </li>

                                        <li class="nav-item">
                                            <a href="../search-page.html" class="nav-link">
                                                Search Page
                                            </a>
                                        </li>
                                        
                                        <li class="nav-item">
                                            <a href="../coming-soon.html" class="nav-link">
                                                Coming Soon
                                            </a>
                                        </li>
                                    </ul>
                                </li>

                                <li class="nav-item">
                                    <a href="#" class="nav-link">
                                        Shop
                                        <i class='bx bx-chevron-down'></i>
                                    </a>
                                    <ul class="dropdown-menu">
                                        <li class="nav-item">
                                            <a href="../shop-left-sidebar.html" class="nav-link">
                                                Shop Left Sidebar 
                                            </a>
                                        </li>
                                        <li class="nav-item">
                                            <a href="../shop-right-sidebar.html" class="nav-link">
                                                Shop Right Sidebar 
                                            </a>
                                        </li>
                                        <li class="nav-item">
                                            <a href="../shop-grid.html" class="nav-link">
                                                Shop Grid 
                                            </a>
                                        </li>
                                        <li class="nav-item">
                                            <a href="../shop-full-width-sidebar.html" class="nav-link">
                                                Shop Full Width Sidebar
                                            </a>
                                        </li>
                                    </ul>
                                </li>

                                <li class="nav-item">
                                    <a href="#" class="nav-link">
                                        Categories
                                        <i class='bx bx-chevron-down'></i>
                                    </a>
                                    <ul class="dropdown-menu">
                                        <li class="nav-item">
                                            <a href="../categories-1.html" class="nav-link">
                                                Categories (2 In Row)
                                            </a>
                                        </li>
                                        <li class="nav-item">
                                            <a href="../categories-2.html" class="nav-link">
                                                Categories (3 In Row) 
                                            </a>
                                        </li>
                                        <li class="nav-item">
                                            <a href="../categories-full-width.html" class="nav-link">
                                                Categories Full Width 
                                            </a>
                                        </li>
                                    </ul>
                                </li>

                                <li class="nav-item">
                                    <a href="#" class="nav-link">
                                        Blog
                                        <i class='bx bx-chevron-down'></i>
                                    </a>
                                    <ul class="dropdown-menu">
                                        <li class="nav-item">
                                            <a href="../blog-1.html" class="nav-link">
                                                Blog Style One 
                                            </a>
                                        </li>
                                        <li class="nav-item">
                                            <a href="../blog-2.html" class="nav-link">
                                                Blog Style Two 
                                            </a>
                                        </li>
                                        <li class="nav-item">
                                            <a href="../blog-details.html" class="nav-link">
                                                Blog Details 
                                            </a>
                                        </li>
                                    </ul>
                                </li>

                                <li class="nav-item">
                                    <a href="../contact.html" class="nav-link">
                                        Contact
                                    </a>
                                </li>
                            </ul>

                            <div class="nav-other">

                                <div class="nav-other-item">
                                    <div class="cart-btn-area">
                                        <a href="#" class="cart-btn"><i class='bx bx-cart'></i></a>
                                        <span>1</span>
                                    </div>
                                </div>
                            </div>

                            <div class="nav-btn nav-other-btn">
                                <a href="../log-in.html" class="default-btn btn-bg-three">登录/注册</a>
                            </div>
                        </div>
                    </nav>
                </div>
            </div>

            <div class="side-nav-responsive">
                <div class="container">
                    <div class="dot-menu">
                        <div class="circle-inner">
                            <div class="circle circle-one"></div>
                            <div class="circle circle-two"></div>
                            <div class="circle circle-three"></div>
                        </div>
                    </div>
                    
                    <div class="container">
                        <div class="side-nav-inner">
                            <div class="side-nav justify-content-center align-items-center">
                                <div class="side-item">
    
                                    <div class="nav-other-item">
                                        <div class="cart-btn-area">
                                            <a href="#" class="cart-btn"><i class='bx bx-cart'></i></a>
                                            <span>1</span>
                                        </div>
                                    </div>

                                    <div class="nav-other-item">
                                        <div class="option-btn">
                                            <a href="../log-in.html" class="default-btn">登录/注册</a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- End Navbar Area -->`
})

new Vue({
    el: "#page-header"
});