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
            cntSuccess: 0,
            loading: true,
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
            this.loading = true;
            this.tableDataTime = [];
            for (let i = 0; i < this.tableDataAll.length; i++) {
                if (this.tableDataAll[i].time >= this.selectDatetime[0] && this.tableDataAll[i].time <= this.selectDatetime[1]) {
                    this.tableDataTime.push(this.tableDataAll[i]);
                }
            }
            this.unionSearch();
            this.loading = false;
        },
        onSearch() {
            this.loading = true;
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

            this.unionSearch();
            this.loading = false;
        },
        unionSearch() {
            this.tableDataResult = [];
            for (let i = 0; i < this.tableDataSelected.length; i++) {
                for (let j = 0; j < this.tableDataTime.length; j++) {
                    if (this.tableDataSelected[i] === this.tableDataTime[j]) {
                        this.tableDataResult.push(this.tableDataSelected[i]);
                        break;
                    }
                }
            }
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
            this.loading = true;
            this.cntSuccess = 0;
            let identification = {
                adminID: $.cookie("id"),
                token: $.cookie("token"),
            };
            $.ajax({
                url: `${url}/requests/admin/order`,
                method: 'post',
                data: JSON.stringify(identification),
                contentType: "application/json;charset=utf-8",
                success: (responseStr) => {
                    let response = JSON.parse(responseStr);
                    console.log(response);
                    if (response.status === 40200) {
                        console.log(response.data);
                        this.tableDataAll = response.data;
                        let promises = [];
                        for (let i = 0; i < this.tableDataAll.length; i++) {
                            promises.push(handleItemInfo(this, i));
                            getUserInfoByAdmin(this.tableDataAll[i].buyer, response => {
                                this.$set(this.tableDataAll[i], 'email', response.data.emailAddress);
                            })
                        }

                        this.tableDataResult = this.tableDataAll;
                        this.tableDataTime = this.tableDataAll;
                        this.tableDataSelected = this.tableDataAll;

                    } else {
                        this.loading = false;
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

async function handleItemInfo(th, i) {
    await getItemInfoById(th.tableDataAll[i].item, response => {
        th.$set(th.tableDataAll[i], 'money', response.data.price * th.tableDataAll[i].quantity);
        th.$set(th.tableDataAll[i], 'name', response.data.name);
        if (response.data.coverPath === null)
            th.$set(th.tableDataAll[i], 'imageUrl', `../img/null2.png`);
        else
            th.$set(th.tableDataAll[i], 'imageUrl', `http://1.15.220.157:8088/requests/image/${response.data.coverPath}`);
        th.cntSuccess++;
        if (th.cntSuccess === th.tableDataAll.length)
            th.loading = false;
    })
}


$(adminInfoForm.getAdminInfo);
$(ordersTable.getOrdersList);