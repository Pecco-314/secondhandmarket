let addUserForm = new Vue({
    el: '#addUser',
    data: {
        dialogVisibleForAdd: false,
        form: {
            email: '',
            nickname: '',
            password: '',
            confirmedPassword: '',
        },
        rules: {
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
                    } else if (value !== addUserForm.form.password) {
                        callback(new Error('两次输入密码不一致'));
                    } else {
                        callback();
                    }
                },
                trigger: 'blur'
            }]
        }
    },
    methods: {
        onRegister() {
            this.$refs["form"].validate(valid => {
                if (valid) {
                    let registerData = {
                        email: this.form.email,
                        nickname: this.form.nickname,
                        password: this.form.password
                    }
                    $.ajax({
                        url: `${url}/requests/register`,
                        method: 'post',
                        data: JSON.stringify(registerData),
                        contentType: "application/json;charset=utf-8",
                        success: (responseStr) => {
                            let response = JSON.parse(responseStr);
                            if (response.status === 10200) {
                                this.clear();
                                confirm("添加成功");
                                userTableForm.getUserList();
                            } else if (response.status === 10402) {
                                showErrorInForm(addUserForm, "form", "email", "rules", response.message);
                            } else {
                                alert(`${response.message}（状态码：${response.status}）`);
                            }

                        }
                    })
                }
            });
        },
        clear() {
            this.$refs.form.resetFields();
            this.dialogVisibleForAdd = false;
        }
    }

})


let userTableForm = new Vue({
        el: '#userTable',
        data: {
            tableData: [],
            dialogVisibleForUpdate: false,
            dialogVisibleForDelete: false,
            form: {
                nickname: '',
            },
            currentId: '',
        },

        methods: {
            getUserList() {
                $.ajax({
                    url: `${url}requests/admin/users`,
                    method: 'get',
                    contentType: "application/json;charset=utf-8",
                    success: (responseStr) => {
                        console.log(responseStr);
                        let response = JSON.parse(responseStr);
                        if (response.status === 50200) {
                            this.tableData = response.data;
                        } else {
                            alert(`${response.message}（状态码：${response.status}）`);
                        }
                    }
                });
            },
            openUpdateDialog(row) {
                this.dialogVisibleForUpdate = true;
                this.currentId = row.id;
                this.form.nickname = row.nickname;
            },
            openDeleteDialog(id) {
                this.currentId = id;
                this.dialogVisibleForDelete = true;
            },
            updateUser() {
                let identification = {
                    userID: this.currentId,
                    nickName: this.form.nickname
                };
                $.ajax({
                    url: `${url}/requests/admin/modifyUser`,
                    method: 'post',
                    data: JSON.stringify(identification),
                    contentType: "application/json;charset=utf-8",
                    success: (responseStr) => {
                        let response = JSON.parse(responseStr);
                        if (response.status === 50200) {
                            this.clear();
                            confirm("更新成功");
                            userTableForm.getUserList();
                        } else {
                            alert(`${response.message}（状态码：${response.status}）`);
                        }
                    }
                })
            },
            deleteUser() {
                let identification = {
                    userId: this.currentId
                };
                $.ajax({
                    url: `${url}/requests/admin/deleteUser/${this.currentId}`,
                    method: 'post',
                    data: JSON.stringify(identification),
                    contentType: "application/json;charset=utf-8",
                    success: (responseStr) => {
                        let response = JSON.parse(responseStr);
                        if (response.status === 50200) {
                            this.dialogVisibleForDelete = false;
                            confirm("删除成功");
                            userTableForm.getUserList();
                        } else {
                            alert(`${response.message}（状态码：${response.status}）`);
                        }
                    }
                })
            },
            clear() {
                this.$refs.form.resetFields();
                this.dialogVisibleForUpdate = false;
            }
        }
    }
)

let adminInfoForm = new Vue({
    el: '#admin-info',
    data: {
        id: '',
        nickname: ''
    },
    methods: {
        getAdminInfo() {
            let adminId = $.cookie("id");
            $.ajax({
                url: `${url}/requests/admin/info/${adminId}`,
                method: 'get',
                contentType: "application/json;charset=utf-8",
                success: (responseStr) => {
                    let response = JSON.parse(responseStr);
                    if (response.status === 10200) {
                        console.log(response);
                        this.nickname = response.data.nickname;
                    }
                }
            })
        }
    }
})


$(userTableForm.getUserList);
$(adminInfoForm.getAdminInfo);