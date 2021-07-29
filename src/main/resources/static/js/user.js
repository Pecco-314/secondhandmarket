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
                        url: `${url}/requests/user/info/update`,
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
            // 
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
            imagesForPost: [],   //用于传送
            imageList: [],      //用于显示
        },
        currentId: '',
        page: 1,
        countItem: 0,
        loading: true,
    },
    async mounted() {
        await getItemInfoByFilter('count', {seller: $.cookie('id')}, response => {
            this.countItem = response.data;
        });
    },
    methods: {
        onPostImageSuccessfully(response, file, fileList) {
            this.form.imagesForPost.push(file);
        },
        onRemoveImage(file) {
            this.form.imagesForPost = removeIf(this.form.imagesForPost, f => f.uid === file.uid);
        },
        async getItemList() {
            this.loading = true;
            await getItemInfoByFilter('search', {seller: $.cookie('id'), page: this.page}, response => {
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
                    this.loading = false;
                } else {
                    alert(`${response.message}（状态码：${response.status}）`);
                }
            });
        },
        openUpdateDialog(item) {
            this.form.imageList = [];
            this.form.imagesForPost = [];
            this.dialogVisibleForUpdate = true;
            this.currentId = item.id;
            this.form.name = item.name;
            this.form.quantity = item.quantity;
            this.form.price = item.price;
            this.form.introduction = item.introduction;
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
                this.form.imagesForPost.push(newFile);
            }

        }
        ,
        openDeleteDialog(id) {
            this.currentId = id;
            this.dialogVisibleForDelete = true;
        }
        ,
        updateItem() {
            //获取当前文件信息
            let images = [];
            for (const file of this.form.imagesForPost) {
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
            dialogVisibleForCancel: false,
            loading: true,
            cntSuccess: 0,
            page: 1,
            countOrder: 0,
        }
    },
    async mounted() {
        await getOrderInfoByFilter('count', {buyer: $.cookie('id')}, response => {
            this.countOrder = response.data;
        })
    },
    methods: {
        async getOrderList() {
            this.cntSuccess = 0;
            await setOrderList(this, 'buyer', this.selectedType, this.page);
        },
        onReceive(id) {
            this.dialogVisibleForConfirm = true;
            this.orderId = id;
        },
        async confirm() {
            await changeOrderState(this.orderId, 'FINISHED', () => {
                this.$message({
                    message: '操作成功',
                    type: 'success'
                });
                this.dialogVisibleForConfirm = false;
                this.getOrderList();
                sellsForm.getOrderList();
            });
        },
        confirmCancel() {
            cancelOrder(this.orderId, () => {
                this.$message({
                    message: '操作成功',
                    type: 'success'
                });
                this.dialogVisibleForCancel = false;
                // this.deleteFromList();
                this.getOrderList();
                sellsForm.getOrderList();
            });
        },
        onCancel(orderId) {
            this.dialogVisibleForCancel = true;
            this.orderId = orderId;
        },
        onPay(orderId) {
            window.open(`../checkout?type=by_id&order=${orderId}`)
        }
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
            loading: true,
            selectedType: 'ALL',
            orders: [],
            orderId: '',
            dialogVisibleForConfirm: false,
            cntSuccess: 0,
            page: 1,
            countOrder: 0,
        }
    },
    async mounted() {
        await getOrderInfoByFilter('count', {seller: $.cookie('id')}, response => {
            this.countOrder = response.data;
        })
    },
    methods: {
        async getOrderList() {
            this.cntSuccess = 0;
            await setOrderList(this, 'seller', this.selectedType, this.page);
        },
        confirm() {
            changeOrderState(this.orderId, 'UNRECEIVED', () => {
                this.$message({
                    message: '操作成功',
                    type: 'success'
                });
                this.dialogVisibleForConfirm = false;
                this.getOrderList();
                ordersForm.getOrderList();
            });
        },
        onDeliver(orderId) {
            this.dialogVisibleForConfirm = true;
            this.orderId = orderId;
        },
    }
})
$(userinfoForm.getUserInfo);
$(itemsForm.getItemList);
$(ordersForm.getOrderList);
$(sellsForm.getOrderList);

async function setOrderList(form, role, state, page) {
    form.loading = true;
    let userId = $.cookie("id");
    let orderFilter = {
        orderId: null,
        buyer: role === 'buyer' ? userId : null,
        seller: role === 'seller' ? userId : null,
        item: null,
        state: state === 'ALL' ? null : state,
        page: page,
    };
    $.ajax({
        url: `${url}/requests/user/orderList/search`,
        method: 'post',
        data: JSON.stringify(orderFilter),
        contentType: "application/json;charset=utf-8",
        success: async (responseStr) => {
            let response = JSON.parse(responseStr);
            if (response.status === 40200) {
                form.orders = response.data;
                let promises = [];
                for (let i = 0; i < form.orders.length; i++) {
                    form.orders[i].state = {
                        value: form.orders[i].state,
                        text: getStateText(form.orders[i].state)
                    };
                    promises.push(handleItemInfo(form, i));
                }
                await Promise.all(promises);
                if (form.cntSuccess === form.orders.length) {
                    form.loading = false;
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

async function handleItemInfo(form, i) {
    await getItemInfoById(form.orders[i].item, (response) => {
        form.orders[i].url = `${url}/item?id=${response.data.id}`;
        form.orders[i].imageurl = getImageOrPlaceholder(response.data.coverPath);
        form.orders[i].price = response.data.price;
        form.orders[i].name = response.data.name;
        form.$set(form.orders[i], 'total', form.orders[i].price * form.orders[i].quantity);
        form.$set(form.orders[i], 'checkCondition', response.data.checkCondition);
        form.$forceUpdate();
        form.cntSuccess++;
    });
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