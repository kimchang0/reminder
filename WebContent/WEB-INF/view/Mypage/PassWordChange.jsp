<%@page import="static poly.util.CmmUtil.nvl"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String userId = nvl((String) session.getAttribute("user_id"));
%>

<!DOCTYPE html>
<html lang="en">
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<head>
<meta charset="utf-8" />
<link rel="apple-touch-icon" sizes="76x76"
	href="/resources/assets/img/apple-icon.png">
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
<link rel="stylesheet" type="text/css"
	href="/resources/fonts/font-awesome-4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css"
	href="/resources/fonts/Linearicons-Free-v1.0.0/icon-font.min.css">
</head>

<body class="" style="background-color: #F3F4EF">
	<div class="wrapper">
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
			<!-- End Navbar -->

			<div class="content">
				<div class="card">
					<div class="card-header">
						<h4 class="mt-0 mb-0 text-center">
							<b>비밀번호 변경</b>
						</h4>
					</div>
					<hr>
					<div class="card-body">

						<div style="text-align: left; font-size: 15px;">
							<form action="/Mypage/passWordChangeProc.do"
								onsubmit="return check()">

								현재 비밀번호: <input type="password" id="current_Password" required><br>
								<span id="past">현재 비밀번호를 입력해주세요.</span><br><br>
								신규 비밀번호: <input type="password" id="newPassWord" name="pwd" required><br> 
								<span class="new">신규 비밀번호를 입력해주세요.</span><br><br> 
								비밀번호 확인: <input type="password" id="passWordCheck" required><br> 
								<span class="renew">비밀번호 확인을 위해 한 번 더 입력해주세요.</span><br><br>
								
								<input type="submit"class="btn btn-success btn-block btn-lg" value="수정하기"> 
								<input type="button" onclick="location.href='/Setting/Mypage.do'" class="btn btn-warning btn-block btn-lg" value="뒤로">

							</form>
						</div>
					</div>
				</div>
			</div>

		</div>
	</div>
	<!--   Core JS Files   -->
	<script src="/resources/assets/js/core/jquery.min.js"></script>
	<script src="/resources/assets/js/core/popper.min.js"></script>
	<script src="/resources/assets/js/core/bootstrap.min.js"></script>
	<script
		src="/resources/assets/js/plugins/perfect-scrollbar.jquery.min.js"></script>
	<!--  Google Maps Plugin    -->
	<script src="https://maps.googleapis.com/maps/api/js?key=YOUR_KEY_HERE"></script>
	<!-- Chart JS -->
	<script src="/resources/assets/js/plugins/chartjs.min.js"></script>
	<!--  Notifications Plugin    -->
	<script src="/resources/assets/js/plugins/bootstrap-notify.js"></script>
	<script>
	
	var pastPwd = 'N';
	var newPwd = 'N';

	function check(){
		if(pastPwd == 'Y' && newPwd == 'Y') {
			return true;
		} else if(pastPwd == 'N' && newPwd == 'N') {
			alert("비밀번호를 입력해주세요.");
			return false;
		} else if(pastPwd == 'N') {
			alert("현재 비밀번호가 다릅니다.");
			return false;
		} else if($("#newPassWord").val() != $("#passWordCheck").val()){
			alert("바꾸실 비밀번호가 일치하지 않습니다. 다시 한 번 확인해주세요.");
			return false;
		} else if(newPwd == 'N') {
			alert("바꾸실 비밀번호를 입력해주세요.");
			return false;
		}
	}

	$("#current_Password").keyup(function() {
		var query = {
			pwd : $("#current_Password").val()
		};

		$.ajax({
			url : "passWordCheck.do",
			type : "post",
			data : query,
			success : function(data) {
			
				if (data == 1) {
					$("#past").text("현재 비밀번호가 일치합니다.");
					$("#past").attr("style", "color:#00f");
					pastPwd = 'Y';
				} else if(data == 0) {
					$("#past").text("현재 비밀번호와 다릅니다.");
					$("#past").attr("style", "color:#f00");
					pastPwd = 'N';
				}
			}
		}); // ajax 끝
	});
	
	
		$('#passWordCheck').keyup(function() {

			var pw = document.getElementById("newPassWord").value; //비밀번호

			var pw2 = document.getElementById("passWordCheck").value; // 확인 비밀번호

			if (pw != '' && pw2 == '') {
				null;
			} else if (pw != "" || pw2 != "") {
				if (pw == pw2) {
					$(".renew").text("비밀번호가 일치합니다.");
					$(".renew").css("color", "#00f");
					newPwd = 'Y';
				} else {
					$(".renew").text("비밀번호가 일치하지 않습니다.");
					$(".renew").css("color", "#f00");
					newPwd = 'N';
				}
			}

		})

		$("#navbar-toggler").on('click', function() {
			if ($(this).hasClass("toggled")) {
				$(this).removeClass("toggled");
				$("html").first().removeClass("nav-open");
			} else {
				$(this).addClass("toggled");
				$("html").first().addClass("nav-open");

			}

		})
	</script>

</body>

</html>
