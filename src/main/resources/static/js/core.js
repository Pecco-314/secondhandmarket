const url = "http://localhost:8088";

function isPossiblyEmail(text) {
    return /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/.test(text);
}

function isPossiblyPhoneNumber(text) {
    return /^1\d{10}$/.test(text);
}

function isPossiblyPrice(text) {
    return /(^[1-9]\d*(\.\d{1,2})?$)|(^0(\.\d{1,2})?$)/.test(text);
}

function isPossiblyID(text) {
    return /^\d+$/.test(text);
}

function toNull(data) {
    if (data === undefined || data === 'null' || data === '')
        return null;
    else
        return data;
}

function toEmptyString(data) {
    if (data === undefined || data === 'null' || data === null)
        return '';
    else
        return data;
}

function showErrorInForm(app, formName, propName, rulesName, message) {
    let currentRules = app[rulesName][propName];
    app[rulesName][propName] = [{validator: (rule, value, callback) => callback(new Error(message))}]
    app.$refs[formName].validateField(propName);
    app[rulesName][propName] = currentRules
}

function getURLVariable(variable) {
    const query = decodeURI(window.location.search.substring(1));
    const vars = query.split("&");
    for (let i = 0; i < vars.length; i++) {
        const pair = vars[i].split("=");
        if (pair[0] == variable) {
            return pair[1];
        }
    }
    return undefined;
}

function hasURLVariables() {
    return window.location.search !== '';
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

function phoneNumberValidator(rule, value, callback) {
    if (value === "" || isPossiblyPhoneNumber(value)) {
        callback();
    } else {
        callback(new Error('请输入正确的电话号码，或留空'));
    }
}

function getUserInfoByAdmin(buyer, callback) {
    $.ajax({
        url: `${url}/requests/admin/user/${buyer}`,
        method: 'get',
        contentType: "application/json;charset=utf-8",
        success: (responseStr) => {
            let response = JSON.parse(responseStr);
            if (response.status === 50200) {
                callback(response);
            } else {
                alert(`${response.message}（状态码：${response.status}）`);
            }
        }
    });
}

function getUserInfo(callback) {
    if ($.cookie("id")) {
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
                    callback(response);
                } else {
                    alert(`${response.message}（状态码：${response.status}）`);
                }
            }
        })
    }
}

function getItemInfo(id, callback) {
    $.ajax({
        url: `${url}/requests/item/${id}`,
        method: 'get',
        contentType: "application/json;charset=utf-8",
        success: (responseStr) => {
            let response = JSON.parse(responseStr);
            if (response.status === 30200) {
                callback(response);
            } else {
                alert(`${response.message}（状态码：${response.status}）`);
            }
        }
    });
}

function getCartList(callback) {
    let identification = {
        userID: $.cookie("id"),
        token: $.cookie("token"),
    };
    return $.ajax({
        url: `${url}/requests/cart/info`,
        method: 'post',
        data: JSON.stringify(identification),
        contentType: "application/json;charset=utf-8",
        success: (responseStr) => {
            let response = JSON.parse(responseStr);
            if (response.status === 60200) {
                callback(response);
            } else {
                alert(`${response.message}（状态码：${response.status}）`)
            }
        }
    });
}

function clearCart(callback) {
    let identification = {
        userID: $.cookie("id"),
        token: $.cookie("token"),
    };
    return $.ajax({
        url: `${url}/requests/cart/clear`,
        method: 'post',
        data: JSON.stringify(identification),
        contentType: "application/json;charset=utf-8",
        success: (responseStr) => {
            let response = JSON.parse(responseStr);
            if (response.status === 60200) {
                callback(response);
            } else {
                alert(`${response.message}（状态码：${response.status}）`)
            }
        }
    })
}

function getWishList(callback) {
    if ($.cookie("id")) {
        let identification = {
            userID: $.cookie("id"),
            token: $.cookie("token"),
        };
        $.ajax({
            url: `${url}/requests/user/wishlist`,
            method: 'post',
            data: JSON.stringify(identification),
            contentType: "application/json;charset=utf-8",
            async: false,//同步
            success: (responseStr) => {
                let response = JSON.parse(responseStr);
                if (response.status === 10200) {
                    callback(response);
                }
            }
        });
    }
}

function getItemCollectedInfo(itemId, callback) {
    if ($.cookie("id")) {
        let identification = {
            userId: $.cookie("id"),
            token: $.cookie("token"),
            itemId: itemId,
        };
        $.ajax({
            url: `${url}/requests/user/wishlist/exists`,
            method: 'post',
            data: JSON.stringify(identification),
            contentType: "application/json;charset=utf-8",
            async: false,//同步
            success: (responseStr) => {
                let response = JSON.parse(responseStr);
                callback(response);
            }
        });
    }
}

function modifyCollection(th, itemId, state, callback) {
    let data = {
        userID: parseInt($.cookie('id')),
        token: $.cookie('token'),
        itemID: itemId,
        isAdding: state,
    };
    $.ajax({
        url: `${url}/requests/user/wishlist/modify`,
        method: 'post',
        data: JSON.stringify(data),
        contentType: "application/json;charset=utf-8",
        success: (responseStr) => {
            let response = JSON.parse(responseStr);
            if (response.status === 10200) {
                th.$message({
                    duration: 600,
                    message: '操作成功',
                    type: 'success'
                });
                callback(response);
            } else {
                th.$message.error('操作失败');
            }
        }
    })
}

function elAlert(component, content, title, callback) {
    component.$alert(content, title, {
        confirmButtonText: '确定',
        callback: callback
    });
}

function getImageOrPlaceholder(path) {
    if (path === null)
        return `../img/null2.png`;
    else
        return `http://1.15.220.157:8088/requests/image/${path}`;
}

function delCookie() {
    let keys = document.cookie.match(/[^ =;]+(?==)/g)
    if (keys) {
        for (let i = keys.length; i--;) {
            document.cookie = keys[i] + '=0;path=/;expires=' + new Date(0).toUTCString() // 清除当前域名下的,例如：m.ratingdog.cn
            document.cookie = keys[i] + '=0;path=/;domain=' + document.domain + ';expires=' + new Date(0).toUTCString() // 清除当前域名下的，例如 .m.ratingdog.cn
            document.cookie = keys[i] + '=0;path=/;domain=ratingdog.cn;expires=' + new Date(0).toUTCString() // 清除一级域名下的或指定的，例如 .ratingdog.cn
        }
    }
}

function changeOrderState(order, state, callback) {
    let orderData = {
        userId: $.cookie('id'),
        token: $.cookie('token'),
        orderId: order,
        state: state,
    };
    return $.ajax({
        url: `${url}/requests/user/orderChecked`,
        method: 'post',
        data: JSON.stringify(orderData),
        contentType: "application/json;charset=utf-8",
        success: (responseStr) => {
            let response = JSON.parse(responseStr);
            if (response.status === 40200) {
                callback(response);
            } else {
                alert(`${response.message}（状态码：${response.status}）`);
            }
        }
    })
}

function cancelOrder(order, callback) {
    let orderFilter = {
        orderId: order,
        userId: $.cookie('id'),
        token: $.cookie('token'),
        itemId: null,
        state: null,
    };
    console.log(orderFilter);
    return $.ajax({
        url: `${url}/requests/user/cancelOrder`,
        method: 'post',
        data: JSON.stringify(orderFilter),
        contentType: "application/json;charset=utf-8",
        success: (responseStr) => {
            let response = JSON.parse(responseStr);
            if (response.status === 40200) {
                callback(response);
            } else {
                alert(`${response.message}（状态码：${response.status}）`);
            }
        }
    })
}