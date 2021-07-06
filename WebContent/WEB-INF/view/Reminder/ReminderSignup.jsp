<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
<title>SingUp</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<!--===============================================================================================-->
<link rel="icon" type="image/png"
	href="../resources2/images/icons/favicon.ico" />
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css"
	href="../resources2/vendor/bootstrap/css/bootstrap.min.css" />
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css"
	href="../resources2/fonts/font-awesome-4.7.0/css/font-awesome.min.css" />
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css"
	href="../resources2/fonts/iconic/css/material-design-iconic-font.min.css" />
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css"
	href="../resources2/vendor/animate/animate.css" />
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css"
	href="../resources2/vendor/css-hamburgers/hamburgers.min.css" />
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css"
	href="../resources2/vendor/animsition/css/animsition.min.css" />
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css"
	href="../resources2/vendor/select2/select2.min.css" />
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css"
	href="../resources2/vendor/daterangepicker/daterangepicker.css" />
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css" href="../resources2/css/util.css" />
<link rel="stylesheet" type="text/css" href="../resources2/css/main.css" />
<!--===============================================================================================-->
<style>
.bline {
	border-bottom: 2px solid #d9d9d9;
	padding-bottom: 15px;
	margin-bottom: 10px;
}

.m-b-15{
	font-size:13px;
}

</style>
</head>
<body>
	<div class="limiter">
		<div class="container-login100" style="background-color: #f4f3ef">
			<div class="wrap-login100 p-l-55 p-r-55 p-t-65 p-b-54 shadowbox">
				<form class="login100-form validate-form"
					action="/Reminder/ReminderSignUpProc.do" method="post">
					<span class="login100-form-title p-b-49">Sign up </span>

					<div class="wrap-input100 validate-input "
						data-validate="ID is reauired">
						<span class="label-input100">ID</span> <input class="input100"
							type="text" name="id" id="userId" placeholder="Type your ID" />
						<span class="focus-input100" data-symbol="&#xf206;"></span>
					</div>
					<div class="msg m-b-15"></div>

					<div class="wrap-input100 validate-input "
						data-validate="Passwrod is reauired">
						<span class="label-input100">PASSWORD</span> <input
							class="input100" type="password" name="pwd" id="newPassWord"
							placeholder="Type your Password" /> <span class="focus-input100"
							data-symbol="&#xf190;"></span>
					</div>
					<div class="new m-b-15"></div> 
					<div class="wrap-input100 validate-input "
						data-validate="PasswrodCheck is reauired">
						<span class="label-input100">PASSWROD CHECK</span> <input
							class="input100" type="password" name="pwd2" id="passWordCheck"
							placeholder="Type your Password Check" /> <span
							class="focus-input100" data-symbol="&#xf190;"></span>
					</div>
					<div class="renew m-b-15"></div> 

					<div class="wrap-input100 validate-input "
						data-validate="Email is reauired">
						<span class="label-input100">EMAIL</span> <input class="input100"
							type="email" name="email" id="userEmail"
							placeholder="Type your email" /> <span class="focus-input100"
							data-symbol="&#9993;"></span>
					</div>
					<div class="msg2 m-b-15"></div>

					<div class="wrap-input100 validate-input "
						data-validate="interest is reauired">
						<span class="label-input100">THINGS</span> <input class="input100"
							type="text" name="interest" id="interest"
							placeholder="type your forget frequently.ex) 지갑, 차키" /> <span class="focus-input100"
							data-symbol="&#9993;"></span>
					</div>

					<div class="text-right p-t-8 p-b-31"></div>

					<div class="container-login100-form-btn">
						<div class="wrap-login100-form-btn">
							<div class="login100-form-bgbtn"></div>
							<button class="login100-form-btn btn" type="submit">
								SIGN UP</button>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>

	<div id="dropDownSelect1"></div>

	<!--===============================================================================================-->
	<script src="../resources2/vendor/jquery/jquery-3.2.1.min.js"></script>
	<!--===============================================================================================-->
	<script src="../resources2/vendor/animsition/js/animsition.min.js"></script>
	<!--===============================================================================================-->
	<script src="../resources2/vendor/bootstrap/js/popper.js"></script>
	<script src="../resources2/vendor/bootstrap/js/bootstrap.min.js"></script>
	<!--===============================================================================================-->
	<script src="../resources2/vendor/select2/select2.min.js"></script>
	<!--===============================================================================================-->
	<script src="../resources2/vendor/daterangepicker/moment.min.js"></script>
	<script src="../resources2/vendor/daterangepicker/daterangepicker.js"></script>
	<!--===============================================================================================-->
	<script src="../resources2/vendor/countdowntime/countdowntime.js"></script>
	<!--===============================================================================================-->
	<script src="../resources2/js/main.js"></script>

</body>
<script>
	
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
	
	var doCheck = 'N'
	
	$("#userId").keyup(function() {
		var query = {
			userId : $("#userId").val()
		};

		$.ajax({
			url : "idCheck.do",
			type : "post",
			data : query,
			success : function(data) {
				if (data == 1) {
					$(".msg").text("사용하고 있는 아이디입니다.");
					$(".msg").attr("style", "color:#f00");
					doCheck = 'N'
				} else {
					$(".msg").text("사용 가능한 아이디입니다.");
					$(".msg").attr("style", "color:#00f");
					//$('#userId').attr("disabled", true);
					doCheck = 'Y'
				}
			}
		}); // ajax 끝
	});
	
	function check() {
		if (doCheck == 'N') {
			alert("사용중인 아이디입니다.")
			return false;
		}
	}

	//이메일 중복확인
	var doCheck = 'N'
	$("#userEmail").keyup(function() {
		var query = {
			userEmail : $("#userEmail").val()
		};

		$.ajax({
			url : "emailCheck.do",
			type : "post",
			data : query,
			success : function(data) {

				if (data == 1) {
					$(".msg2").text("사용하고 있는 이메일입니다.");
					$(".msg2").attr("style", "color:#f00");
					doCheck = 'N'
				} else {
					$(".msg2").text("사용 가능한 이메일입니다.");
					$(".msg2").attr("style", "color:#00f");
					doCheck = 'Y'
				}
			}
		}); // ajax 끝
	});

	function check() {
		if (doCheck == 'N') {
			alert("입력을 다시 확인해주세요.")
			return false;
		}
	}
</script>
</html>
