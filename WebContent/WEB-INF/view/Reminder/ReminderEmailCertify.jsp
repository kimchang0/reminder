<%@page import="static poly.util.CmmUtil.nvl"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	String user_email = nvl((String)session.getAttribute("user_email"));    
%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <title>email</title>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <!--===============================================================================================-->
    <link rel="icon" type="image/png" href="../resources2/images/icons/favicon.ico" />
    <!--===============================================================================================-->
    <link
      rel="stylesheet"
      type="text/css"
      href="../resources2/vendor/bootstrap/css/bootstrap.min.css"
    />
    <!--===============================================================================================-->
    <link
      rel="stylesheet"
      type="text/css"
      href="../resources2/fonts/font-awesome-4.7.0/css/font-awesome.min.css"
    />
    <!--===============================================================================================-->
    <link
      rel="stylesheet"
      type="text/css"
      href="../resources2/fonts/iconic/css/material-design-iconic-font.min.css"
    />
    <!--===============================================================================================-->
    <link rel="stylesheet" type="text/css" href="../resources2/vendor/animate/animate.css" />
    <!--===============================================================================================-->
    <link
      rel="stylesheet"
      type="text/css"
      href="../resources2/vendor/css-hamburgers/hamburgers.min.css"
    />
    <!--===============================================================================================-->
    <link
      rel="stylesheet"
      type="text/css"
      href="../resources2/vendor/animsition/css/animsition.min.css"
    />
    <!--===============================================================================================-->
    <link
      rel="stylesheet"
      type="text/css"
      href="../resources2/vendor/select2/select2.min.css"
    />
    <!--===============================================================================================-->
    <link
      rel="stylesheet"
      type="text/css"
      href="../resources2/vendor/daterangepicker/daterangepicker.css"
    />
    <!--===============================================================================================-->
    <link rel="stylesheet" type="text/css" href="../resources2/css/util.css" />
    <link rel="stylesheet" type="text/css" href="../resources2/css/main.css" />
    <!--===============================================================================================-->
    <style>
      select {
        appearance: none;
        -webkit-appearance: none;
      }
      select::-ms-expand {
        display: none;
      }
      .fbox {
        display: flex;
        justify-content: space-between;
        align-items: flex-end;
      }

      .emailbtn {
        background-color: lightblue;
      }
    </style>
  </head>
  <body>
    <div class="limiter">
      <div class="container-login100" style="background-color: #f4f3ef">
        <div class="wrap-login100 p-l-55 p-r-55 p-t-65 p-b-54 shadowbox">
          <form
            action="/Reminder/ReminderLogin.do"
            method="post"
            onsubmit="return check()"
            class="login100-form validate-form">
            <div
              class="wrap-input100 validate-input m-b-23"
              data-validate="Email is reauired"
              style="height: 107px"
            >
              <div class="fbox">
                <span class="label-input100">EMAIL</span>
                <button type="button" id="send" class="btn emailbtn">
                  ????????????
                </button>
              </div>
              <input
              	value="<%=user_email %>" 
                class="input100"
                type="email"
                name="email"
                id="userEmail"
                placeholder="Type your email"
              />
              <span class="focus-input100" data-symbol="&#9993;"></span>
            </div>

            <div
              class="wrap-input100 validate-input m-b-23"
              data-validate="Security Code is reauired"
              style="height: 107px"
            >
              <div class="fbox">
                <span class="label-input100">Security Code</span>
                <button type="button" id="complete" class="btn emailbtn">
                  ????????????
                </button>
              </div>
              <input
                class="input100"
                type="text"
                name="auth"
                id="auth"
                numberOnly="true"
                maxlength="6"
                placeholder="Type your Security Code"
              />
              <span class="focus-input100" data-symbol="&#xf190;"></span>
            </div>
            <span class="msg">???????????? ?????? ??? ??????????????? ???????????????.</span>
            <br><br>

            <div class="container-login100-form-btn">
              <div class="wrap-login100-form-btn">
                <div class="login100-form-bgbtn"></div>
                <button type="submit" class="login100-form-btn btn">
                  Done
                </button>
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

var authCheck = 'N'

	
$('#send').click(function() {
	console.log("click");
	var query = {
		
			email : $("#userEmail").val()
			
		};

		$.ajax({
			url : "ReminderEmailCertifyProc.do",
			type : "post",
			data : query,
			success : function(data) {
				console.log("data");
				if(data == 1) {
					$("#auth").attr("able", "ture");
					alert("????????? ????????? ?????????????????????. 10??? ?????? ????????? ??????????????????.");
				} else {
					$("#auth").attr("disabled", "true");
					alert("?????? ?????????????????? ???????????? ??? ?????? ????????? ???????????????.");
				}
			}
		}); // ajax ???
});

$(document).on("keyup", "input:text[numberOnly]", function() {
	$(this).val( $(this).val().replace(/[^0-9]/gi,"") );
	});

$('#complete').click(function() {
	var query = {
			auth : $("#auth").val()
		};

		$.ajax({
			url : "authNumCheck.do",
			type : "post",
			data : query,
			success : function(data) {
				if (data == 1) {
					$(".msg").text("?????? ??????.");
					$(".msg").attr("style", "color:#00f");
					authCheck = 'Y'
					
				} else {
					$(".msg").text("?????? ????????? ????????????. ?????? ??????????????????.");
					$(".msg").attr("style", "color:#f00");
					authCheck = 'N'
				}
			}}); // ajax ???
});

	function check() {
	if( authCheck == 'Y') {
		alert("?????? ????????? ?????????????????????. ???????????? ?????????/??????????????? ????????? ????????????.");
		return true;
	} else if( authCheck == 'N'){
		alert("?????? ????????? ????????????. ?????? ????????? ??????????????????.")
		return false;
	}
	
}
</script>
</html>
