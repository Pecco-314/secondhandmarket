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
                                this.$message({
                                    message: '添加成功',
                                    duration: 600,
                                    type: 'success'
                                });
                                userTableForm.getUserList();
                            } else if (response.status === 10402) {
                                showErrorInForm(addUserForm, "form", "email", "rules", response.message);
                            } else {
                                this.$message.error('添加失败');
                                //alert(`${response.message}（状态码：${response.status}）`);
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
            currentPage: 1,
            pageSize: 20,
            loading: true,
        },

        methods: {
            getUserList() {
                this.loading = true;
                $.ajax({
                    url: `${url}/requests/admin/users`,
                    method: 'get',
                    contentType: "application/json;charset=utf-8",
                    success: (responseStr) => {
                        let response = JSON.parse(responseStr);
                        if (response.status === 50200) {
                            this.tableData = response.data;
                        } else {
                            alert(`${response.message}（状态码：${response.status}）`);
                        }
                        this.loading = false;
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
                            this.$message({
                                message: '操作成功',
                                duration: 600,
                                type: 'success'
                            });
                            userTableForm.getUserList();
                        } else {
                            this.$message.error('操作失败');
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
                            this.$message({
                                message: '操作成功',
                                duration: 600,
                                type: 'success'
                            })
                            userTableForm.getUserList();
                        } else {
                            this.$message.error('操作失败');
                        }
                    }
                })
            },
            handleSizeChange(val) {
                this.pageSize = val;
                console.log('每页 ${val} 条');
            },
            handleCurrentChange(val) {
                this.currentPage = val;
                console.log(`当前页: ${val}`);
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
            console.log(adminId);
            $.ajax({
                url: `${url}/requests/admin/info/${adminId}`,
                method: 'get',
                contentType: "application/json;charset=utf-8",
                success: (responseStr) => {
                    console.log(responseStr);
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