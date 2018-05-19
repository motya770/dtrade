$(document).ready(function () {
  //email mask
  $('.inputname').blur(function () {
    if ($(this).val() != '') {
      var pattern = /^([a-z0-9_\.-])+@[a-z0-9-]+\.([a-z]{2,4}\.)?[a-z]{2,4}$/i;
      if (pattern.test($(this).val())) {
        $(this).css({
          'border': '1px solid #569b44'
        });
      } else {
        $(this).css({
          'border': '1px solid #ff0000'
        });
      }
    } else {
      $(this).css({
        'border': '1px solid #ff0000'
      });
    }
  });

  //placeholder
  $('input, textarea').each(function () {
    var placeholder = $(this).attr('placeholder');
    $(this).focus(function () {
      $(this).attr('placeholder', '');
    });
    $(this).focusout(function () {
      $(this).attr('placeholder', placeholder);
    });
  });

  // бургер
  $('.header-nav-btn').click(function () {
    var menu = $('.header-nav');
    if (!$(this).hasClass('header-nav-btn_active')) {
      $(this).addClass('header-nav-btn_active');
      menu.css('right', '0');
      $('.content').addClass('body-fixed');
    } else {
      $(this).removeClass('header-nav-btn_active');
      menu.css('right', '-100%');
      $('.content').removeClass('body-fixed');
    }
  });

  // слайдер на главной
  $('.timeline-slider').owlCarousel({
    responsive: {
      0: {
        autoWidth: true,
        loop: true,
        center: true,
        items: 1,
        margin: 15,
        dots: false,
        nav: false,
        onInitialized: countSliderResp,
        onTranslated: countSliderResp,
      },
      768: {
        dots: false,
        items: 3,
        loop: true,
        margin: -60,
        nav: true,
        autoWidth: true,
        center: true,
        navText: ["", ""],
        onInitialized: countSlider,
        onTranslated: countSlider,
      }
    },
  })

  function countSliderResp(e) {
    $('.timeline-item__slide-number-all').text('/ ' + e.item.count);
    $('.owl-item').css('marginRight', '15px')
    $('.owl-item.active:last').css('marginRight', '0');
  }

  function countSlider(e) {
    $('.timeline-item__slide-number-all').text('/ ' + e.item.count);
    $('.owl-item').css('marginRight', '-60px')
    $('.owl-item.active:last').css('marginRight', '0');
  }

  $('.team-slider').owlCarousel({
    responsive: {
      0: {
        autoWidth: true,
        loop: true,
        center: true,
        items: 1,
        margin: 20,
        dots: false,
        nav: true,
        navText: ["", ""],
        autoplay: true,
        autoplayTimeout: 4000,
      },
      768: {
        autoWidth: true,
        loop: true,
        center: true,
        items: 2,
        margin: 20,
        dots: false,
        nav: true,
        navText: ["", ""],
        autoplay: true,
        autoplayTimeout: 4000
      },
      992: {
        autoWidth: true,
        loop: true,
        center: false,
        items: 3,
        margin: 20,
        dots: false,
        nav: true,
        navText: ["", ""],
        // autoplay: true,
        // autoplayTimeout: 4000,
      }
    },
  })


  // скролл
  $(".token-top-info-transactions-body").mCustomScrollbar('scrollTo', ['-=0', '-=0']);
  $(".token-top-info-transactions-body").mCustomScrollbar({
    axis: "yx",
  });
  $(".token-top-info-transactions-body-table-row__item").mCustomScrollbar('scrollTo', '0%');
  $(".token-top-info-transactions-body-table-row__item").mCustomScrollbar({
    axis: "x",
  });

  // табы
  $(".token-top-info-tabs__item").click(function () {
    var tabNo = $(this).attr("data-tab-link-no");
    var tabs = $('.token-top-info-blocks-item:nth-child(' + tabNo + ')');
    $('.token-top-info-tabs__item').removeClass('token-top-info-tabs__item_active');
    $(this).addClass('token-top-info-tabs__item_active');
    $('.token-top-info-blocks-item').removeClass('token-top-info-blocks-item_show');
    tabs.addClass('token-top-info-blocks-item_show');
  });

  // копия по клику
  $(".token-top-info-wallets__input-copy").click(function () {
    console.log('123')
    var $temp = $("<input>");
    $("body").append($temp);
    $temp.val($(this).siblings('.token-top-info-wallets__input').val()).select();
    document.execCommand("copy");
    $temp.remove();
  });


  // паралакс
  $.stellar({
    hideDistantElements: false,
    horizontalScrolling: false,
  });


  // график
  var bonusChartBar = new Chartist.Bar('.bonus-chart', {
    labels: ['10', '20', '30', '40', '50', '60', '70', '80', '90', '100'],
    series: [15, 12, 10, 7.5, 5, 3.8, 2.5, 1.3, 0, 0] // данные графика сюда
  }, {
    distributeSeries: true,
  });
  bonusChartBar.on('draw', function (data) {
    if (data.type === 'bar') {
      data.element.animate({
        y2: {
          dur: 2500,
          from: data.y1,
          to: data.y2,
          easing: Chartist.Svg.Easing.easeOutQuint
        },
        opacity: {
          dur: 1000,
          from: 0,
          to: 1,
          easing: Chartist.Svg.Easing.easeOutQuint
        }
      });
      var barHorizontalCenter, barVerticalCenter, label, value;
      barHorizontalCenter = data.x1 + (data.element.width() * .5) + 1;
      barVerticalCenter = data.y2 - 3;
      value = data.element.attr('ct:value');
      if (value) {
        label = new Chartist.Svg('text');
        label.text(value + '%');
        label.addClass("ct-barlabel");
        label.attr({
          x: barHorizontalCenter,
          y: barVerticalCenter,
          'text-anchor': 'middle'
        });
        return data.group.append(label);
      }
    }
  });

  progressBar();
  function progressBar() {
    for(var u = 1; u < $('.initial-progress-item__bar').length + 1; u++) {
      var progressText;
      var progressWidth = $('.initial-progress-item:nth-child('+u+') .initial-progress-item__bar').attr('data-progress');
      $('.initial-progress-item:nth-child('+u+') .initial-progress-item__inside').animate({width: progressWidth+"%"}, {duration: 1500});
      if (progressWidth < 50) {
        $('.initial-progress-item:nth-child('+u+') .initial-progress-item__inside')
          .append('<span class="initial-progress-item__text initial-progress-item__text_white">'+progressWidth+"%"+'</span>');
          var textLeft = $('.initial-progress-item__bar').width()/100*progressWidth;
          $('.initial-progress-item:nth-child('+u+') .initial-progress-item__inside .initial-progress-item__text_white').css('left', textLeft+10+'px');
      } else {
        $('.initial-progress-item:nth-child('+u+') .initial-progress-item__inside')
          .append('<span class="initial-progress-item__text">'+progressWidth+"%"+'</span>');
      }
    }
  }


  // таймер
  function getTimeRemaining(endtime) {
    var t = Date.parse(endtime) - Date.parse(new Date());
    var seconds = Math.floor((t / 1000) % 60);
    var minutes = Math.floor((t / 1000 / 60) % 60);
    var hours = Math.floor((t / (1000 * 60 * 60)) % 24);
    var days = Math.floor(t / (1000 * 60 * 60 * 24));
    return {
      'total': t,
      'days': days,
      'hours': hours,
      'minutes': minutes,
      'seconds': seconds
    };
  }

  function initializeClock(div_class, endtime) {
    var clock = document.querySelector(div_class);
    var daysSpan = clock.querySelector('.token-top-timer-date__day-number');
    var hoursSpan = clock.querySelector('.token-top-timer-date__hours-number');
    var minutesSpan = clock.querySelector('.token-top-timer-date__min-number');
    var secondsSpan = clock.querySelector('.token-top-timer-date__sec-number');

    function updateClock() {
      var t = getTimeRemaining(endtime);

      daysSpan.innerHTML = t.days;
      hoursSpan.innerHTML = ('0' + t.hours).slice(-2);
      minutesSpan.innerHTML = ('0' + t.minutes).slice(-2);
      secondsSpan.innerHTML = ('0' + t.seconds).slice(-2);

      if (t.total <= 0) {
        clearInterval(timeinterval);
      }
    }
    updateClock();
    var timeinterval = setInterval(updateClock, 1000);
  }
  var deadline = "January 01 2018 00:00:00 GMT+0300"; //for Ukraine
  var deadline = new Date(Date.parse(new Date()) + 17 * 24 * 60 * 60 * 1000); // for endless timer
  initializeClock('.token-top-timer', deadline);

  
});