$(function() {

	$(".header-lang__open").click(function () {
		$(this).parent().children(".header-lang__list").slideToggle();
	});

	$(".header-lang").click(function (e) {
		e.stopPropagation();
	});

	$(document).click(function () {
		$(".header-lang__list").slideUp();
	});

	var fsliderCarousel = $(".f-slider-carousel").owlCarousel({
		loop: true,
		margin: 0,
		nav: false,
		dots: false,
		items: 1,
		margin: 10
	});

	$(".feedback-slider__btn--next").click(function () {
		fsliderCarousel.trigger('next.owl.carousel');
	});

	$(".feedback-slider__btn--prev").click(function () {
		fsliderCarousel.trigger('prev.owl.carousel');
	});

	$(".sert-carousel").owlCarousel({
	    loop: true,
	    margin: 0,
	    nav: false,
	    dots: true,
	    responsive:{
	        0:{
	            items:1
	        },
	        600:{
	            items:3
	        },
	        1200:{
	            items:4
	        }
	    }
	});

	$("[data-open]").click(function (e) {
		e.preventDefault();
		var show = $(this).attr("data-open");
		$("tr[data-show='"+show+"']").toggle();
	});

	if ($(".chart").length) {
		$('.donut-chart').cssCharts({type:"donut"}).trigger('show-donut-chart');

		$(".donut-chart").each(function () {
			var setValue = $(this).attr("data-val");
			$(this).children("h2").children("p").empty().append(setValue);
		});
	}

	$(".hero-form__tabslist").webcTabs({
		links : $(".lk-tabs-heading a"),
		tabContent : $(".lk-main-tab"),
		hideFirst : true
	});

	$(".sep-cab-tabs--tables").webcTabs({
		links : $(".sep-cab-tabs--tables a"),
		tabContent : $(".sep-cab-tab"),
		hideFirst : true
	});

	$(".sep-cab-tabs--trade").webcTabs({
		links : $(".sep-cab-tabs--trade a"),
		tabContent : $(".trades-tab"),
		hideFirst : true
	});

	$(".sep-cab-tabs--form").webcTabs({
		links : $(".sep-cab-tabs--form a"),
		tabContent : $(".form-tabs__tab"),
		hideFirst : true
	});

	$(".trades-tab, .type-diamond-table").niceScroll({
		cursoropacitymin: 1,
		cursorcolor: "#eaeaea",
		cursorwidth: "5px",
		cursorborder: "1px solid transparent",
		cursorborderradius: "0",
	});

	$(document).click(function () {
		$(".selectbox__list").slideUp();
	});

	$(".selectbox").click(function (e) {
		e.stopPropagation();
	});

	$(".selectbox__heading").click(function () {
		$(".selectbox__list").not($(this).parent().children()).slideUp();
		$(this).parent().children(".selectbox__list").slideDown();
	});

	$(".selectbox__list a").click(function (e) {
		e.preventDefault();
		$(".selectbox__list").slideUp();
		var valAppend = $(this).text();
		$(this).parent().parent().parent().children(".selectbox__input").attr("value", valAppend);
		$(this).parent().parent().parent().children(".selectbox__heading").empty().append(valAppend);
	});

});
