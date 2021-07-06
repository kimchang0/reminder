<%@page import="poly.util.EncryptUtil"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	List<Map<String, String>> rList = (List<Map<String, String>>) request.getAttribute("rList");
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
<link rel="stylesheet" type="text/css"
	href="/resources/fonts/font-awesome-4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css"
	href="/resources/fonts/Linearicons-Free-v1.0.0/icon-font.min.css">
<link rel="stylesheet" href="/resources/scss/Button.css">
<link rel="stylesheet" href="/resources/css/tempbtn.css" type="text/css">
<style>
html, body {
	height: 100%;
	margin: 0;
}

</style>

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
				<span style="font-size: 30px; margin:auto; float:left;"><b> PassWord's Inquiry </b></span>
					<div class="card-header">
							<button class="btncls" type="button" style="float:right;" data-toggle="modal" data-target="#myModal">+</button>
					</div>
				</div>
				<%for(Map<String, String> rMap : rList){ 
					String dec_id = EncryptUtil.decAES128CBC(rMap.get("site_id"));
					String reDec_id = EncryptUtil.decAES128CBC(dec_id);
					String dec_pw = EncryptUtil.decAES128CBC(rMap.get("site_pw"));
					String reDec_pw = EncryptUtil.decAES128CBC(dec_pw);
					String seceret_pw = reDec_pw.replaceAll(".*", "*********");
				%>

				<div class="card" id="">
				<span style="font-size: 30px; margin:auto; float:center;"><b><%=rMap.get("site_name") %></b></span>
					<div class="card-body">
						<span>Url: </span><span><%=rMap.get("site_address") %></span> <button type="button" name="<%=rMap.get("site_address") %>" onclick="new_page(this);" class="btn btn-default" style="background-color:#50BCDF; margin:auto;">Move</button> <br>
						<span>Id: </span><span><%=reDec_id %></span> <button type="button" name="<%=reDec_id %>" onclick="copy_id(this);" class="btn btn-default" style="background-color:#50BCDF;">Copy</button> <br>
						<span>PW: </span><span><%=seceret_pw %></span> <button type="button" name="<%=reDec_pw %>" onclick="copy_pw(this);" class="btn btn-default" style="background-color:#50BCDF;">Copy</button> <br>
					</div>
					<div>
						<form action="/Site/deleteList.do" onsubmit="return delete_site();" method="post">
							<input type="hidden" value="<%=rMap.get("site_address") %>" name="site_address">
							<input type="hidden" value="<%=rMap.get("site_id") %>" name="site_id">
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
							<h4 class="modal-title">Site Edit</h4>
						</div>
						<div class="modal-body">
							<form id="site_edit_form<%=i %>" name="form_data<%=i %>" action="/Site/siteEdit.do" method="post">
								<p>Site Name: </p><input type="text" name="site_name" value="<%=rMap.get("site_name") %>" style="width:100%;" required>
								<p>Site Url: </p><input type="text" name="site_address" value="<%=rMap.get("site_address") %>" style="width:100%;" required>
								<p>Site Id: </p><input type="text" name="site_id" value="<%=reDec_id %>" style="width:100%;" required>
								<p>Site PassWord: </p><input type="password" name="site_pw" value="<%=reDec_pw %>" style="width:100%;" required>
								<input type="hidden" name="before_site_name" value="<%=rMap.get("site_name") %>">
								<input type="hidden" name="before_site_address" value="<%=rMap.get("site_address") %>">
								<input type="hidden" name="before_site_id" value="<%=rMap.get("site_id") %>">
								<input type="hidden" name="before_site_pw" value="<%=rMap.get("site_pw") %>">
								<input type="hidden" name="index" value="site_edit_form<%=i %>">
								
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
			
			



			
			
			
		<!-- Modal -->
			<div class="modal fade" id="myModal" role="dialog">
		    	<div class="modal-dialog">
		    
				<!-- Modal content-->
					<div class="modal-content">
						<div class="modal-header">
							<h4 class="modal-title">Site Add</h4>
						</div>
						<div class="modal-body">
							<form id="site_add_form" name="form_data_add" action="/Site/siteAdd.do">
								<p>Site Name: </p><input type="text" name="site_name" placeholder="사이트 이름을 입력하세요. ex)네이버" style="width:100%;" required>
								<p>Site Url: </p><input type="text" name="site_address" placeholder="사이트 주소를 입력하세요." style="width:100%;" required>
								<p>Site Id: </p><input type="text" name="site_id" placeholder="사이트 아이디를 입력하세요." style="width:100%;" required>
								<p>Site PassWord: </p><input type="password" name="site_pw" placeholder="사이트의 비밀번호를 입력하세요." style="width:100%;" required>
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
	<!-- Control Center for Now Ui Dashboard: parallax effects, scripts for the example pages etc -->
	<script src="/resources/assets/js/paper-dashboard.min.js?v=2.0.1"
		type="text/javascript"></script>
	<script>
	
		function start_add(form_data){
			console.log("start_add_clicked!");
			if(form_data.site_name.value == "") {
				(function showNotification() {
					color = 'warning';

					$.notify({
						icon : "nc-icon nc-bell-55",
						message : "사이트 명을 입력해주세요."

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
			} else if(form_data.site_address.value == "") {
				(function showNotification() {
					color = 'warning';

					$.notify({
						icon : "nc-icon nc-bell-55",
						message : "사이트 주소를 입력해주세요."

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
			} else if(form_data.site_id.value == "") {
				(function showNotification() {
					color = 'warning';

					$.notify({
						icon : "nc-icon nc-bell-55",
						message : "사이트 아이디를 입력해주세요."

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
			} else if(form_data.site_pw.value == "") {
				(function showNotification() {
					color = 'warning';

					$.notify({
						icon : "nc-icon nc-bell-55",
						message : "사이트 비밀번호를 입력해주세요."

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
				document.getElementById("site_add_form").submit();
			}
		}
		
		function start_edit(form_data){
			console.log("start_edit_clicked!");
			if(form_data.site_name.value == "") {
				(function showNotification() {
					color = 'warning';

					$.notify({
						icon : "nc-icon nc-bell-55",
						message : "사이트 명을 입력해주세요."

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
			} else if(form_data.site_address.value == "") {
				(function showNotification() {
					color = 'warning';

					$.notify({
						icon : "nc-icon nc-bell-55",
						message : "사이트 주소를 입력해주세요."

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
			} else if(form_data.site_id.value == "") {
				(function showNotification() {
					color = 'warning';

					$.notify({
						icon : "nc-icon nc-bell-55",
						message : "사이트 아이디를 입력해주세요."

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
			} else if(form_data.site_pw.value == "") {
				(function showNotification() {
					color = 'warning';

					$.notify({
						icon : "nc-icon nc-bell-55",
						message : "사이트 비밀번호를 입력해주세요."

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
		

		(function showNotification() {
			color = 'warning';

			$.notify({
				icon : "nc-icon nc-bell-55",
				message : "사이트를 선택해주세요."

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

		$("#navbar-toggler").on('click', function() {
			if ($(this).hasClass("toggled")) {
				$(this).removeClass("toggled");
				$("html").first().removeClass("nav-open");
			} else {
				$(this).addClass("toggled");
				$("html").first().addClass("nav-open");

			}

		});

		$("#herald").on('click', function() {
			var tempElem = document.createElement('textarea');
			tempElem.value = "";
			document.body.appendChild(tempElem);

			tempElem.select();
			document.execCommand("copy");
			document.body.removeChild(tempElem);
			color = 'warning';

			$.notify({
				icon : "nc-icon nc-bell-55",
				message : "비밀번호가 복사되었습니다."

			}, {
				type : color,
				delay : 1000,
				timer : 1000,
				placement : {
					from : 'top',
					align : 'center'
				}
			});
		});

		
		function new_page(site_url) {
			console.log("new_page 실행");
			newPage=window.open(site_url.getAttribute('name'));
		}
		function copy_id(site_id) {
			var tempElem = document.createElement('textarea');
			tempElem.value = site_id.getAttribute('name');
			document.body.appendChild(tempElem);

			tempElem.select();
			document.execCommand("copy");
			document.body.removeChild(tempElem);
			color = 'warning';

			$.notify({
				icon : "nc-icon nc-bell-55",
				message : "아이디가 복사되었습니다."

			}, {
				type : color,
				delay : 1000,
				timer : 1000,
				placement : {
					from : 'top',
					align : 'center'
				}
			});
		}
		
		function copy_pw(site_pw) {
			var tempElem = document.createElement('textarea');
			tempElem.value = site_pw.getAttribute('name');
			document.body.appendChild(tempElem);

			tempElem.select();
			document.execCommand("copy");
			document.body.removeChild(tempElem);
			color = 'warning';

			$.notify({
				icon : "nc-icon nc-bell-55",
				message : "비밀번호가 복사되었습니다."

			}, {
				type : color,
				delay : 1000,
				timer : 1000,
				placement : {
					from : 'top',
					align : 'center'
				}
			});
		}
		function delete_site(){
			console.log("delete_site 실행");
			var result = window.confirm("삭제하시겠습니까?");
			
			if(result) {
				return true;
			} else {
				return false;
			}
			
		}
	</script>

</body>

</html>