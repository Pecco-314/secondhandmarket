function switchToTab(index) {
    let tab = $(".checkout-tab");
    tab.find('ul.tabs > li').removeClass('current');
    $(`#tab-link-${index}`).addClass('current');
    tab.find('.tab_content').find('div.tabs_item').not('div.tabs_item:eq(' + index + ')').slideUp();
    tab.find('.tab_content').find('div.tabs_item:eq(' + index + ')').slideDown();
}

Vue.component('campus-select', {
    methods: {
        onSelectionChange() {
            checkoutForm.form.campus = this.campus;
        }
    },
    template: `
        <select v-model="campus" class="form-control" @change="onSelectionChange">
            <option
                    v-for="option in options" :key="option"
                    :value="option" v-text="option">
            </option>
        </select>
`,
    data() {
        return {
            options: [
                "翔安校区", "思明校区"
            ],
            campus: "翔安校区"
        }
    },
})

let checkoutForm = new Vue({
    el: "#checkout-form",
    methods: {
        onCompleteOrderInfo() {
            checkoutForm.$refs.form.validate(valid => {
                if (valid) {
                    switchToTab(1);
                }
            });
        },
        validateField(field) {
            checkoutForm.$refs.form.validateField(field);
        },
    },
    data: {
        form: {
            receiverName: "",
            phoneNumber: "",
            campus: "翔安校区",
            dorm: "",
            detailedAddress: "",
        },
        rules: {
            receiverName: [{required: true, message: "请输入收货人姓名"}],
            phoneNumber: [{
                validator: (rule, value, callback) => {
                    if (isPossiblyPhoneNumber(value)) {
                        callback();
                    } else {
                        callback(new Error('请输入正确的电话号码'));
                    }
                }
            }],
            dorm: [{required: true, message: "请输入公寓园区"}],
        }
    },
});
$(() => {
        getUserInfo((response) => {
            checkoutForm.form.phoneNumber = response.data.phoneNumber;
        })
    }
);