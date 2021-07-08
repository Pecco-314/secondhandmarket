const url = "http://localhost:8088";

function isPossiblyPrice(text) {
    return /(^[1-9]\d*(\.\d{1,2})?$)|(^0(\.\d{1,2})?$)/.test(text);
}

function removeIf(list, predicate) {
    let res = []
    for (const e of list) {
        if (!predicate(e)) {
            res.push(e);
        }
    }
    return res;
}

let postForm = new Vue({
    el: "#post-form",
    data() {
        return {
            postForm: {
                name: '',
                type: '',
                quantity: 1,
                price: '',
                originalPrice: '',
                introduction: '',
            },
            options: [
                {value: 'DIGITAL', label: '数码产品',},
                {value: 'BOOK', label: '书籍教材',},
                {value: 'CLOTHES', label: '衣鞋帽伞',},
                {value: 'TRANSPORT', label: '代步工具',},
                {value: 'SPORTS', label: '体育健身',},
                {value: 'ELECTRIC', label: '家用电器'},
                {value: 'DAILY_USE', label: '日常用品',},
                {value: 'TICKET', label: '票券产品',},
                {value: 'OTHERS', label: '其他',},
            ],
            presetTags: [
                {name: "原装正品", type: 'info'},
                {name: "无拆无修", type: 'info'},
                {name: "如假包退", type: 'info'},
                {name: "价格可谈", type: 'info'},
                {name: "一口价", type: 'info'},
            ],
            fileList: [],
            postFormRules: {
                name: [
                    {required: true, message: '请输入商品名称', trigger: 'blur'},
                ],
                type: [
                    {required: true, message: '请选择类型', trigger: 'change'},
                ],
                price: [
                    {required: true, message: '请输入单价', trigger: 'blur'},
                    {
                        validator: (rule, value, callback) => {
                            if (isPossiblyPrice(value)) {
                                callback();
                            } else {
                                callback(new Error('请输入数字，最多两位小数'));
                            }
                        },
                        trigger: 'blur'
                    }
                ],
                originalPrice: [
                    {
                        validator: (rule, value, callback) => {
                            if (isPossiblyPrice(value)) {
                                callback();
                            } else {
                                callback(new Error('请输入数字，最多两位小数'));
                            }
                        },
                        trigger: 'blur'
                    }
                ],
            }
        }
    },
    computed: {
        tags: () => {
            let tags = [];
            for (const tag of postForm.presetTags) {
                if (tag.type === '')
                    tags.push(tag.name);
            }
            tags = tags.concat(postForm.$refs.tagsAppender.dynamicTags);
            return tags;
        },
        images: () => {
            let res = [];
            for (const file of postForm.fileList) {
                res.push(file.response.data[0]);
            }
            return res;
        }
    },
    methods: {
        switchType(tag) {
            if (tag.type === 'info') {
                tag.type = '';
            } else {
                tag.type = 'info';
            }
        },
        onPost() {
            this.$refs.postForm.validate(valid => {
                if (valid) {
                    let form = this.postForm;
                    let itemInfo = {
                        seller: $.cookie("id"),
                        token: $.cookie("token"),
                        name: form.name,
                        type: form.type,
                        quantity: Number.parseInt(form.quantity),
                        originalPrice: (form.originalPrice === "" ? null : Number.parseFloat(form.originalPrice)),
                        price: Number.parseFloat(form.price),
                        tags: this.tags,
                        introduction: form.introduction === "" ? null : form.introduction,
                        images: this.images
                    }
                    $.ajax({
                        url: `${url}/requests/post`,
                        method: 'post',
                        data: JSON.stringify(itemInfo),
                        contentType: "application/json;charset=utf-8",
                        success: (responseStr) => {
                            let response = JSON.parse(responseStr);
                            if (response.status === 30200) {
                                confirm("发布成功！");
                                location.reload();
                            } else {
                                alert(`未知错误（状态码：${response.status}）`);
                            }
                        }
                    })
                }
            })
        },
        onPostImageSuccessfully(response, file) {
            this.fileList.push(file);
        },
        onRemoveImage(file) {
            this.fileList = removeIf(this.fileList, f => f.uid === file.uid);
        }
    }
})