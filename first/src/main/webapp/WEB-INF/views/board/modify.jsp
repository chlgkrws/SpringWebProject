<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="_csrf_header" content="${_csrf.headerName}" />
    <meta name="_csrf" content="${_csrf.token}" />
<title>게시물 수정</title>
</head>
<body>
<div id="nav">
	<%@ include file="../include/nav.jsp" %>
</div>
<form method="post" >
	<label>제목</label>
	<input type="text" name="title" value="${view.title }"/><br/>
	
	<label>작성자</label>
	<input type="text" name="writer" value="${view.writer }"/><br/>
	
	<label>내용</label>
	<textarea rows="5" cols="50" name="content" id ="content" >${view.content }</textarea><br/>
	
	<button type="summit">완료</button>
	
	<script type="text/javascript" src="http://code.jquery.com/jquery-1.11.3.js"></script>
    <script src="<c:url value="/resources/ckeditor/ckeditor.js" />"></script>
    <script type="text/javascript">
            $(document).ready(function(){
                 
                CKEDITOR.replace( 'content' );
                CKEDITOR.config.height = 500;
                
            });
        </script>
</form>
</body>
</html>