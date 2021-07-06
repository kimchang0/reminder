<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


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
<link rel="stylesheet" href="/resources/scss/Button.css">
</head>

<body class="">
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
							<b>회원정보 수정</b>
						</h4>
					</div>
					<hr>
					<div class="card-body">

						<div style="text-align: left; font-size: 18px;">
							<form action="/Setting/Mypage.do" onsubmit="return check()">

								<input type="password" id="pwdCheck"> <br> 
								<span id="msg">비밀번호를 입력해주세요.</span>
								<br> <br> 
								<input class="btn btn-success btn-block btn-lg" type="submit" value="확인"> 
								<input type="button" onclick="move_back()" class="btn btn-warning btn-block btn-lg" value="뒤로"> <br>


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
	<!-- Chart JS -->
	<script src="/resources/assets/js/plugins/chartjs.min.js"></script>
	<!--  Notifications Plugin    -->
	<script src="/resources/assets/js/plugins/bootstrap-notify.js"></script>
	<script>
	
		function showNotification() {
		    color = 'danger';
		
		    $.notify({
		      icon: "nc-icon nc-bell-55",
		      message: "비밀번호를 확인해주세요"
		
		    }, {
		      type: color,
		      delay: 600,
		      timer: 500,
		      placement: {
		        from: 'top',
		        align: 'center'
		      }
		    });
		 }
		
		var PasswordCheck = 'N'

		$("#pwdCheck").keyup(function() {
			var query = {
				pwd : $("#pwdCheck").val()
			};

			$.ajax({
				url : "MypageCheckProc.do",
				type : "post",
				data : query,
				success : function(data) {

					if (data == 1) {
						PasswordCheck = 'Y'
					} else if (data == 0) {
						PasswordCheck = 'N'
					}
				}
			}); // ajax 끝
		});

		function check() {
			if (PasswordCheck == 'Y') {
				return true;
			} else if (PasswordCheck == 'N') {
				showNotification()
				return false;
			}

		}
		
		function move_back(){
			location.href="/Setting/setting.do";
		}

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
