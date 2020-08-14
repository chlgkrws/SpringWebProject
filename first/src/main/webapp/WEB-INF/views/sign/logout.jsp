<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>logout page</title>
</head>
<body>
<h1>Logout Page</h1>

<form method="post" action="/logout">
	<div>
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token }">
	</div>
	<button>로그아웃</button>
</form>
</body>
</html>