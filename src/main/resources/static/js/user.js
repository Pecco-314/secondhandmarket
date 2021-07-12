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
            phoneNumber: [{validator: (rule, value, callback) => {
                    if (value === "" || isPossiblyPhoneNumber(value)) {
                        callback();
                    } else {
                        callback(new Error('请输入正确的电话号码，或留空'));
                    }
                }},],
        }
    },
    methods: {
        getUserInfo() {
            getUserInfo((response) => {
                this.form.nickname = response.data.nickname;
                this.form.phoneNumber = response.data.phoneNumber;
                this.form.emailAddress = response.data.emailAddress;
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
            userID: "",
            token: "",

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
let itemsForm = new Vue({
    el: '#myItems',
    data: {
        items: [],
        dialogVisibleForUpdate: false,
        dialogVisibleForDelete: false,
        form: {
            name: '',
            quantity: 0,
            price: 0.0,
            introduction: '',
        },
        currentId: '',
    },
    methods: {
        getItemList() {
            let userId = $.cookie("id");
            $.ajax({
                url: `${url}requests/user/items/${userId}`,
                method: 'get',
                contentType: "application/json;charset=utf-8",
                success: (responseStr) => {
                    let response = JSON.parse(responseStr);
                    if (response.status === 30200) {
                        this.items = response.data;
                        for (let i = 0; i < this.items.length; i++) {
                            if (this.items[i].checkCondition === 'TRUE')
                                this.items[i].checkCondition = '审核通过';
                            else if (this.items[i].checkCondition === 'FALSE')
                                this.items[i].checkCondition = '审核未通过';
                            else
                                this.items[i].checkCondition = '审核中';

                            this.items[i].url = `http://localhost:8088/requests/image/${this.items[i].coverPath}`;
                        }
                    }
                }
            })
        },
        openUpdateDialog(item) {

            this.dialogVisibleForUpdate = true;
            this.currentId = item.id;
            this.form.name = item.name;
            this.form.quantity = item.quantity;
            this.form.price = item.price;
            this.form.introduction = item.introduction;
            console.log(this.currentId);
        },
        openDeleteDialog(id) {
            console.log(id);
            this.currentId = id;
            this.dialogVisibleForDelete = true;
        },
        updateItem() {
            let identification = {
                itemID: this.currentId,
                name: this.form.name,
                quantity: this.form.quantity,
                price: this.form.price,
                introduction: this.form.introduction
            }
            $.ajax({
                url: `${url}requests/user/modifyItem`,
                method: 'post',
                data: JSON.stringify(identification),
                contentType: "application/json;charset=utf-8",
                success: (responseStr) => {
                    let response = JSON.parse(responseStr);
                    if (response.status === 30200) {
                        this.clear();
                        confirm("更新成功");
                        itemsForm.getItemList();
                    } else {
                        alert(`${response.message}（状态码：${response.status}）`);
                    }
                }
            })
        },
        deleteItem() {
            let itemId = this.currentId;
            $.ajax({
                url: `${url}/requests/user/deleteItem/${itemId}`,
                method: 'get',
                contentType: "application/json;charset=utf-8",
                success: (responseStr) => {
                    let response = JSON.parse(responseStr);
                    if (response.status === 30200) {
                        this.dialogVisibleForDelete = false;
                        confirm("删除成功");
                        itemsForm.getItemList();
                    } else {
                        alert(`${response.message}（状态码：${response.status}）`);
                    }
                }
            })
        },
        clear() {
            this.$refs.form.resetFields();
            this.dialogVisibleForUpdate = false;
            this.dialogVisibleForDelete = false;
        }
    }
})

$(userinfoForm.getUserInfo);
$(itemsForm.getItemList);