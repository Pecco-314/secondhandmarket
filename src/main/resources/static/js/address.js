new Vue({
    el: "#address-aside",
    methods: {
        handleOpen(key, keyPath) {
            console.log(key, keyPath);
        },
        handleClose(key, keyPath) {
            console.log(key, keyPath);
        }
    }
})

new Vue().$mount('#app');

new Vue ({
    el: "#addr-form",
    data() {
        return {
            newForm: {
                name: '',
                telephone: '',
                address: '',
                deaddress: '',
                addrtype: '',
                defaultaddr: []
            },
            modifyForm: {
                name: '',
                telephone: '',
                address: '',
                deaddress: '',
                addrtype: '',
                defaultaddr: []
            },
            newRules: {
                name: [
                    { required: true, message: '请输入收货人姓名', trigger: 'blur' }
                ],
                telephone: [
                    { required: true, message: '请输入手机号', trigger: 'blur' }
                ],
                address: [
                    { required: true, message: '请选择收货地址', trigger: 'blur' }
                ],
                deaddress: [
                    { required: true, message: '请输入详细地址', trigger: 'blur' }
                ],
                addrtype: [
                    { required: true, message: '请至少选择一个地址类型', trigger: 'blur' }
                ],
                defaultaddr: [
                    { required: false }
                ]
            }
        };
    },
    methods: {
        submitForm() {
            this.$refs["newForm"].validate((valid) => {
                if (valid) {
                    alert('submit!');
                } else {
                    console.log('error submit!!');
                    return false;
                }
            });
        },
        resetForm() {
            this.$refs["newForm"].resetFields();
        }
    }
})

new Vue ({
    el: 'addressHorizontalSelect',

    components: {},

    props: {
        hideCity: { // 隐藏市
            type: Boolean,
            default: false
        },
        hideArea: { // 隐藏区/县
            type: Boolean,
            default: false
        },
        addressCode: null // 地址编码
    },

    model: {
        prop: 'addressCode',
        event: 'addressSelect'
    },

    data() {
        return {
            provinceList: [], // 省份列表
            cityList: [], // 城市列表
            areaList: [], // 区/县列表
            provinceCode: '', // 省份编码
            cityCode: '', // 城市编码
            areaCode: '', // 区/县编码
            cityFlag: false, // 避免重复请求的标志
            provinceFlag: false,
            areaFlag: false
        }
    },

    computed: {
        span () {
            if (this.hideCity) {
                return 24
            }
            if (this.hideArea) {
                return 12
            }
            return 8
        }
    },

    // watch: {
    // },

    created () {
        this.getProvinces()
    },

    methods: {
        /**
         * 获取数据
         * @param {Array} array 列表
         * @param {String} url 请求url
         * @param {String} code 编码(上一级编码)
         */
        fetchData (array, url, code) {
            this.$http({
                method: 'get',
                url: url + '/' + code
            })
                .then(res => {
                    if (res.data.code === this.API.SUCCESS) {
                        let body = res.data.body || []
                        array.splice(0, array.length, ...body)
                    }
                })
                .catch(err => {
                    console.log(err)
                })
                .finally(res => {
                })
        },
        // 根据国家编码获取省份列表
        getProvinces () {
            if (this.provinceFlag) {
                return
            }
            this.fetchData(this.provinceList, this.API.province, 156)
            this.provinceFlag = true
        },
        // 省份修改，拉取对应城市列表
        changeProvince (val) {
            this.fetchData(this.cityList, this.API.city, this.provinceCode)
            this.cityFlag = true
            this.cityCode = ''
            this.areaCode = ''
            this.$emit('addressSelect', val)
        },
        // 根据省份编码获取城市列表
        getCities () {
            if (this.cityFlag) {
                return
            }
            if (this.provinceCode) {
                this.fetchData(this.cityList, this.API.city, this.provinceCode)
                this.cityFlag = true
            }
        },
        // 城市修改，拉取对应区域列表
        changeCity (val) {
            this.fetchData(this.areaList, this.API.area, this.cityCode)
            this.areaFlag = true
            this.areaCode = ''
            this.$emit('addressSelect', val)
        },
        // 根据城市编码获取区域列表
        getAreas () {
            if (this.areaFlag) {
                return
            }
            if (this.cityCode) {
                this.fetchData(this.areaList, this.API.area, this.cityCode)
            }
        },
        // 区域修改
        changeArea (val) {
            this.$emit('addressSelect', val)
        },
        // 重置省市区/县编码
        reset () {
            this.provinceCode = '',
                this.cityCode = '',
                this.areaCode = ''
        },
        // 地址编码转换成省市区列表
        addressCodeToList (addressCode) {
            if (!addressCode) return false
            this.$http({
                method: 'get',
                url: this.API.addressCode + '/' + addressCode
            })
                .then(res => {
                    let data = res.data.body
                    if (!data) return
                    if (data.provinceCode) {
                        this.provinceCode = data.provinceCode
                        this.fetchData(this.cityList, this.API.city, this.provinceCode)
                    } else if (data.cityCode) {
                        this.cityCode = data.cityCode
                        this.fetchData(this.areaList, this.API.area, this.cityCode)
                    } else if (data.areaCode) {
                        this.areaCode = data.areaCode
                    }
                })
                .finally(res => {
                })
        }
    },

    watch: {
        addressCode: {
            deep: true,
            immediate: true,
            handler (newVal) {
                if (newVal) {
                    this.addressCodeToList(newVal)
                } else {
                    this.$nextTick(() => {
                        this.reset()
                    })
                }
            }
        }
    }
})

