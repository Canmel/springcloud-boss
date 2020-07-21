Vue.filter('formatDate', function (value, fmt) {
    fmt = 'yyyy-MM-dd hh:mm:ss';
    if(!value) {
        return '';
    }
    var getDate = new Date(value);
    var o = {
        'M+': getDate.getMonth() + 1,
         'd+': getDate.getDate(),
        'h+': getDate.getHours(),
        'm+': getDate.getMinutes(),
        's+': getDate.getSeconds(),
        'q+': Math.floor((getDate.getMonth() + 3) / 3),
        'S': getDate.getMilliseconds()
    };
    if (/(y+)/.test(fmt)) {
        fmt = fmt.replace(RegExp.$1, (getDate.getFullYear() + '').substr(4 - RegExp.$1.length))
    }
    for (var k in o) {
        if (new RegExp('(' + k + ')').test(fmt)) {
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length === 1) ? (o[k]) : (('00' + o[k]).substr(('' + o[k]).length)))
        }
    }
    return fmt;
});

Vue.filter('formatOnlyDate', function (value, fmt) {
    fmt = 'yyyy-MM-dd';
    if(!value) {
        return '';
    }
    var getDate = new Date(value);
    var o = {
        'M+': getDate.getMonth() + 1,
        'd+': getDate.getDate(),
        'h+': getDate.getHours(),
        'm+': getDate.getMinutes(),
        's+': getDate.getSeconds(),
        'q+': Math.floor((getDate.getMonth() + 3) / 3),
        'S': getDate.getMilliseconds()
    };
    if (/(y+)/.test(fmt)) {
        fmt = fmt.replace(RegExp.$1, (getDate.getFullYear() + '').substr(4 - RegExp.$1.length))
    }
    for (var k in o) {
        if (new RegExp('(' + k + ')').test(fmt)) {
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length === 1) ? (o[k]) : (('00' + o[k]).substr(('' + o[k]).length)))
        }
    }
    return fmt;
});

Vue.filter('formatOnlyTime', function (value, fmt) {
    fmt = 'hh:mm:ss';
    if(!value) {
        return '';
    }
    var getDate = new Date(value);
    var o = {
        'M+': getDate.getMonth() + 1,
        'd+': getDate.getDate(),
        'h+': getDate.getHours(),
        'm+': getDate.getMinutes(),
        's+': getDate.getSeconds(),
        'q+': Math.floor((getDate.getMonth() + 3) / 3),
        'S': getDate.getMilliseconds()
    };
    if (/(y+)/.test(fmt)) {
        fmt = fmt.replace(RegExp.$1, (getDate.getFullYear() + '').substr(4 - RegExp.$1.length))
    }
    for (var k in o) {
        if (new RegExp('(' + k + ')').test(fmt)) {
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length === 1) ? (o[k]) : (('00' + o[k]).substr(('' + o[k]).length)))
        }
    }
    return fmt;
});

Vue.filter('toNow', function (value) {
    if(!value) return '';
    var now = new Date().getTime(),
        diffValue = now - new Date(value),
        result={
            isToday:false
        },
        minute = 1000 * 60,
        hour = minute * 60,
        day = hour * 24,
        halfamonth = day * 15,
        month = day * 30,
        year = month * 12,

        _year = diffValue/year,
        _month =diffValue/month,
        _week =diffValue/(7*day),
        _day =diffValue/day,
        _hour =diffValue/hour,
        _min =diffValue/minute;

    if(new Date().toDateString()==new Date(value).toDateString()){
        result.isToday=true;
    }
    if(_year>=1) result.text=parseInt(_year) + "年前";
    else if(_month>=1) result.text=parseInt(_month) + "个月前";
    else if(_week>=1) result.text=parseInt(_week) + "周前";
    else if(_day>=1) result.text=parseInt(_day) +"天前";
    else if(_hour>=1) result.text=parseInt(_hour) +"小时前";
    else if(_min>=1) result.text=parseInt(_min) +"分钟前";
    else result.text="刚刚";
    return result.text;
});