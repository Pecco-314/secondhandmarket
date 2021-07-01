let loginBox = new Vue({
    el: "#login-box",
    data:  {
            form: {
                email: '',
                password: ''
            }
    },
    methods: {
        onLogin() {
            let userData = {
                email: loginBox.form.email,
                password: loginBox.form.password
            }
            $.ajax({
                url: 'http://localhost:8088/login',
                method: 'post',
                data: JSON.stringify(userData),
                contentType: "application/json;charset=utf-8",
                success: (data) => {
                    console.log(data);
                }
            })
        }
    }
})