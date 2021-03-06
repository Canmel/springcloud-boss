// var $parentNode = window.parent.document;

// function $childNode(name) {
//     return window.frames[name]
// }

// // tooltips
// $('.tooltip-demo').tooltip({
//     selector: "[data-toggle=tooltip]",
//     container: "body"
// });

// // 使用animation.css修改Bootstrap Modal
// $('.modal').appendTo("body");

// $("[data-toggle=popover]").popover();

// //折叠ibox
// $('.collapse-link').click(function () {
//     var ibox = $(this).closest('div.ibox');
//     var button = $(this).find('i');
//     var content = ibox.find('div.ibox-content');
//     content.slideToggle(200);
//     button.toggleClass('fa-chevron-up').toggleClass('fa-chevron-down');
//     ibox.toggleClass('').toggleClass('border-bottom');
//     setTimeout(function () {
//         ibox.resize();
//         ibox.find('[id^=map-]').resize();
//     }, 50);
// });

// //关闭ibox
// $('.close-link').click(function () {
//     var content = $(this).closest('div.ibox');
//     content.remove();
// });

// //判断当前页面是否在iframe中
// if (top == this) {
//     var gohome = '<div class="gohome"><a class="animated bounceInUp" href="index.html?v=4.0" title="返回首页"><i class="fa fa-home"></i></a></div>';
//     $('body').append(gohome);
// }
$(document).ready(function(){
    $('[data-toggle="tooltip"]').tooltip();
});

//animation.css
function animationHover(element, animation) {
    element = $(element);
    element.hover(
        function () {
            element.addClass('animated ' + animation);
        },
        function () {
            //动画完成之前移除class
            window.setTimeout(function () {
                element.removeClass('animated ' + animation);
            }, 2000);
        });
}

// //拖动面板
// function WinMove() {
//     var element = "[class*=col]";
//     var handle = ".ibox-title";
//     var connect = "[class*=col]";
//     $(element).sortable({
//             handle: handle,
//             connectWith: connect,
//             tolerance: 'pointer',
//             forcePlaceholderSize: true,
//             opacity: 0.8,
//         })P
//         .disableSelection();
// };

window.toPage = function(url, params) {
    if(params) {
        sessionStorage.setItem('editParams', params);
    }
    $.pjax({
        url: mvm.applicationContextRootPath + url,
        container: '.page-container'
    });
}


jQuery.validator.addMethod("isMobile", function(value, element) {
    var length = value.length;
    var mobile = /^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/;
    return this.optional(element) || (length == 11 && mobile.test(value));
}, "请正确填写手机号码");