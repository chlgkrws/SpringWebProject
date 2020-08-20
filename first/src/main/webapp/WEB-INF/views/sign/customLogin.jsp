<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>login page</title>
</head>
<body>
<h1>Custom Login Page</h1>
<form method="post" action="/login">
	<div>
		학번 : <input type="text" name="username" id="student_id" value="${username }"/>
	</div>
	<div>
		비밀번호  :<input type="password" name="password" id="student_password" value="${password }"/>
	</div>
	<div>
		<button type="submit" id="login" name="login" >제출</button>
	</div>
	<div>
		<input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }">
	</div>

	<c:if test="${not empty ERRORMSG }">
		<font color="red">
			<p>Your login attempt was not successful due to <br/>
			${ERRORMSG }</p>
		</font>
	</c:if>	
</form>
</body>
</html>