<!DOCTYPE html>
<html>

<head>

    <meta property="og:image" content="/theme/app/img/logo.png">
    <meta property="og:image:type" content="image/png">
    <meta property="og:image:width" content="1024">
    <meta property="og:image:height" content="1024">

    <link rel="stylesheet" href="/theme/app/header.min.css">
    <link rel="stylesheet" href="/theme/app/css/main.min.css">
    <link rel="stylesheet" href="/theme/app/css/custom.css">
    <link rel="stylesheet" href="/theme/app/css/fonts.min.css">

<script type="text/javascript">
	function submitForm(that) {

        var url_string = window.location.href
        var url = new URL(url_string);
        var c = url.searchParams.get("ref");
        document.getElementById('hidden_ref').value = c;

        that.parentNode.submit();
    }
</script>
</head>

<body>
	<!-- Custom HTML -->
	<header class="header">
		<div class="header__inner">
			<div class="header__logo">
				<a href="/">Exchange 1<br>free stock and crypto trading</a>
			</div>
			<div class="header__right">
				<!--
				<div class="header__lang header-lang">
					<span class="header-lang__open">ru</span>
					<ul class="header-lang__list">
						<li class="active">
							<a href="#">ru</a>
						</li>
						<li>
							<a href="#">en</a>
						</li>
					</ul>
				</div>-->

				    <a href="/trade#!/basic" class="btn btn--darken">Trade</a>
                    <a href="/trade#!/trade" class="btn btn--darken">Advanced</a>
                    <a href="/trade#!/login-form" class="btn btn--darken">Login</a>
			</div>
		</div>
	</header>
	<main class="content">

        <div class="hero" style="background-image: url(/theme/app/img/hope.jpg)">
			<div class="hero__inner">
				<div class="hero__text">
					<h1 class="hero__heading">Stock and crypto trading as it should be</h1>
					<p class="hero__descr">
						No commissions on trade, no commission on deposits and withdraws.
						Join financial revolution right now.
				</div>
				<div class="hero__stat">
					<div class="hero-stat-elem">
						<h3>56</h3>
						<p>Trading<br>stocks</p>
					</div>
					<div class="hero-stat-elem">
						<h3>20</h3>
						<p>Coins<br>for trading</p>
					</div>
				</div>

				<div class="hero__text">
					<form type="POST" action="/accounts/create-referral" style="margin-top: -30px;">
                        <p class="hero__descr">Get free tokens with your mail</p>
                        <input id="hidden_ref" name="hidden_ref" type="hidden"/>
					    <input name="mail" style="font-size: 14px; height: 30px; width: 300px;" value=""/>
                        <a href='#' class="btn btn--lg" onclick='submitForm(this);return false;'>Sign</a>
					</form>
				</div>

			</div>
		</div>
		<div class="versus">
			<div class="versus__inner">
				<div class="versus__icons">
					<span class="versus__ico">
						<img src="/theme/app/img/quality-ico.png" alt="quality">
					</span>
					<span class="versus__ico">
						<img src="/theme/app/img/flash-ico.png" alt="quality">
					</span>
					<span class="versus__ico">
						<img src="/theme/app/img/man-ico.png" alt="quality">
					</span>
					<span class="versus__ico">
						<img src="/theme/app/img/quant-ico.png" alt="quality">
					</span>
				</div>
				<div class="versus__descr">
					<h2>Why Exchange 1</h2>
					<p>

						Trading went in the wrong way. We believe that trading should be free and simple.
						We created simple trading platform for everyone.
						We list startups for free. We don't charge commissions for trading.
						We don't take money on deposits
						and withdraws.
					</p>
				</div>
			</div>
		</div>
		<div class="make-invest">
			<div class="make-invest__inner">
				<div class="make-invest__descr">
					<h2>Our mission</h2>
					<blockquote>Our mission is to disrupt finance, industry should work for you, not you for it</blockquote>
					<ul>
						<li>Stocks and cryptocurrencies in a simple way</li>

						<li>No previous experience needed</li>

						<li>Free trading</li>
					</ul>
				</div>
				<!--
				<div class="make-invest__icons">
					<span class="make-invest__ico">
						<img src="/theme/app/img/stone1.png" alt="stone">
						<span>Princess</span>
					</span>
					<span class="make-invest__ico">
						<img src="/theme/app/img/stone2.png" alt="stone">
						<span>Radian</span>
					</span>
					<span class="make-invest__ico">
						<img src="/theme/app/img/stone3.png" alt="stone">
						<span>Assher</span>
					</span>
					<span class="make-invest__ico">
						<img src="/theme/app/img/stone4.png" alt="stone">
						<span>Cushion</span>
					</span>
					<span class="make-invest__ico">
						<img src="/theme/app/img/stone5.png" alt="stone">
						<span>Oval</span>
					</span>
					<span class="make-invest__ico">
						<img src="/theme/app/img/stone6.png" alt="stone">
						<span>Emerald</span>
					</span>
					<span class="make-invest__ico">
						<img src="/theme/app/img/stone7.png" alt="stone">
						<span>Rear</span>
					</span>
					<span class="make-invest__ico">
						<img src="/theme/app/img/stone8.png" alt="stone">
						<span>Marquis</span>
					</span>
					<span class="make-invest__ico">
						<img src="/theme/app/img/stone9.png" alt="stone">
						<span>Heart</span>
					</span>
				</div>-->
			</div>
		</div>
		<div class="ctablock">
			<div class="ctablock__inner">
				<div class="ctablock__content">
					<h2>Choose coins</h2>
					<a href="/trade" class="btn btn--lg">Start trading</a>
				</div>
			</div>
		</div>
		<!--
		<div class="gray-bg">
			<div class="feedback">
				<div class="feedback__inner">
					<div class="feedback__slider feedback-slider">
						<div class="owl-carousel feedback-slider__carousel f-slider-carousel">
							<div class="f-slider-carousel__item">
								<span class="f-slider-carousel__image" style="background-image: url(/theme/app/img/feedback.jpg)"></span>
								<div class="f-slider-carousel__info">
									<h3>John Wailters</h3>
									<p>Marketing specialist<br>39 years</p>
								</div>
								<div class="f-slider-carousel__text">
									<p>"This is a unique service for a new type of investment.
										Diamonds are more accessible than ever."</p>
								</div>
							</div>
							<div class="f-slider-carousel__item">
								<span class="f-slider-carousel__image" style="background-image: url(/theme/app/img/man-photo.jpeg)"></span>
								<div class="f-slider-carousel__info">
									<h3>Andrew Broklovsky</h3>
									<p>Professional trader<br>43 года</p>
								</div>
								<div class="f-slider-carousel__text">
									<p>"An exciting tool for anyone who is truly focused on investment and profit."</p>
								</div>
							</div>
						</div>
						<div class="feedback-slider__ctrls">
							<button class="feedback-slider__btn feedback-slider__btn--next"></button>
							<button class="feedback-slider__btn feedback-slider__btn--prev"></button>
						</div>
					</div>
				</div>
			</div>
			<div class="sert">
				<div class="sert__inner">

					<h2 class="sert__heading">Certificates</h2>
					<div class="owl-carousel def-dots sert__carousel sert-carousel">
						<div class="sert-carousel__item">
							<div>
								<img src="/theme/app/img/brand1.png" alt="brand">
							</div>
						</div>
						<div class="sert-carousel__item">
							<div>
								<img src="/theme/app/img/brand2.png" alt="brand">
							</div>
						</div>
						<div class="sert-carousel__item">
							<div>
								<img src="/theme/app/img/brand3.png" alt="brand">
							</div>
						</div>
						<div class="sert-carousel__item">
							<div>
								<img src="/theme/app/img/brand4.png" alt="brand">
							</div>
						</div>
						<div class="sert-carousel__item">
							<div>
								<img src="/theme/app/img/brand4.png" alt="brand">
							</div>
						</div>
					</div>

				</div>
			</div>
		</div>
		-->

	</main>
	<div class="footer">
		<div class="footer__inner">
			<div class="footer__content">
				<p class="footer__copy">Crypto and stock trading</p>
				<img src="/theme/app/img/cards.png" alt="cards" class="footer__card">
			</div>
		</div>
	</div>

</body>
</html>
