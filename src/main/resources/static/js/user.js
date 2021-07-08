const url = "http://localhost:8088/"

let userinfoForm = new Vue({
    el: "#userinfo-form",
    data: {
        nickname: "",
        phoneNumber: "",
        emailAddress: "",
    },
    methods: {
        getUserInfo() {
            let identification = {
                userID: $.cookie("id"),
                token: $.cookie("token")
            };
            $.ajax({
                url: `${url}/requests/user/info`,
                method: 'post',
                data: JSON.stringify(identification),
                contentType: "application/json;charset=utf-8",
                success: (responseStr) => {
                    let response = JSON.parse(responseStr);
                    if (response.status === 50200) {
                        this.nickname = response.data.nickname;
                        this.phoneNumber = response.data.phoneNumber;
                        this.emailAddress = response.data.emailAddress;
                    } else {
                        alert(`未知错误（状态码：${response.status}）`);
                    }
                }
            })

        }
    }
})

$(userinfoForm.getUserInfo);