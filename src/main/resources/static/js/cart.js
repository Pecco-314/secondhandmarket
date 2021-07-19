let cartForm = new Vue({
    el: '#myCart',
    data: {
        items: [],
        form: {
            quantity: 0
        },
        currentId: '',
    },
    methods: {
        getCartItemList() {
            let userId = $.cookie("id");
            $.ajax({
                url: `${url}/requests/cart/info/${userId}`,
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
                            this.items[i].imageurl = `http://1.15.220.157:8088/requests/image/${this.items[i].coverPath}`;
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
            this.dialogVisibleForUpdate = false;
            this.dialogVisibleForDelete = false;
        }
    }
})