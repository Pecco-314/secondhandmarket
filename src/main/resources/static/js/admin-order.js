let ordersTable = new Vue({
    el: '#ordersTable',
    data() {
        return {
            options: [
                {text: "订单号", value: 'orderID'},
                {text: "买家编号", value: 'buyerID'},
                {text: "买家邮箱", value: 'buyerEmail'},
            ],
            selectDatetime: '',
            selectedType: 'orderID',
            searchContent: "",
            tableDataAll: [],
            tableDataSelected: [],
            tableDataTime: [],
            tableDataResult: [],
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
        dateSearch() {
            this.tableDataTime = [];
            for (let i = 0; i < this.tableDataAll.length; i++) {
                if (this.tableDataAll[i].time >= this.selectDatetime[0] && this.tableDataAll[i].time <= this.selectDatetime[1]) {
                    this.tableDataSelected.splice(i, 1);
                }
            }
        },
        onSearch() {
            if (this.searchContent === '') {
                this.tableDataSelected = this.tableDataAll;
            } else if (this.selectedType === 'orderID') {
                this.tableDataSelected = [];
                for (let i = 0; i < this.tableDataAll.length; i++) {
                    if (this.tableDataAll[i].id === parseInt(this.searchContent))
                        this.tableDataSelected.push(this.tableDataAll[i]);
                }
            } else if (this.selectedType === 'buyerID') {
                this.tableDataSelected = [];
                for (let i = 0; i < this.tableDataAll.length; i++) {
                    if (this.tableDataAll[i].buyer === parseInt(this.searchContent))
                        this.tableDataSelected.push(this.tableDataAll[i]);
                }
            } else if (this.selectedType === 'buyerEmail') {
                this.tableDataSelected = [];
                for (let i = 0; i < this.tableDataAll.length; i++) {
                    if (this.tableDataAll[i].email === this.searchContent)
                        this.tableDataSelected.push(this.tableDataAll[i]);
                }
            }

        },
        unionSearch() {

        },
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
                        this.tableDataAll = response.data;
                        for (let i = 0; i < this.tableDataAll.length; i++) {
                            getItemInfo(this.tableDataAll[i].item, response => {
                                this.$set(this.tableDataAll[i], 'money', response.data.price * this.tableDataAll[i].quantity);
                                this.$set(this.tableDataAll[i], 'name', response.data.name);
                            })
                            getUserInfoByAdmin(this.tableDataAll[i].buyer, response => {
                                this.$set(this.tableDataAll[i], 'email', response.data.emailAddress);
                            })
                            this.tableDataSelected = this.tableDataAll;
                            console.log(this.tableDataAll);
                        }

                        console.log(this.tableDataAll);
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