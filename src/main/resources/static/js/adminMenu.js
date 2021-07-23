let adminMenu = new Vue({
    el: '#admin-menu',
    methods: {
        logout() {
            delCookie();
            window.open("../login", "_self");
        }
    }
})