const url = "http://localhost:8088/"

let updateUserForm = new Vue({
    el: '#updateUser',
    data: {
        dialogVisible: false,
        form: {
            nickname: '',
            number: ''
        }}}
)

let addUserForm = new Vue({
    el: '#addUser',
    data: {
        dialogVisible: false,
        form: {
            name: '',
            number: ''
        }}}
)

let userListForm = new Vue({
        el: '#userList',
        data: {
            tableData: []
        },


        methods:{
            getUserList(){
                $.ajax({
                    url: `${url}requests/admin/users`,
                    method: 'get',
                    contentType: "application/json;charset=utf-8",
                    success: (responseStr) => {
                        console.log(responseStr);
                        let response = JSON.parse(responseStr);
                        if (response.status === 50200) {
                            this.tableData=response.data;
                        } else {
                            alert(`${response.message}（状态码：${response.status}）`);
                        }
                    }
                });
            }
        }
    }
)

$(userListForm.getUserList);