let userinfoForm = new Vue({
    el: "#userinfo-form",
    data: {
        form: {
            nickname: "",
            phoneNumber: "",
            emailAddress: "",
        },
        imageUrl: '',
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
            phoneNumber: [{
                validator: (rule, value, callback) => {
                    if (value === "" || isPossiblyPhoneNumber(value)) {
                        callback();
                    } else {
                        callback(new Error('请输入正确的电话号码，或留空'));
                    }
                }
            },],
        }
    },
    methods: {
        getUserInfo() {
            getUserInfo((response) => {
                this.form.nickname = response.data.nickname;
                this.form.phoneNumber = response.data.phoneNumber;
                this.form.emailAddress = response.data.emailAddress;
                if (response.data.imagePath !== null) {
                    this.imageUrl = `http://1.15.220.157:8088/requests/user/${response.data.imagePath}`;
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
                        url: `${url}/requests/user/head/update`,
                        method: 'post',
                        data: JSON.stringify(identification),
                        contentType: "application/json;charset=utf-8",
                        success: (responseStr) => {
                            let response = JSON.parse(responseStr);
                            if (response.status === 50200) {
                                elAlert(this, "修改成功", '', () => {
                                });
                            } else {
                                alert(`${response.message}（状态码：${response.status}）`);
                            }
                        }
                    })
                }
            });
        },
        exit() {
            delCookie();
            window.open("../", "_self");
        },
        validateField(field) {
            userinfoForm.$refs.form.validateField(field);
        },
        // 上传成功回调
        handleAvatarSuccess(res, file) {
            this.imageUrl = `${url}/requests/user/${res.data[0]}`;
            let identification = {
                userID: $.cookie("id"),
                token: $.cookie("token"),
                imageUrl: res.data[0],
            };
            $.ajax({
                url: `${url}/requests/user/head/update`,
                method: 'post',
                data: JSON.stringify(identification),
                contentType: "application/json;charset=utf-8",
                success: (responseStr) => {
                    let response = JSON.parse(responseStr);
                    if (response.status === 50200) {
                        elAlert(this, "修改成功", '', () => {
                        });
                    } else {
                        alert(`${response.message}（状态码：${response.status}）`);
                    }
                }
            })
            // console.log(res.data[0]);
        },
        // // 上传前格式和图片大小限制
        // beforeAvatarUpload(file) {
        //     const type = file.type === 'image/jpeg' || 'image/jpg' || 'image/webp' || 'image/png'
        //     const isLt2M = file.size / 1024 / 1024 < 2
        //     if (!type) {
        //         this.$message.error('图片格式不正确!(只能包含jpg，png，webp，JPEG)')
        //     }
        //     if (!isLt2M) {
        //         this.$message.error('上传图片大小不能超过 2MB!')
        //     }
        //     return type && isLt2M
        // }

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
                {
                    validator: (rule, value, callback) => {
                        if (value === passwordForm.form.oldPassword) {
                            callback(new Error('新密码与旧密码相同'));
                        } else {
                            callback();
                        }
                    }
                },
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
                                this.clear();
                                elAlert(this, "修改成功", '', () => {
                                });
                            } else if (response.status === 50401) {
                                showErrorInForm(passwordForm, 'form', 'oldPassword', 'rules', response.message)
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
        },
        clear() {
            this.$refs.form.resetFields();
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
            imageList: [],
        },
        currentId: '',
    },
    methods: {
        onPostImageSuccessfully(response, file) {
            this.form.imageList.push(file);
            console.log(this.form.imageList);
        },
        onRemoveImage(file) {
            this.form.imageList = removeIf(this.form.imageList, f => f.uid === file.uid);
            console.log(this.form.imageList);
        },
        getItemList() {
            let userId = $.cookie("id");
            $.ajax({
                url: `${url}/requests/user/items/${userId}`,
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
                            this.items[i].url = `${url}/item?id=${this.items[i].id}`
                            this.items[i].imageurl = getImageOrPlaceholder(this.items[i].coverPath);
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
            this.form.imageList = [];
            //插入已有图片
            for (let i = 0; i < item.itemImages.length; i++) {
                let newFile = {
                    url: getImageOrPlaceholder(item.itemImages[i]),
                    //与上传的图片格式保持一致，方便解析
                    response: {
                        data: [item.itemImages[i]]
                    }
                }
                this.form.imageList.push(newFile);
            }
            console.log(this.form.imageList);
            console.log(this.currentId);
        },
        openDeleteDialog(id) {
            console.log(id);
            this.currentId = id;
            this.dialogVisibleForDelete = true;
        },
        updateItem() {
            //获取当前文件信息
            let images = [];
            for (const file of this.form.imageList) {
                images.push(file.response.data[0]);
            }
            let identification = {
                itemID: this.currentId,
                name: this.form.name,
                quantity: this.form.quantity,
                price: this.form.price,
                introduction: this.form.introduction,
                images: images,
            }
            $.ajax({
                url: `${url}/requests/user/modifyItem`,
                method: 'post',
                data: JSON.stringify(identification),
                contentType: "application/json;charset=utf-8",
                success: (responseStr) => {
                    let response = JSON.parse(responseStr);
                    if (response.status === 30200) {
                        this.clear();
                        elAlert(this, "更新成功", '', () => {
                        });
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
                        elAlert(this, "删除成功", '', () => {
                        });
                        itemsForm.getItemList();
                    } else {
                        alert(`${response.message}（状态码：${response.status}）`);
                    }
                }
            })
        },
        clear() {
            this.$refs.form.resetFields();
            this.form.imageList = [];
            this.dialogVisibleForUpdate = false;
            this.dialogVisibleForDelete = false;
        }
    }
})

