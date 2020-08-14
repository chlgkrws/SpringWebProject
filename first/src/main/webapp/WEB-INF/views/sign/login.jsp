<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>login page</title>
</head>
<body>

<form method="post" action="/login">
	<div>
		학번 : <input type="text" name="username" id="student_id"/>
	</div>
	<div>
		비밀번호  :<input type="password" name="password" id="student_password"/>
	</div>
	<div>
		<button type="summit" id="login" name="login" >제출</button>
	</div>
	<div>
		<input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }">
	</div>
	
</form>
</body>
</html>