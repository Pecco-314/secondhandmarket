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
    const query = window.location.search.substring(1);
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

function getUserInfo(callback) {
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