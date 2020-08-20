<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
  <%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
	<title>Home</title>
</head>
<body>
<h1>
	Hello world!  
</h1>
<sec:authorize access="isAuthenticated()">
	<h1><sec:authentication property="principal.student_name"></sec:authentication>님 어서오세요</h1>
	</sec:authorize>
<P>  The time on the server is ${serverTime}. </P>

<p><a href="/board/listPageSearch?num=1">게시물 목록</a></p>
<p><a href="/board/write">게시물 작성</a></p>

</body>
</html>
