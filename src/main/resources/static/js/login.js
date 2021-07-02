/* response ::= {
    "status": <number>,
    "message": <string>,
    "data": {
        "id": <string>,
        "token": <string>,
    },
}
 */

function isPossiblyEmail(text) {
    return /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/.test(text);
}

function isPossiblyID(text) {
    return /^\d+$/.test(text);
}

function handleSuccessfulResponse(response, userType) {
    let data = response.data;
    if (userType === 'user')
        $.cookie("id", data.userID, {expires: 7, path: '/'});
    else if (userType === 'admin')
        $.cookie("id", data.adminID, {expires: 7, path: '/'});
    $.cookie("token", data.token, {expires: 7, path: '/'});
    $.cookie("userType", userType, {expires: 7, path: '/'});
    window.open("../");
}

function showErrorInForm(app, formName, propName, rulesName, message) {
    let currentRules = app[rulesName][propName];
    app[rulesName][propName] = [{validator: (rule, value, callback) => callback(new Error(message))}]
    app.$refs[formName].validateField(propName);
    app[rulesName][propName] = currentRules
}

const url = 'http://localhost:8088'

let loginBox = new Vue({
    el: "#login-box",
    data: {
        formType: "userLogin",
        userLoginForm: {
            emailOrID: '',
            password: ''
        },
        adminLoginForm: {
            id: '',
            password: ''
        },
        registerForm: {
            email: '',
            nickname: '',
            password: '',
            confirmedPassword: ''
        },
        userLoginRules: {
            emailOrID: [
                {
                    validator: (rule, value, callback) => {
                        if (isPossiblyID(value) || isPossiblyEmail(value)) {
                            callback();
                        } else {
                            callback(new Error('请输入正确的账号或电子邮箱地址'));
                        }
                    },
                    trigger: 'blur'
                },
            ],
            password: [
                {required: true, message: '请输入密码', trigger: 'blur'},
                {min: 6, message: '密码应至少有6位', trigger: 'blur'},
            ],
        },
        adminLoginRules: {
            id: [
                {
                    validator: (rule, value, callback) => {
                        if (isPossiblyID(value)) {
                            callback();
                        } else {
                            callback(new Error('请输入正确的账号'));
                        }
                    }
                    , trigger: 'blur'
                },
            ],
            password: [
                {required: true, message: '请输入密码', trigger: 'blur'},
                {min: 6, message: '密码应至少有6位', trigger: 'blur'},
            ],
        },
        registerRules: {
            email: [
                {
                    validator: (rule, value, callback) => {
                        if (isPossiblyEmail(value)) {
                            callback();
                        } else {
                            callback(new Error('请输入正确的电子邮箱地址'));
                        }
                    },
                    trigger: 'blur'
                },
            ],
            nickname: [
                {required: true, message: '请输入昵称', trigger: 'blur'},
            ],
            password: [
                {required: true, message: '请输入密码', trigger: 'blur'},
                {min: 6, message: '密码应至少有6位', trigger: 'blur'},
            ],
            confirmedPassword: [{
                validator: (rule, value, callback) => {
                    if (value === '') {
                        callback(new Error('请再次输入密码'));
                    } else if (value !== loginBox.registerForm.password) {
                        callback(new Error('两次输入密码不一致'));
                    } else {
                        callback();
                    }
                },
                trigger: 'blur'
            }]
        },
    },
    methods: {
        onUserLogin() {
            this.$refs["userLoginForm"].validate(valid => {
                if (valid) {
                    let userData = {
                        emailOrID: this.userLoginForm.emailOrID,
                        password: this.userLoginForm.password
                    }
                    $.ajax({
                        url: `${url}/login/user`,
                        method: 'post',
                        data: JSON.stringify(userData),
                        contentType: "application/json;charset=utf-8",
                        success: (responseStr) => {
                            let response = JSON.parse(responseStr);
                            if (response.status === 10200) {
                                handleSuccessfulResponse(response, 'user');
                            } else if (response.status === 10400) {
                                showErrorInForm(loginBox, "userLoginForm", "emailOrID", "userLoginRules", response.message);
                            } else if (response.status === 10401) {
                                showErrorInForm(loginBox, "userLoginForm", "password", "userLoginRules", response.message);
                            } else {
                                alert(`未知错误（状态码：${response.status}）`);
                            }
                        }
                    })
                }
            });
        },
        onAdminLogin() {
            this.$refs["adminLoginForm"].validate(valid => {
                if (valid) {

                    let adminData = {
                        id: this.adminLoginForm.id,
                        password: this.adminLoginForm.password
                    }
                    $.ajax({
                        url: `{url}/login/admin`,
                        method: 'post',
                        data: JSON.stringify(adminData),
                        contentType: "application/json;charset=utf-8",
                        success: (responseStr) => {
                            let response = JSON.parse(responseStr);
                            if (response.status === 10200) {
                                handleSuccessfulResponse(response, 'admin');
                            } else if (response.status === 10400) {
                                showErrorInForm(loginBox, "adminLoginForm", "id", "adminLoginRules", response.message);
                            } else if (response.status === 10401) {
                                showErrorInForm(loginBox, "adminLoginForm", "password", "adminLoginRules", response.message);
                            } else {
                                alert(`未知错误（状态码：${response.status}）`);
                            }
                        }
                    })
                }
            });
        },
        onRegister() {
            this.$refs["registerForm"].validate(valid => {
                if (valid) {
                    let registerData = {
                        email: this.registerForm.email,
                        nickname: this.registerForm.nickname,
                        password: this.registerForm.password
                    }
                    $.ajax({
                        url: `${url}/register`,
                        method: 'post',
                        data: JSON.stringify(registerData),
                        contentType: "application/json;charset=utf-8",
                        success: (responseStr) => {
                            let response = JSON.parse(responseStr);
                            if (response.status === 10200) {
                                handleSuccessfulResponse(response, 'user');
                            } else if (response.status === 10402) {
                                showErrorInForm(loginBox, "registerForm", "email", "registerRules", response.message);
                            } else {
                                alert(`未知错误（状态码：${response.status}）`);
                            }
                        }
                    })
                }
            });
        }
    }
})