new Vue ({
    el: "#select-city",
    props: {
        value: {
            required: true
        },
        getCityName: {

        }
    },
    data() {
        return {
            options2: addressData,
            props: {
                label: 'name',
                value: 'id',
                children: 'children'
            },
            selCity: []
        }
    },
    watch: {
        value (val) {
            this.init()
        }
    },
    created() {
        // 组件刚载入并不会触发watch value
    },
    methods: {
        init() {
            let el = this.$refs.selectCity
            if (!this.value) {
                if (this.selCity.length) {
                    this.selCity = []
                    el.getElementsByClassName('el-cascader__label')[0].innerHTML = ''
                    el.getElementsByClassName('el-input__inner')[0].setAttribute('placeholder', '请选择')
                }
            } else {
                if (this.selCity.length===0 || this.selCity[3] !== this.value) {
                    this.selCity[0] = this.value.substr(0, 2) + '0000'
                    this.selCity[1] = this.value.substr(0, 4) + '00000000'
                    this.selCity[2] = this.value.substr(0, 6) + '000000'
                    this.selCity[3] = this.value
                    let name = this.getNode().join('<span>/</span>')
                    el.getElementsByClassName('el-cascader__label')[0].innerHTML = name
                    el.getElementsByClassName('el-input__inner')[0].setAttribute('placeholder', '')
                }
            }
        },
        change(val) {
            // 只有选完了，才会将数据返回给父组件
            this.$emit('input', val[3])
            this.returnCityName()
        },
        returnCityName() {
            if (typeof this.getCityName === 'function') {
                this.getCityName(this.getNode().join(''))
            }
        },
        getNode() {
            let name = []
            this.options2.filter(v => {
                if (name[0]) return
                if (v.id===this.selCity[0]) {
                    name.push(v.name)
                    v.children.filter(v => {
                        if (name[1]>0) return
                        if (v.id===this.selCity[1]) {
                            name.push(v.name)
                            v.children.filter(v => {
                                if (name[2]>0) return
                                if (v.id===this.selCity[2]) {
                                    name.push(v.name)
                                    v.children.filter(v => {
                                        if (name[3]>0) return
                                        if (v.id===this.selCity[3]) {
                                            name.push(v.name)
                                            return false
                                        }
                                    })
                                }
                            })
                        }
                    })
                }
            })
            return name
        }
    }
})