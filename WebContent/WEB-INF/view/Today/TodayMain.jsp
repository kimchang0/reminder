<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String user_interest =  (String) request.getAttribute("user_interest");
	List<Map<String, String>> rList =  (List<Map<String, String>>) request.getAttribute("rList");
%>
<!DOCTYPE html>
<html lang="en">
<style>

</style>
<head>
<meta charset="utf-8" />
<link rel="apple-touch-icon" sizes="76x76"
	href="/WEB-INF/view/Today/newsSlide2.jsp">
<link rel="icon" type="image/png"
	href="/resources/assets/img/favicon.png">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>Reminder</title>
<meta
	content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0, shrink-to-fit=no'
	name='viewport' />
<!--     Fonts and icons     -->
<link
	href="https://fonts.googleapis.com/css?family=Montserrat:400,700,200"
	rel="stylesheet" />
<link
	href="https://maxcdn.bootstrapcdn.com/font-awesome/latest/css/font-awesome.min.css"
	rel="stylesheet">
<!-- CSS Files -->
<link href="/resources/assets/css/bootstrap.min.css" rel="stylesheet" />
<link href="/resources/assets/css/paper-dashboard.css?v=2.0.1"
	rel="stylesheet" />
<link rel="stylesheet" href="/resources/scss/Button.css">
<link rel="stylesheet" href="/resources/css/tempbtn.css" type="text/css">
</head>

