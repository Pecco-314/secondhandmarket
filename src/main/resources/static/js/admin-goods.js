let goodsTable = new Vue({
    el: '#goodsTable',
    data() {
        return {
            tableData: [],
            dialogVisibleForIllegal: false,
            dialogVisibleForPass: false,
            currentId: 0,
            currentPage: 1,
            pageSize: 20,
        }
    },
    methods: {
        // resetDateFilter() {
        //     this.$refs.goodsTable.clearFilter('date');
        // },
        // clearFilter() {
        //     this.$refs.goodsTable.clearFilter();
        // },
        formatter(row, column) {
            return row.address;
        },
        filterTag(value, row) {
            return row.status === value;
        },
        filterHandler(value, row, column) {
            const property = column['property'];
            return row[property] === value;
        },
        getGoodsList() {
            $.ajax({
                url: `${url}/requests/admin/items`,
                method: 'get',
                contentType: "application/json;charset=utf-8",
                success: (responseStr) => {
                    let response = JSON.parse(responseStr);
                    if (response.status === 30200) {
                        this.tableData = response.data;
                        for (let i = 0; i < this.tableData.length; i++) {
                            if (this.tableData[i].checkCondition === 'UNCHECKED') {
                                this.tableData[i].btnIllegalDisabled = false;
                                this.tableData[i].btnPassDisabled = false;
                            } else if (this.tableData[i].checkCondition === 'TRUE') {
                                this.tableData[i].btnIllegalDisabled = false;
                                this.tableData[i].btnPassDisabled = true;
                            } else if (this.tableData[i].checkCondition === 'FALSE') {
                                this.tableData[i].btnIllegalDisabled = true;
                                this.tableData[i].btnPassDisabled = false;
                            }
                        }
                        //console.log(this.tableData);
                    } else {
                        alert(`${response.message}（状态码：${response.status}）`);
                    }
                }
            });
        },
        openIllegalDialog(row) {
            this.dialogVisibleForIllegal = true;
            this.currentId = row.id;
            console.log(this.currentId);
        },

        openPassDialog(row) {
            this.dialogVisibleForPass = true;
            this.currentId = row.id;
        },

        illegalGoods() {
            let identification = {
                itemId: this.currentId,
                checkCondition: 'FALSE',
            };

            $.ajax({
                url: `${url}/requests/admin/checkItem`,
                method: 'post',
                data: JSON.stringify(identification),
                contentType: "application/json;charset=utf-8",
                success: (responseStr) => {
                    let response = JSON.parse(responseStr);
                    if (response.status === 30200) {
                        this.dialogVisibleForIllegal = false;
                        confirm("更新成功");
                        goodsTable.getGoodsList();
                    } else {
                        alert(`${response.message}（状态码：${response.status}）`);
                    }
                }
            })
        },

        passedGoods() {
            let identification = {
                itemId: this.currentId,
                checkCondition: 'TRUE',
            };
            $.ajax({
                url: `${url}/requests/admin/checkItem`,
                method: 'post',
                data: JSON.stringify(identification),
                contentType: "application/json;charset=utf-8",
                success: (responseStr) => {
                    let response = JSON.parse(responseStr);
                    if (response.status === 30200) {
                        this.dialogVisibleForPass = false;
                        confirm("通过审核成功");
                        goodsTable.getGoodsList();
                    } else {
                        alert(`${response.message}（状态码：${response.status}）`);
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

        // clear() {
        //     this.$refs.form.resetFields();
        //     // this.dialogVisibleForUpdate = false;
        // }
    }
})


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


$(goodsTable.getGoodsList);
$(adminInfoForm.getAdminInfo);