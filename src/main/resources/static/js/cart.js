let MyCartForm = new Vue({
    el: '#myCart',
    data: {
        carts: [{
            itemName: 'alice',
            price: 12,
            quantity: 15,
            total: 180,
        }],
    },
    methods: {
        getCartList() {
            let identification = {
                userID: $.cookie("id"),
                token: $.cookie("token"),
            };
            $.ajax({
                url: `${url}/requests/cart/info`,
                method: 'post',
                data: JSON.stringify(identification),
                contentType: "application/json;charset=utf-8",
                success: (responseStr) => {
                    let response = JSON.parse(responseStr);
                    if (response.status === 60200) {
                        this.carts = response.data;
                    }
                }
            })
        }
    }
})