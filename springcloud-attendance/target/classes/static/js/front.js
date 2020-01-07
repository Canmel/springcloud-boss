$(function () {
    $('.sidebar-toggler').on('click', function () {
        $('.sidebar').toggleClass('shrink show');
    });

    var stylesheet = $('link#theme-stylesheet');
    $( "<link id='new-stylesheet' rel='stylesheet'>" ).insertAfter(stylesheet);
    var alternateColour = $('link#new-stylesheet');

    $("#colour").change(function () {
        if ($(this).val() !== '') {
            var theme_csspath = 'css/style.' + $(this).val() + '.css';
            alternateColour.attr("href", theme_csspath);
        }
        return false;
    });
});
