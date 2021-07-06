<%@page import="poly.util.EncryptUtil"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%
	List<Map<String, String>> rList = (List<Map<String, String>>)request.getAttribute("rList");
	int i = 0;
%>

<!DOCTYPE html>
<html lang="en">

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
<link rel="stylesheet" href="/resources/scss/Button.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<link rel="stylesheet" href="/resources/css/tempbtn.css" type="text/css">
<style>
.blockquote {
	height: 80%;
}

.blockquote:hover {
	background-color: #51cbce;
	color: white;
}
</style>
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
			<!-- End Navbar -->


			<div class="content">
				<div class="card">
				<span style="font-size: 30px; margin:auto; float:left;"><b> Today's Schedule </b></span>
					<div class="card-header">
							<button class="btncls" type="button" style="float:right;" data-toggle="modal" data-target="#myModal">+</button>
					</div>
				</div>
				
				<%for(Map<String, String> rMap : rList){
					String alarm = "";
					
					if(rMap.get("schedule_alarm").equals("one")) {
						alarm = "한 번 알림";
					} else {
						alarm = "반복 알림";
					}
				%>

				<div class="card" id="">
				<span style="font-size: 30px; margin:auto; float:center;"><b><%=rMap.get("schedule_name") %></b></span>
					<div class="card-body">
						<span><!-- <%=alarm %>--></span><br>
						<span><%=rMap.get("schedule_time") %>시</span>
						<span><%=rMap.get("schedule_minute") %>분</span>
					</div>
					<div>
						<form action="/Schedule/deleteList.do" onsubmit="return delete_schedule();" method="post">
							<input type="hidden" name="schedule_name" value="<%=rMap.get("schedule_name") %>">
							<input type="hidden" name="schedule_time" value="<%=rMap.get("schedule_time") %>">
							<input type="hidden" name="schedule_minute" value="<%=rMap.get("schedule_minute") %>">
							<button type="submit" class="btn btn-default" style="background-color:#dc143c; float:right;">삭제</button>
						</form>
						<button type="button" class="btn btn-default" style="background-color:#50BCDF; float:right;" data-toggle="modal" data-target="#myModal<%=i%>">수정</button>
					</div>
				</div>
				<!-- Modal -->
			<div class="modal fade" id="myModal<%=i%>" role="dialog">
		    	<div class="modal-dialog">
		    
				<!-- Modal content-->
					<div class="modal-content">
						<div class="modal-header">
							<h4 class="modal-title">Schedule Edit</h4>
						</div>
						<div class="modal-body">
							<form id="schedule_edit_form<%=i %>" name="form_data<%=i %>" action="/Schedule/scheduleEdit.do" method="post">
								<input type="text" name="schedule_name" value="<%=rMap.get("schedule_name") %>" style="width:100%;"> 
								<div style="float:right;">
								<label for="onlyOne" style="align:middle;"> 1회 알림 </label>
								<input type="radio" id="onlyOne" name="schedule_alarm" value="one" checked>
								<label for="many"> 반복 알림 </label>
								<input type="radio" id="many" name="schedule_alarm" value="loop">
								</div><br><br>
								<select name="time" id="time_select" name="time_select">
									<option value="00">00</option>
									<option value="01">01</option>
									<option value="02">02</option>
									<option value="03">03</option>
									<option value="04">04</option>
									<option value="05">05</option>
									<option value="06">06</option>
									<option value="07">07</option>
									<option value="08">08</option>
									<option value="09">09</option>
									<option value="10">10</option>
									<option value="11">11</option>
									<option value="12">12</option>
									<option value="13">13</option>
									<option value="14">14</option>
									<option value="15">15</option>
									<option value="16">16</option>
									<option value="17">17</option>
									<option value="18">18</option>
									<option value="19">19</option>
									<option value="20">20</option>
									<option value="21">21</option>
									<option value="22">22</option>
									<option value="23">23</option>
								</select>
								<span> 시</span>
								<select name="minute" id="minute_select" name="minute_select">
									<option value="00">00</option>
									<option value="01">01</option>
									<option value="02">02</option>
									<option value="03">03</option>
									<option value="04">04</option>
									<option value="05">05</option>
									<option value="06">06</option>
									<option value="07">07</option>
									<option value="08">08</option>
									<option value="09">09</option>
									<option value="10">10</option>
									<option value="11">11</option>
									<option value="12">12</option>
									<option value="13">13</option>
									<option value="14">14</option>
									<option value="15">15</option>
									<option value="16">16</option>
									<option value="17">17</option>
									<option value="18">18</option>
									<option value="19">19</option>
									<option value="20">20</option>
									<option value="21">21</option>
									<option value="22">22</option>
									<option value="23">23</option>
									<option value="24">24</option>
									<option value="25">25</option>
									<option value="26">26</option>
									<option value="27">27</option>
									<option value="28">28</option>
									<option value="29">29</option>
									<option value="30">30</option>
									<option value="31">31</option>
									<option value="32">32</option>
									<option value="33">33</option>
									<option value="34">34</option>
									<option value="35">35</option>
									<option value="36">36</option>
									<option value="37">37</option>
									<option value="38">38</option>
									<option value="39">39</option>
									<option value="40">40</option>
									<option value="41">41</option>
									<option value="42">42</option>
									<option value="43">43</option>
									<option value="44">44</option>
									<option value="45">45</option>
									<option value="46">46</option>
									<option value="47">47</option>
									<option value="48">48</option>
									<option value="49">49</option>
									<option value="50">50</option>
									<option value="51">51</option>
									<option value="52">52</option>
									<option value="53">53</option>
									<option value="54">54</option>
									<option value="55">55</option>
									<option value="56">56</option>
									<option value="57">57</option>
									<option value="58">58</option>
									<option value="59">59</option>
								</select>
								<span> 분</span>
								<input type="hidden" name="before_schedule_name" value="<%=rMap.get("schedule_name") %>">
								<input type="hidden" name="before_schedule_alarm" value="<%=rMap.get("schedule_alarm")%>">
								<input type="hidden" name="before_schedule_time" value="<%=rMap.get("schedule_time")%>">
								<input type="hidden" name="before_schedule_minute" value="<%=rMap.get("schedule_minute")%>">
								<input type="hidden" name="index" value="schedule_edit_form<%=i %>">
							</form>
						</div>
					<!-- Modal button -->
						<div class="modal-footer">
							<button type="button" class="btn btn-default" style="background-color:#50BCDF;" onclick="start_edit(form_data<%=i %>);">Confirm</button>
							<button type="button" class="btn btn-default" data-dismiss="modal" style="background-color:#dc143c;">Close</button>
						</div>
					</div>
				</div>
		  </div>
		  		
				<%
				i++;
				} 
				%>
			</div>
			</div>
			

			<!-- Modal -->
			<div class="modal fade" id="myModal" role="dialog">
		    	<div class="modal-dialog">
		    
				<!-- Modal content-->
					<div class="modal-content">
						<div class="modal-header">
							<h4 class="modal-title">Schedule Add</h4>
						</div>
						<div class="modal-body">
							<form id="schedule_add_form" name="form_data_add" action="/Schedule/scheduleAdd.do" method="post">
								<input type="text" name="schedule_name" placeholder="추가할 일정의 이름을 입력하세요. ex)산책, 산책하기"style="width:100%;"> 
								<div style="float:right;">
								<label for="onlyOne" style="align:middle;"> 1회 알림 </label>
								<input type="radio" id="onlyOne" name="alarm" value="one" checked>
								<label for="many"> 반복 알림 </label>
								<input type="radio" id="many" name="alarm" value="loop">
								</div><br><br>
								<select name="time" id="time_select" name="time_select">
									<option value="00">00</option>
									<option value="01">01</option>
									<option value="02">02</option>
									<option value="03">03</option>
									<option value="04">04</option>
									<option value="05">05</option>
									<option value="06">06</option>
									<option value="07">07</option>
									<option value="08">08</option>
									<option value="09">09</option>
									<option value="10">10</option>
									<option value="11">11</option>
									<option value="12">12</option>
									<option value="13">13</option>
									<option value="14">14</option>
									<option value="15">15</option>
									<option value="16">16</option>
									<option value="17">17</option>
									<option value="18">18</option>
									<option value="19">19</option>
									<option value="20">20</option>
									<option value="21">21</option>
									<option value="22">22</option>
									<option value="23">23</option>
								</select>
								<span> 시</span>
								<select name="minute" id="minute_select" name="minute_select">
									<option value="00">00</option>
									<option value="01">01</option>
									<option value="02">02</option>
									<option value="03">03</option>
									<option value="04">04</option>
									<option value="05">05</option>
									<option value="06">06</option>
									<option value="07">07</option>
									<option value="08">08</option>
									<option value="09">09</option>
									<option value="10">10</option>
									<option value="11">11</option>
									<option value="12">12</option>
									<option value="13">13</option>
									<option value="14">14</option>
									<option value="15">15</option>
									<option value="16">16</option>
									<option value="17">17</option>
									<option value="18">18</option>
									<option value="19">19</option>
									<option value="20">20</option>
									<option value="21">21</option>
									<option value="22">22</option>
									<option value="23">23</option>
									<option value="24">24</option>
									<option value="25">25</option>
									<option value="26">26</option>
									<option value="27">27</option>
									<option value="28">28</option>
									<option value="29">29</option>
									<option value="30">30</option>
									<option value="31">31</option>
									<option value="32">32</option>
									<option value="33">33</option>
									<option value="34">34</option>
									<option value="35">35</option>
									<option value="36">36</option>
									<option value="37">37</option>
									<option value="38">38</option>
									<option value="39">39</option>
									<option value="40">40</option>
									<option value="41">41</option>
									<option value="42">42</option>
									<option value="43">43</option>
									<option value="44">44</option>
									<option value="45">45</option>
									<option value="46">46</option>
									<option value="47">47</option>
									<option value="48">48</option>
									<option value="49">49</option>
									<option value="50">50</option>
									<option value="51">51</option>
									<option value="52">52</option>
									<option value="53">53</option>
									<option value="54">54</option>
									<option value="55">55</option>
									<option value="56">56</option>
									<option value="57">57</option>
									<option value="58">58</option>
									<option value="59">59</option>
								</select>
								<span> 분</span>
							</form>
						</div>
					<!-- Modal button -->
						<div class="modal-footer">
							<button type="button" class="btn btn-default" style="background-color:#50BCDF;" onclick="start_add(form_data_add);">Confirm</button>
							<button type="button" class="btn btn-default" data-dismiss="modal" style="background-color:#dc143c;">Close</button>
						</div>
					</div>
				</div>
		  </div>



			<footer class="footer footer-black  footer-white ">
				<div class="container-fluid"></div>
			</footer>
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
		$("#navbar-toggler").on('click', function() {
			if ($(this).hasClass("toggled")) {
				$(this).removeClass("toggled");
				$("html").first().removeClass("nav-open");
			} else {
				$(this).addClass("toggled");
				$("html").first().addClass("nav-open");

			}

		});
		
		window.addEventListener('DOMContentLoaded', function(){
			
			var today = new Date();
			
			var hour = today.getHours(); 
			
			var minute = today.getMinutes();
			
			select_time('time_select', hour);
			
			select_minute('minute_select', minute);

		});
		

		function select_time(id, value) {
			
			var obj = document.getElementById(id);

			for (i = 0; i < obj.length; i++) {

				if (obj[i].value == value) {
					obj[i].selected = true;
				}
			}
		}
		
		function select_minute(id, value) {

			var obj = document.getElementById(id);

			for (i = 0; i < obj.length; i++) {

				if (obj[i].value == value) {
					obj[i].selected = true;
				}
			}
		}
		
		function start_add(form_data) {
			console.log("start_add 실행");
			
			if(form_data.schedule_name.value == "") {
				
				color = 'warning';
				
				$.notify({
					icon : "nc-icon nc-bell-55",
					message : "일정명을 입력해주세요."

				}, {
					type : color,
					delay : 1000,
					timer : 1000,
					placement : {
						from : 'top',
						align : 'center'
					}
				});
				
				return false;
				
			} else {
				
				document.getElementById('schedule_add_form').submit();
				
			}
			
		}
		
		function delete_schedule(){
			console.log("delete_schedule 실행");
			var result = window.confirm("삭제하시겠습니까?");
			
			if(result) {
				return true;
			} else {
				return false;
			}
			
		}
		
		function start_edit(form_data){
			console.log("start_edit_clicked!");
			if(form_data.schedule_name.value == "") {
				(function showNotification() {
					color = 'warning';

					$.notify({
						icon : "nc-icon nc-bell-55",
						message : "일정명을 입력해주세요."

					}, {
						type : color,
						delay : 1000,
						timer : 1000,
						placement : {
							from : 'top',
							align : 'center'
						}
					});
				})();
			} else {
				document.getElementById(form_data.index.value).submit();
			}
		}
	</script>
</body>

</html>
