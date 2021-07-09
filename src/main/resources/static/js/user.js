const url = "http://localhost:8088/"
function isPossiblyEmail(text) {
    return /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/.test(text);
}
function isPossiblyPhoneNumber(text) {
    return /^1\d{10}$/.test(text);
}


let userinfoForm = new Vue({
    el: "#userinfo-form",
    data: {
        form: {
            nickname: "",
            phoneNumber: "",
            emailAddress: "",
        },
        rules: {
            nickname: [
                {required: true, message: '请输入昵称'},
            ],
            emailAddress: [
                {
                    validator: (rule, value, callback) => {
                        if (isPossiblyEmail(value)) {
                            callback();
                        } else {
                            callback(new Error('请输入正确的电子邮箱地址'));
                        }
                    }
                },
            ],
            phoneNumber: [
                {
                    validator: (rule, value, callback) => {
                        if (value === "" || isPossiblyPhoneNumber(value)) {
                            callback();
                        } else {
                            callback(new Error('请输入正确的电话号码，或留空'));
                        }
                    }
                },
            ],
        }
    },
    methods: {
        getUserInfo() {
            let identification = {
                userID: $.cookie("id"),
                token: $.cookie("token")
            };
            $.ajax({
                url: `${url}/requests/user/info`,
                method: 'post',
                data: JSON.stringify(identification),
                contentType: "application/json;charset=utf-8",
                success: (responseStr) => {
                    let response = JSON.parse(responseStr);
                    if (response.status === 50200) {
                        this.form.nickname = response.data.nickname;
                        this.form.phoneNumber = response.data.phoneNumber;
                        this.form.emailAddress = response.data.emailAddress;
                    } else {
                        alert(`${response.message}（状态码：${response.status}）`);
                    }
                }
            })
        },
        postUserInfo() {
            this.$refs.form.validate(valid => {
                if (valid) {
                    let identification = {
                        userID: $.cookie("id"),
                        token: $.cookie("token"),
                        telephone: this.form.phoneNumber,
                        emailAddress: this.form.emailAddress,
                        nickname: this.form.nickname
                    };
                    $.ajax({
                        url: `${url}/requests/user/info/update`,
                        method: 'post',
                        data: JSON.stringify(identification),
                        contentType: "application/json;charset=utf-8",
                        success: (responseStr) => {
                            let response = JSON.parse(responseStr);
                            if (response.status === 50200) {
                                confirm("修改成功");
                            } else {
                                alert(`${response.message}（状态码：${response.status}）`);
                            }
                        }
                    })
                }
            });
        },
        validateField(field) {
            userinfoForm.$refs.form.validateField(field);
        }
    }
})
let passwordForm = new Vue({
    el: "#password-form",
    data: {
        form: {
            userID:"",
            token:"",

            oldPassword: "",
            newPassword: "",
            newPassword1: "",
        },
        rules: {
            oldPassword: [
                {required: true, message: '请输入旧密码'},
            ],
            newPassword: [
                {min: 6, message: '密码应至少有6位', trigger: 'blur'},
                {required: true, message: '请输入新密码'},
            ],
            newPassword1: [
                {
                    validator: (rule, value, callback) => {
                        if (value !== passwordForm.form.newPassword) {
                            callback(new Error('两次密码请一致'));
                        } else {
                            callback();
                        }
                    }
                },
            ],
        }
    },
    methods: {
        postPassword() {
            this.$refs.form.validate(valid => {
                if (valid) {
                    let identification = {
                        userID: $.cookie("id"),
                        token: $.cookie("token"),

                        oldPassword: this.form.oldPassword,
                        newPassword: this.form.newPassword,
                        newPassword1: this.form.newPassword1
                    };
                    $.ajax({
                        url: `${url}/requests/user/password/update`,
                        method: 'post',
                        data: JSON.stringify(identification),
                        contentType: "application/json;charset=utf-8",
                        success: (responseStr) => {
                            let response = JSON.parse(responseStr);
                            if (response.status === 50200) {
                                confirm("修改成功");
                            } else {
                                alert(`修改错误（状态码：${response.status}）`);
                            }
                        }
                    })
                }
            });
        },
        validateField(field) {
            userinfoForm.$refs.form.validateField(field);
        }
    }
})

$(userinfoForm.getUserInfo);