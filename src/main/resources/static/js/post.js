let postForm = new Vue({
    el: "#post-form",
    data() {
        return {
            postForm: {
                name: '',
                type: '',
                quantity: '',
                price: '',
                originalPrice: '',
                introduction: '',
            },
            options: [
                {value: 'shuma', label: '数码产品',},
                {value: 'shuji', label: '书籍教材',},
                {value: 'yixie', label: '衣鞋帽伞',},
                {value: 'daibu', label: '代步工具',},
                {value: 'tiyu', label: '体育健身',},
                {value: 'dianqi', label: '家用电器'},
                {value: 'richang', label: '日常用品',},
                {value: 'piaoquan', label: '票券产品',},
                {value: 'qita', label: '其他',},
            ],
            presetTags: [
                {name: "原装正品", type: 'info'},
                {name: "无拆无修", type: 'info'},
                {name: "如假包退", type: 'info'},
                {name: "价格可谈", type: 'info'},
                {name: "一口价", type: 'info'},
            ],
            images: []
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
                    let itemInfo = { // TODO 告诉后端接口修改
                        seller: $.cookie("id"),
                        token: $.cookie("token"), // HERE
                        name: form.name,
                        type: form.type,
                        quantity: Number.parseInt(form.quantity),
                        originalPrice: (form.originalPrice === "" ? null : Number.parseFloat(form.originalPrice)),
                        price: Number.parseFloat(form.price),
                        tags: this.tags,
                        introduction: form.introduction === "" ? null : form.introduction,
                        images: this.images // HERE
                    }
                    console.log(itemInfo)
                    $.ajax({
                        url: `${url}/requests/post`,
                        method: 'post',
                        data: JSON.stringify(itemInfo),
                        contentType: "application/json;charset=utf-8",
                        success: (responseStr) => {
                            let response = JSON.parse(responseStr);
                            // TODO
                        }
                    })
                }
            })
        },
        onPostImageSuccessfully() {
            // TODO
        }
    }
})