(function( $ ) {
  $.fn.webcTabs = function(options) {

    var settings = $.extend( {
      'links'      : $(this).find("a"),
      'tabContent' : $(".webcTab"),
      'hideFirst' : true
    }, options);

    if (settings.hideFirst) {
      settings.tabContent.not(":first-child").hide();
    }

    settings.links.click(function (e) {
      e.preventDefault();
      $(this).parent().addClass("active");
      $(this).parent().siblings().removeClass("active");
      var tab = $(this).attr("href");
      settings.tabContent.not(tab).css("display", "none");
      $(tab).fadeIn();
    });

  };
})(jQuery);