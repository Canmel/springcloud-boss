/**
 * Created by liuruijie on 2016/9/28.
 * 前端控制
 */
//状态码
web_status = {
    SUCCESS: 200,
    FAIL: 500,
    NO_LOGIN: 401,
    NO_PRIVILEGE: "004"
};

function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}

function simpleSuccess(result) {
    //如果成功，则读取后端返回的操作指令
    if (result.code == web_status.SUCCESS) {
        console.log(result);
        return result;
    }
    //未登录
    if (result.status == web_status.NO_LOGIN) {
        alert("您还未登陆！");
        window.location.href =
            "http://127.0.0.1:8081/login.html?backToUrl=" + encodeURIComponent(btoa(window.location.href));
    } else {
        //其他错误情况，直接弹出提示框
        if (result.msg != null) {
            alert(result.msg);
        }
    }
    return null;
}

//对jquery的ajax方法再次封装
__ajax = function (url, data, success, type, contentType) {
    success = success || function (data) {
    };
    data = data || {};
    var access_token = sessionStorage.getItem('access_token');
    if (access_token) {
        data['access_token'] = access_token;
    }
    if (type == 'delete') {
        url += '?access_token=' + access_token;
    }
    var config = {
        url: url,
        type: type,
        dataType: "json",
        data: data,
        success: function (result) {
            success(simpleSuccess(result));
        },
        error: function (resp) {
            // 未登录
            console.log('', url)
            if (resp.status === web_status.NO_LOGIN) {
                location.href = 'http://' + window.location.host + '/login?redirect_url=/acti/';
            }
        }
    };
    //如果需要token校验
    if (contentType) {
        config.contentType = contentType;
    }

    var token = $.cookie("token");
    if (token) {
        config.beforeSend = function (xhr) {
            xhr.setRequestHeader("Authorization", "Basic " + btoa(token));
        }
    }
    $.ajax(config)
};

//再再次封装
AJAX = {
    GET: function (url, data, success) {
        __ajax(url, data, success, "get");
    },
    POST_JSON: function (url, data, success) {
        __ajax(url, data, success, "post", "application/json");
    },
    POST: function (url, data, success) {
        __ajax(url, data, success, "post");
    },
    DELETE: function (url, data, success) {
        __ajax(url, data, success, "delete");
    },
    PUT: function (url, data, success) {
        __ajax(url, data, success, "put", "application/json");
    },
    PATCH: function (url, data, success) {
        __ajax(url, data, success, "patch", "application/json");
    },
    INCLUDE: function (url, id) {
        $.ajax({
            url: url,
            type: "get",
            dataType: "html",
            error: function (code) {
                $("#" + id).html("加载失败");
            },
            success: function (result) {
                $("#" + id).html(result);
            }
        })
    }
};
