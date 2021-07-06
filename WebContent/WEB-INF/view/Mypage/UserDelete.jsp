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
<!-- CSS Just for demo purpose, don't include it in your project -->
<link href="/resources/assets/demo/demo.css" rel="stylesheet" />
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
							<b>탈퇴 확인</b>
						</h4>
					</div>
					<hr>
					<div class="card-body">

						<form action="/Mypage/UserDeleteProc.do"
							onsubmit="return check()" class="text-center"
							style="font-size: 22px">

							<span id="msg" class="txt1" style="color: #706c6e;"> <b>회원탈퇴를
									하면</b> <br> <b>다시 되돌릴 수 없으며,</b> <br> <b>모든 정보가
									삭제됩니다.</b> <br> <b>탈퇴를 계속 진행하시려면</b> <br> <b
								style="color: #ed4c8d;">' Account_withdrawal '</b> <br> <b>를
									입력해주세요.</b>
							</span> <br> <br> <br> <input class="text-center"
								type="text" id="deleteCheck" placeholder="Account_withdrawal"
								required> <br> <br> <input
								style="float: right;" class="btn btn-success btn-block btn-lg"
								type="submit" value="확인"> <input type="button"
								onclick="location.href='/Setting/Mypage.do'"
								class="btn btn-warning btn-block btn-lg" value="취소"> <br>
						</form>
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
	<!-- Control Center for Now Ui Dashboard: parallax effects, scripts for the example pages etc -->
	<script src="/resources/assets/js/paper-dashboard.min.js?v=2.0.1"
		type="text/javascript"></script>
	<!-- Paper Dashboard DEMO methods, don't include it in your project! -->
	<script src="/resources/assets/demo/demo.js"></script>
	<script>
		var userDeleteCheck = 'N'

		$("#deleteCheck").keyup(function() {
			var query = {
				DeleteCheck : $("#deleteCheck").val()
			};

			$.ajax({
				url : "UserDeleteCheck.do",
				type : "post",
				data : query,
				success : function(data) {

					if (data == 1) {
						userDeleteCheck = 'Y'
					} else if (data == 0) {
						userDeleteCheck = 'N'
					}
				}
			}); // ajax 끝
		});

		function check() {
			if (userDeleteCheck == 'Y') {
				return true;
			} else if (userDeleteCheck == 'N') {
				alert("탈퇴 확인 문장이 틀렸습니다. 다시 입력해주세요.");
				$("#deleteCheck").val('');
				return false;
			}

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
