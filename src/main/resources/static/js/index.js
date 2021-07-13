let indexForm = new Vue({
    el: '#NewProducts',
    data: {
        items: [],
    },
    methods: {
        getIndexItems() {
            $.ajax({
                url: `${url}/index/items`, 
                method: 'get',
                contentType: "application/json;charset=utf-8",
                success: (responseStr) => {
                    console.log(responseStr);
                    let response = JSON.parse(responseStr);
                    if (response.status === 30200) {
                        this.items = response.data;
                        for (let i = 0; i < this.items.length; i++) {
                            this.items[i].imageurl = `http://1.15.220.157:8088/requests/image/${this.items[i].coverPath}`;
                            this.items[i].url = `${url}/item?id=${this.items[i].id}`;
                        }
                        console.log(this.items);
                    } else {
                        alert(`${response.message}（状态码：${response.status}）`);
                    }

                }
            })
        }
    }
})

let mainCarousel = new Vue({
    el: "#main-carousel",
    data() {
        return {
            carouselList: [
                {key: '1', src: '../external/olex/images/home-one/home-one1.png', desc: 'xxxx'},
                {key: '2', src: '../external/olex/images/home-two/home-two1.png', desc: 'xxxx'},
                {key: '3', src: '../external/olex/images/home-two/home-two2.png', desc: 'xxxx'},
                {key: '4', src: '../external/olex/images/home-two/home-two3.png', desc: 'xxxx'},
                {key: '5', src: '../external/olex/images/inner-banner/inner-banner1.png', desc: 'xxxx'},
                {key: '6', src: '../external/olex/images/inner-banner/inner-banner2.png', desc: 'xxxx'},
            ],
            screenWidth: 0
        }
    },
    methods: {
        setSize: function () {
            // 通过浏览器宽度(图片宽度)计算高度
            this.bannerHeight = 400 / 1920 * this.screenWidth;
        },
    },
    mounted() {
        // 首次加载时,需要调用一次
        this.screenWidth = window.innerWidth;
        this.setSize();
        // 窗口大小发生改变时,调用一次
        window.onresize = () => {
            this.screenWidth = window.innerWidth;
            this.setSize();
        }
    }
})

$(indexForm.getIndexItems());