<body class="">
	<div class="wrapper ">
		<%@ include file="/WEB-INF/view/sidebar.jsp"%>
		<div class="main-panel">
			<!-- Navbar -->
			<nav
				class="navbar navbar-expand-lg navbar-absolute fixed-top navbar-transparent">
				<div class="container-fluid">
					<div class="navbar-wrapper">
						<div class="navbar-toggle">
							<button type="button" class="navbar-toggler" id="navbar-toggler">
								<span class="navbar-toggler-bar bar1"></span> <span
									class="navbar-toggler-bar bar2"></span> <span
									class="navbar-toggler-bar bar3"></span>
							</button>
						</div>
						<a class="navbar-brand" href="/Today/TodayMain.do">Reminder</a>
					</div>
				</div>
			</nav>
			<!-- End Navbar------------------------------------------------------------------ -->

			<div class="content">

				<div class="card">
					<div class="card-header">
						<!-- <h4 class="mt-0 mb-0" style="color: orange; font-size: 15px">Trending</h4> -->
							<span style="font-size: 30px; margin:auto; float:left;"><b> Today's Important </b></span> 
							<!-- <img alt="add_list" src="/resources/images/plus.png" style=" height: 16px; width: 16px; float: right; vertical-align: middle; display: flex;" onclick="add_list();"> -->
							<button class="btncls" type="button" style="float:right;" onclick="move_add_list();">+</button>
					</div>
					<hr>
					<div class="card-body">
						<div>
						<%
						if(user_interest.equals("") || user_interest == null){
						%>
							<span id="important" style="font-size:18px; margin:auto; align:center;"> <b>오늘의 중요 물품이 없습니다. 중요 물품을 추가해주세요.</b></span>
						<%
						} else {
						%>
							<span id="important" style="font-size:18px; margin:auto; align:center;"> <b>오늘의 중요 물품은 <%=user_interest %> 입니다.</b></span>
						<%
						}
						%>
						</div>
					</div>
				</div>

				
				<div class="card">
					<div class="card-header">
						<!-- <h4 class="mt-0 mb-0" style="color: orange; font-size: 15px">Trending</h4> -->
							<span style="font-size: 30px; margin:auto; float:left;"><b> Today's Schedule </b></span> 
							<!-- <img alt="add_list" src="/resources/images/plus.png" style=" height: 16px; width: 16px; float: right; vertical-align: middle; display: flex;" onclick="add_list();"> -->
							<button class="btncls" type="button" style="float:right;" onclick="move_add_schedule();">+</button>
					</div>
					<hr>
					<div class="card-body">
						<div>
										<%
						if(rList.size() == 0){
						%>
							<span id="schedule" style="font-size:18px; margin:auto; align:center;"> <b>오늘의 일정이 없습니다. 일정을 추가해주세요.</b></span>
						<%
						} else {
						%>
							<span id="schedule" style="font-size:18px; margin:auto; align:center;"> <b>오늘의 일정은 <%for(Map<String, String> rMap : rList){ %><%=rMap.get("schedule_time") %>시 <%=rMap.get("schedule_minute") %>분에 <%=rMap.get("schedule_name") %>, <%} %>입니다.</b></span>
						<%
						}
						%>
						</div>
					</div>
				</div>
				
				<div class="card">
					<div class="card-header">
						<!-- <h4 class="mt-0 mb-0" style="color: orange; font-size: 15px">Trending</h4> -->
							<span style="font-size: 30px; margin:auto; float:left;"><b> PassWord's Inquiry </b></span> 
							<!-- <img alt="add_list" src="/resources/images/plus.png" style=" height: 16px; width: 16px; float: right; vertical-align: middle; display: flex;" onclick="add_list();"> -->
							<button class="btncls" type="button" style="float:right;" onclick="move_add_password();">+</button>
					</div>
					<hr>
					<div class="card-body">
						<div>
						
						</div>
					</div>
				</div>

			</div>
		</div>
	</div>

	<script src="/js/annyang.js"></script>
	<script>
	
		$("#navbar-toggler").on('click', function() {
			if ($(this).hasClass("toggled")) {
				$(this).removeClass("toggled");
				$("html").first().removeClass("nav-open");
			} else {
				$(this).addClass("toggled");
				$("html").first().addClass("nav-open");
			}
		})
		
		function move_add_list(){
			console.log("move_add_list 실행");
			location.href="/Today/TodayAddList.do";
		}
		
		function move_add_schedule(){
			console.log("move_add_schedule 실행");
			location.href="/Schedule/ScheduleList.do"
		}
		
		function move_add_password() {
			console.log("move_add_password 실행")
			location.href="/Site/SiteList.do";
		}

		document.addEventListener('scroll', function() {
			let currentScrollValue = document.documentElement.scrollTop;
			console.log('currentScrollValue is ' + currentScrollValue);
		});
	</script>
	<script>
	
		let mainAudio;
		let importantAudio;
		let scheduleAudio;

		annyang.start({
			autoRestart : true,
			continuous : true
		});

		let recognition = annyang.getSpeechRecognizer();

		let final_transcript = "";

		recognition.interimResults = true;

		recognition.onresult = function(event) {
			
			final_transcript = "";
			
			for (let i = event.resultIndex; i < event.results.length; ++i) {
				if (event.results[i].isFinal) {
					final_transcript += event.results[i][0].transcript;
				}
			}
			
			final_transcript = final_transcript.trim();
			
			console.log("final_transcript: " + final_transcript);
			
			if (final_transcript == "명령어") {

				console.log("명령어 실행");
				mainAudio = new Audio('/voice/getMainvoice.do');
				mainAudio.load();
				mainAudio.play();

			} else if (final_transcript == "물품") {

				console.log("물품 실행");
				importantAudio = new Audio('/voice/getImportantVoice.do');
				importantAudio.load();
				importantAudio.play();

			} else if (final_transcript == "일정") {

				console.log("일정 실행");
				scheduleAudio = new Audio('/voice/getScheduleVoice.do');
				scheduleAudio.load();
				scheduleAudio.play();

			} else if (final_transcript == "물품 추가") {

				console.log("물품추가 실행");
				move_add_list();

			} else if (final_transcript == "일정 추가") {

				console.log("일정추가 실행");
				move_add_schedule();

			} else if (final_transcript == "비밀번호 조회") {

				console.log("비밀번호 조회 실행");
				move_add_password();

			}
		}

		window.addEventListener('DOMContentLoaded', function() {

			let important = document.getElementById('important');
			let schedule = document.getElementById('schedule');

			let query = {
				important : important.innerText
			};

			$.ajax({
				url : "/voice/insertImportantVoice.do",
				type : "post",
				data : query,
				success : function(data) {
					if (data == 'success') {
						console.log("important TTS 생성 성공");
					}
				}
			}); // ajax 끝

			query = {
				schedule : schedule.innerText
			};

			$.ajax({
				url : "/voice/insertScheduleVoice.do",
				type : "post",
				data : query,
				success : function(data) {
					if (data == 'success') {
						console.log("schedule TTS 생성 성공");
					}
				}
			}); // ajax 끝
		});
	</script>
</body>

</html>