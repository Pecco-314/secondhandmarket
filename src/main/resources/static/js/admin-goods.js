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
                        console.log(this.tableData);
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
                goodsId: this.currentId
            };

            $.ajax({
                url: `${url}/requests/admin/illegalGoods${this.currentId}`,
                method: 'post',
                data: JSON.stringify(identification),
                contentType: "application/json;charset=utf-8",
                success: (responseStr) => {
                    let response = JSON.parse(responseStr);
                    if (response.status === 50200) {
                        this.dialogVisibleForIllegal = false;
                        confirm("更新成功");
                        goodsTable.getGoodsList();
                    } else {
                        alert(`${response.message}（状态码：${response.status}）`);
                    }
                }
            })
        },

        passedGoods(row) {
            let identification = {
                goodsId: this.currentId
            };
            $.ajax({
                url: `${url}/requests/admin/passGoods${this.currentId}`,
                method: 'post',
                data: JSON.stringify(identification),
                contentType: "application/json;charset=utf-8",
                success: (responseStr) => {
                    let response = JSON.parse(responseStr);
                    if (response.status === 50200) {
                        this.dialogVisibleForPass = false;
                        this.$("#passbtn").setAttribute("disabled", true);
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

        clear() {
            this.$refs.form.resetFields();
            // this.dialogVisibleForUpdate = false;
        }
    }
})


// let goodsTableForm = new Vue({
//         //el: '#goodsTable',
//         data: {
//             tableData: [],
//             dialogVisibleForDetail: false,
//             dialogVisibleForIllegal: false,
//             dialogVisibleForPass: false,
//             form: {
//                 id: '',
//                 goodsname: '',
//                 goodstype: '',
//                 introduction: '',
//                 updatetime: '',
//             },
//             currentId: '',
//         },
//
//         methods: {
//             getGoodsList() {
//                 $.ajax({
//                     url: `${url}requests/admin/goods`,
//                     method: 'get',
//                     contentType: "application/json;charset=utf-8",
//                     success: (responseStr) => {
//                         console.log(responseStr);
//                         let response = JSON.parse(responseStr);
//                         if (response.status === 50200) {
//                             this.tableData = response.data;
//                         } else {
//                             alert(`${response.message}（状态码：${response.status}）`);
//                         }
//                     }
//                 });
//             },
//
//             openIllegalDialog(row) {
//                 this.dialogVisibleForIllegal = true;
//                 this.currentId = row.id;
//             },
//
//             openPassDialog(row) {
//                 this.dialogVisibleForPass = true;
//                 this.currentId = row.id;
//             },
//
//             illegalGoods() {
//                 let identification = {
//                     goodsId: this.currentId
//                 };
//
//                 $.ajax({
//                     url: `${url}/requests/admin/illegalGoods`,
//                     method: 'post',
//                     data: JSON.stringify(identification),
//                     contentType: "application/json;charset=utf-8",
//                     success: (responseStr) => {
//                         let response = JSON.parse(responseStr);
//                         if (response.status === 50200) {
//                             this.dialogVisibleForIllegal = false;
//                             confirm("更新成功");
//                             goodsTableForm.getGoodsList();
//                         } else {
//                             alert(`${response.message}（状态码：${response.status}）`);
//                         }
//                     }
//                 })
//             },
//
//             passedGoods(row) {
//                 let identification = {
//                     goodsId: this.currentId
//                 };
//                 $.ajax({
//                     url: `${url}/requests/admin/passGoods`,
//                     method: 'post',
//                     data: JSON.stringify(identification),
//                     contentType: "application/json;charset=utf-8",
//                     success: (responseStr) => {
//                         let response = JSON.parse(responseStr);
//                         if (response.status === 50200) {
//                             this.$("#passbtn").setAttribute("disabled", true);
//                             confirm("通过审核成功");
//                             goodsTableForm.getGoodsList();
//                         } else {
//                             alert(`${response.message}（状态码：${response.status}）`);
//                         }
//                     }
//                 })
//             },
//
//             clear() {
//                 this.$refs.form.resetFields();
//                 // this.dialogVisibleForUpdate = false;
//             }
//         }
//     }
// )

// let adminInfoForm = new Vue({
//     el: '#admin-info',
//     data: {
//         id: '',
//         nickname: ''
//     },
//     methods: {
//         getAdminInfo() {
//             let adminId = $.cookie("id");
//             $.ajax({
//                 url: `${url}/requests/admin/info/${adminId}`,
//                 method: 'get',
//                 contentType: "application/json;charset=utf-8",
//                 success: (responseStr) => {
//                     let response = JSON.parse(responseStr);
//                     if (response.status === 10200) {
//                         console.log(response);
//                         this.nickname = response.data.nickname;
//                     }
//                 }
//             })
//         }
//     }
// })


$(goodsTable.getGoodsList);
// $(adminInfoForm.getAdminInfo);