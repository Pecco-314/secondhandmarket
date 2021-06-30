let loginBox = new Vue({
    el: "#login-box",
    data:  {
            form: {
                username: '',
                passname: ''
            }
    },
    methods: {
        onLogin() {
            console.log(`login`);
        }
    }
})