let ordersForm = new Vue({
    el: '#myOrders',
    data() {
        return {
            options: [
                {text: "全部", value: 'ALL'},
                {text: "待付款", value: 'UNPAID'},
                {text: "待发货", value: 'UNDELIVERED'},
                {text: "待收货", value: 'UNRECEIVED'},
                {text: "已完成", value: 'FINISHED'},
            ],
            selectedType: 'ALL',
            orders: [],
            orderId: '',
            dialogVisibleForConfirm: false,
        }
    },
    methods: {
        getOrderList() {
            console.log(this.selectedType);
            setOrderList(this, 'buyer', this.selectedType);
        },
        confirmPressed(id) {
            this.dialogVisibleForConfirm = true;
            this.orderId = id;
        },
        confirm() {
            $.ajax({
                url: `${url}/requests/user/orderChecked/${this.orderId}`,
                method: 'get',
                contentType: "application/json;charset=utf-8",
                success: (responseStr) => {
                    let response = JSON.parse(responseStr);
                    if (response.status === 40200) {
                        confirm("更新成功");
                        this.getOrderList();
                    } else {
                        alert(`${response.message}（状态码：${response.status}）`);
                    }
                }
            })
            this.dialogVisibleForConfirm = false;
        },
    }
})
let sellsForm = new Vue({
    el: '#mySells',
    data() {
        return {
            options: [
                {text: "全部", value: 'ALL'},
                {text: "待付款", value: 'UNPAID'},
                {text: "待发货", value: 'UNDELIVERED'},
                {text: "待收货", value: 'UNRECEIVED'},
                {text: "已完成", value: 'FINISHED'},
            ],
            selectedType: 'ALL',
            orders: [],
            orderId: '',
            dialogVisibleForConfirm: false,
        }
    },
    methods: {
        getOrderList() {
            setOrderList(this, 'seller', this.selectedType);
        },
        confirmPressed(id) {
            this.dialogVisibleForConfirm = true;
            this.orderId = id;
            console.log(this.orderId);
        },
        confirm() {
            $.ajax({
                url: `${url}/requests/user/orderChecked/${this.orderId}`,
                method: 'get',
                contentType: "application/json;charset=utf-8",
                success: (responseStr) => {
                    let response = JSON.parse(responseStr);
                    if (response.status === 40200) {
                        confirm("更新成功");
                        this.getOrderList();
                    } else {
                        alert(`${response.message}（状态码：${response.status}）`);
                    }
                }
            })
            this.dialogVisibleForConfirm = false;
        },
        test() {
            console.log(this.orders);
        }
    }
})
$(userinfoForm.getUserInfo);
$(itemsForm.getItemList);
$(ordersForm.getOrderList);
$(sellsForm.getOrderList);

function setOrderList(form, role, state) {
    let userId = $.cookie("id");
    let orderFilter = {
        buyer: role === 'buyer' ? userId : null,
        seller: role === 'seller' ? userId : null,
        item: null,
        state: state === 'ALL' ? null : state,
    };
    $.ajax({
        url: `${url}/requests/user/orderList/search`,
        method: 'post',
        data: JSON.stringify(orderFilter),
        contentType: "application/json;charset=utf-8",
        success: (responseStr) => {
            let response = JSON.parse(responseStr);
            console.log(orderFilter);
            console.log(response);
            if (response.status === 40200) {
                form.orders = response.data;
                for (let i = 0; i < form.orders.length; i++) {
                    form.orders[i].state = {
                        value: form.orders[i].state,
                        text: getStateText(form.orders[i].state)
                    };
                    getItemInfo(form.orders[i].item, (response) => {
                        form.orders[i].url = `${url}/item?id=${response.data.id}`;
                        form.orders[i].imageurl = getImageOrPlaceholder(response.data.coverPath);
                        form.orders[i].price = response.data.price;
                        form.orders[i].name = response.data.name;
                        form.$forceUpdate();
                    });
                }
            } else if (response.status === 40400) {
                form.orders = [];
                form.$forceUpdate();
            } else {
                alert(`${response.message}（状态码：${response.status}）`);
            }
        }
    })
}

function getStateText(state) {
    switch (state) {
        case 'UNPAID':
            return '待付款';
        case 'UNDELIVERED':
            return '待发货';
        case 'UNRECEIVED':
            return '待收货';
        case 'FINISHED':
            return '已完成';
    }
}