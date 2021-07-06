<%@page import="static poly.util.CmmUtil.nvl"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	String id = nvl((String)session.getAttribute("user_id"));    
%>

<!DOCTYPE html>
<html>
<style>
a{
text-decoration: none;
color: blue;
}
ul{
list-style-type: none;
}
body{
text-align: center;
}
</style>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<div style="background-color: none;">
		<div>
			<ul>
				<li>
					<%if(id.isEmpty()){ %>
						<a href="/The/TheLogin.do">
							로그인
						</a>
						<a href="/The/TheSignUp.do">
							회원가입
						</a>
					<%} else {%>
						<%=id %>님 환영합니다.
						<a href="/The/TheLogout.do">
							로그아웃							
						</a>
					<%} %>
				</li>
			</ul>
		</div>
	</div>
</body>
</html>