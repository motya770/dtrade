$(document).ready(function () {
    //$('.selectpicker').selectpicker({
    //    maxOptions: 1
    //});

    $('.settings-wrapper').click(function(){
        $(this).find('.dropdown-settings').find('.dropdown-settings-open').toggle();
    });

    $('.statistic tbody tr').click(function(){
        $('.statistic tbody tr').removeClass('green-tr');
        $(this).addClass('green-tr');
    });

    $('a.button.arrow').click(function(){
        $('a.button.arrow').removeClass('active');
        $(this).addClass('active');
    });
});
