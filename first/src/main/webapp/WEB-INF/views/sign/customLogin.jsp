<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>login page</title>
	
	<meta charset="UTF-8">
	<meta name="author" content="JaeKeun-Lee">
	<meta name="description" content="Web Project">
	<meta name="keywords" content="Web Project">
	
	<title>로그인</title>
	
	<link rel="stylesheet" href="/resources/css/reset.css">
	<link rel="stylesheet" href="/resources/css/style.css">
</head>
<body>
	<div id="nav">
		<%@ include file="../include/nav.jsp"%>
	</div>
    
       
        <div class="layer">
            <div id="login_box">
                <p class="l_title">로그인</p>
                <form id="login" name="login" action="/login" method="post">
                    <fieldset id="login_fs">
                        <legend>회원로그인</legend>
                        <input type="text" name="username" id="student_id"  value="${username }" placeholder="아이디" tabindex="1" required>
                        <input type="password" name="password" id="student_password" value="" autocomplete="new-password" placeholder="비밀번호" tabindex="2" required>
                        <input class="submit" type="submit" id="login" name="login"  value="로그인" tabindex="3">
                        <input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }">
                    </fieldset>
                </form>

                <div class="find">
                    <a href="#" tabindex="4">비밀번호 찾기</a>
                    <span class="space">|</span>
                    <a href="/sign/signUp" tabindex="5">회원가입</a>
                </div>
                <c:if test="${not empty ERRORMSG }">
				<font color="red">
					<p>Your login attempt was not successful due to <br/>
					${ERRORMSG}</p>
				</font>
				</c:if>	
            </div>
        </div>
        
        
        
<%-- <h1>Custom Login Page</h1>
<form method="post" action="/login">
	<div>
		학번 : <input type="text" name="username" id="student_id" value="${username }"/>
	</div>
	<div>
		비밀번호  :<input type="password" name="password" id="student_password" value="" autocomplete="new-password"/>
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
			${ERRORMSG}</p>
		</font>
	</c:if>	
</form> --%>


</body> 
</html>