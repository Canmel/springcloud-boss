/**
 * Created by liuruijie on 2016/9/28.
 * 前端控制
 */
    //状态码
web_status = {
    SUCCESS : 200,
    FAIL : 500,
    NO_LOGIN : 401,
    NO_PRIVILEGE : "004"
};

$(function () {
    var access_token = sessionStorage.getItem('access_token');
    if (!access_token) {
        // location.href = 'http://' + window.location.host + '/login?redirect_url=/survey/';
    }
});

function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}

function simpleSuccess(result) {
    //如果成功，则读取后端返回的操作指令
    if (result.code == web_status.SUCCESS) {
        return result;
    }
    //未登录
    if (result.status == web_status.NO_LOGIN) {
        alert("您还未登陆！");
        window.location.href =
            "http://127.0.0.1:8081/login.html?backToUrl="+encodeURIComponent(btoa(window.location.href));
    }else{
        //其他错误情况，直接弹出提示框
        if(result.msg!=null){
            alert(result.msg);
        }
    }
    return null;
}

//对jquery的ajax方法再次封装
__ajax = function(url, data, success, type ,contentType, sync, json){
    url =  '/survey/' + url;
    if(null == sync) {
        sync = false;
    }

    success = success||function(data){};
    data = data||{};
    var access_token = sessionStorage.getItem('access_token');
    console.log(access_token);
    if(access_token && url.indexOf('access_token')) {
        data['access_token'] = access_token;
    }
    if(type == 'delete') {
        url += '/' + data['id'] + '?access_token=' + access_token;
    }
    if(type == 'put') {
        url += '?access_token=' + access_token;
    }
    if(json) {
        data = JSON.stringify(data);
    }
    var config = {
        url:url,
        type:type,
        dataType:"json",
        data:data,
        async: sync,
        success:function(result){
            success(simpleSuccess(result));
        },
        error: function (resp) {
            // 未登录
            console.log('', url)
            if(resp.status === web_status.NO_LOGIN) {
                location.href = 'http://' + window.location.host + '/login?redirect_url=/survey/';
            }
        }
    };
    //如果需要token校验
    if(contentType){
        config.contentType = contentType;
    }
    // var token = $.cookie("token");
    // if(token){
    //     config.beforeSend = function (xhr) {
    //         xhr.setRequestHeader("Authorization", "Basic " + btoa(token));
    //     }
    // }
   if(type === 'put') {
        config.dataType = 'json';
   }
   console.log(config)
    $.ajax(config)
};

//再再次封装
AJAX = {
    GET:function(url, data, success){
      __ajax(url, data, success, "get");
    },
    GET_SYNC:function(url, data, success){
        __ajax(url, data, success, "get", null, true);
    },
    POST_JSON: function(url, data, success){
        if(sessionStorage.getItem("access_token")) {
            url = url + '?access_token=' + sessionStorage.getItem("access_token");
        }

        __ajax(url, data, success, "post", "application/json", false, true);
    },
    POST:function(url, data, success){
        __ajax(url, data, success, "post");
    },
    POST_ARRAY:function(url, data, success){
        var access_token = sessionStorage.getItem('access_token');
        url += '?access_token=' + access_token;
        __ajax(url, data, success, "post");
    },
    DELETE: function(url, data, success){
        __ajax(url, data, success, "delete");
    },
    PUT:function(url, data, success){
        __ajax(url, data, success, "put", 'application/json; charset=UTF-8');
    },
    PATCH: function (url, data, success) {
        __ajax(url, data, success, "patch", "application/json");
    },
    INCLUDE: function (url, id) {
        $.ajax({
            url:url,
            type:"get",
            dataType:"html",
            error: function (code) {
                $("#"+id).html("加载失败");
            },
            success: function (result) {
                $("#"+id).html(result);
            }
        })
    }
};
