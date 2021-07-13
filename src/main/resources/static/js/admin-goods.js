function showErrorInForm(app, formName, propName, rulesName, message) {
    let currentRules = app[rulesName][propName];
    app[rulesName][propName] = [{validator: (rule, value, callback) => callback(new Error(message))}]
    app.$refs[formName].validateField(propName);
    app[rulesName][propName] = currentRules
}

let goodsTable = new Vue({
    el: '#goodsTable',
    data() {
        return {
            tableData: [{
                id: '12987122',
                name: '好滋好味鸡蛋仔',
                category: '江浙小吃、小吃零食',
                desc: '荷兰优质淡奶，奶香浓而不腻',
                updatetime: '2021-7-8',
                status: '已审核'
            }, {
                id: '12987123',
                name: '好滋好味鸡蛋仔',
                category: '江浙小吃、小吃零食',
                desc: '荷兰优质淡奶，奶香浓而不腻',
                updatetime: '2021-6-8',
                status: '违规'
            }, {
                id: '12987125',
                name: '好滋好味鸡蛋仔',
                category: '江浙小吃、小吃零食',
                desc: '荷兰优质淡奶，奶香浓而不腻',
                updatetime: '2021-5-8',
                status: '已审核'
            }, {
                id: '12987126',
                name: '好滋好味鸡蛋仔',
                category: '江浙小吃、小吃零食',
                desc: '荷兰优质淡奶，奶香浓而不腻',
                updatetime: '2021-4-8',
                status: '待审核'
            }]
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
                url: `${url}requests/admin/goods`,
                method: 'get',
                contentType: "application/json;charset=utf-8",
                success: (responseStr) => {
                    console.log(responseStr);
                    let response = JSON.parse(responseStr);
                    if (response.status === 50200) {
                        this.tableData = response.data;
                    } else {
                        alert(`${response.message}（状态码：${response.status}）`);
                    }
                }
            });
        },
        openIllegalDialog(row) {
            this.dialogVisibleForIllegal = true;
            this.currentId = row.id;
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


$(userTableForm.getGoodsList);
// $(adminInfoForm.getAdminInfo);