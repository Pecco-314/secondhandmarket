let ordersTable = new Vue({
    el: '#ordersTable',
    data() {
        return {
            tableData: [],
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
        getOrdersList() {
            $.ajax({
                url: `${url}/requests/admin/order`,
                method: 'get',
                contentType: "application/json;charset=utf-8",
                success: (responseStr) => {
                    let response = JSON.parse(responseStr);
                    if (response.status === 40200) {
                        this.tableData = response.data;
                        for (let i = 0; i < this.tableData.length; i++) {
                            getItemInfo(this.tableData[i].item, response => {
                                this.$set(this.tableData[i], 'money', response.data.price * this.tableData[i].quantity);
                                this.$set(this.tableData[i], 'name', response.data.name);
                            })
                            getUserInfoByAdmin(this.tableData[i].buyer, response => {
                                this.$set(this.tableData[i], 'email', response.data.emailAddress);
                            })
                            console.log(this.tableData);
                        }
                        console.log(this.tableData);
                    } else {
                        console.log(response);
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


$(ordersTable.getOrdersList);
$(adminInfoForm.getAdminInfo);