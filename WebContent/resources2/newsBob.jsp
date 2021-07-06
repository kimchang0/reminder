<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String heraldTitle = "";
String reutersTitle = "";
String timesTitle = "";
String yonhapTitle = "";
String heraldImg = (String)request.getAttribute("heraldImg");
%>
<!DOCTYPE html>
<html lang="en">
<head>
<style>
.slide:nth-child(1) .slide__inner {
	background-image:
	<%if(heraldImg!=null){%>
		url("<%=heraldImg %>") !important;
	<%}else{%>
		url("https://s3-us-west-2.amazonaws.com/s.cdpn.io/142996/onepgscr-2.jpg") !important;
	<%}%>
}

.slide:nth-child(2) .slide__inner {
	background-image:
		url("https://s3-us-west-2.amazonaws.com/s.cdpn.io/142996/onepgscr-3.jpg") !important;
}

.slide:nth-child(3) .slide__inner {
	background-image:
		url("https://s3-us-west-2.amazonaws.com/s.cdpn.io/142996/onepgscr-4.jpg") !important;
}

.slide:nth-child(4) .slide__inner {
	background-image:
		url("https://s3-us-west-2.amazonaws.com/s.cdpn.io/142996/onepgscr-5.jpg") !important;
}
</style>
<link rel="stylesheet" href="/resources/scss/cardBob.css">
<meta charset="UTF-8">
<title>Document</title>
<script>

</script>
</head>
<body>
	<div class="slider">
		<!-- slides -->
		<div class="slider__slides">
			<div class="slide s--active">
				<div class="slide__inner">
					<form target="_parent" action="/Today/TodayNews.do">
						<span onclick="document.forms[0].submit()" style="cursor: pointer">
							<div class="slide__content">
								<h2 class="slide__heading">Korea Herald</h2>
								<p class="slide__text">
									<script>
										document.write(heraldTitle);
									</script>
								</p>
							</div>
						</span> <input type="hidden" name="news_name" value="herald">
					</form>
				</div>
			</div>
			<div class="slide">
				<div class="slide__inner">
					<form target="_parent" action="/Today/TodayNews.do">
						<span onclick="document.forms[1].submit()" style="cursor: pointer">
							<div class="slide__content">
								<h2 class="slide__heading">Reuters</h2>
								<p class="slide__text">
									<script>
										document.write(reutersTitle);
									</script>
								</p>
							</div>
						</span> <input type="hidden" name="news_name" value="reuters">
					</form>
				</div>
			</div>
			<div class="slide">
				<div class="slide__inner">
					<form target="_parent" action="/Today/TodayNews.do">
						<span onclick="document.forms[2].submit()" style="cursor: pointer">
							<div class="slide__content">
								<h2 class="slide__heading">Korea Times</h2>
								<p class="slide__text">
									<script>
										document.write(timesTitle);
									</script>
								</p>
							</div>
						</span> <input type="hidden" name="news_name" value="times">
					</form>
				</div>
			</div>
			<div class="slide s--prev">
				<div class="slide__inner">
					<form target="_parent" action="/Today/TodayNews.do">
						<span onclick="document.forms[3].submit()" style="cursor: pointer">
							<div class="slide__content">
								<h2 class="slide__heading">Yonhap News</h2>
								<p class="slide__text">
									<script>
										document.write(yonhapTitle);
									</script>
								</p>
							</div>
						</span> <input type="hidden" name="news_name" value="yonhap">
					</form>
				</div>
			</div>
		</div>
		<!-- slides end -->
		<div class="slider__control">
			<div class="slider__control-line"></div>
			<div class="slider__control-line"></div>
		</div>
		<div class="slider__control slider__control--right m--right">
			<div class="slider__control-line"></div>
			<div class="slider__control-line"></div>
		</div>
	</div>
</body>
<script>
	(function() {
		var $slides = document.querySelectorAll('.slide');
		var $controls = document.querySelectorAll('.slider__control');
		var numOfSlides = $slides.length;
		var slidingAT = 1300; // sync this with scss variable
		var slidingBlocked = false;

		[].slice.call($slides).forEach(function($el, index) {
			var i = index + 1;
			$el.classList.add('slide-' + i);
			$el.dataset.slide = i;
		});

		[].slice.call($controls).forEach(function($el) {
			$el.addEventListener('click', controlClickHandler);
		});

		function controlClickHandler() {
			if (slidingBlocked)
				return;
			slidingBlocked = true;

			var $control = this;
			var isRight = $control.classList.contains('m--right');
			var $curActive = document.querySelector('.slide.s--active');
			var index = +$curActive.dataset.slide;
			(isRight) ? index++ : index--;
			if (index < 1)
				index = numOfSlides;
			if (index > numOfSlides)
				index = 1;
			var $newActive = document.querySelector('.slide-' + index);

			$control.classList.add('a--rotation');
			$curActive.classList.remove('s--active', 's--active-prev');
			document.querySelector('.slide.s--prev').classList
					.remove('s--prev');

			$newActive.classList.add('s--active');
			if (!isRight)
				$newActive.classList.add('s--active-prev');

			var prevIndex = index - 1;
			if (prevIndex < 1)
				prevIndex = numOfSlides;

			document.querySelector('.slide-' + prevIndex).classList
					.add('s--prev');

			setTimeout(function() {
				$control.classList.remove('a--rotation');
				slidingBlocked = false;
			}, slidingAT * 0.75);
		}
		;
	}());
</script>

</html>
