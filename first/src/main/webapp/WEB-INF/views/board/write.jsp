<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="org.springframework.security.core.context.SecurityContextHolder"%>
<%@ page import="org.springframework.security.core.Authentication"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="_csrf_header" content="${_csrf.headerName}" />
<meta name="_csrf" content="${_csrf.token}" />
<script type="text/javascript"
		src="http://code.jquery.com/jquery-1.11.3.js"></script>
<title>게시물 작성</title>
</head>
<body>
<sec:authorize access="isAuthenticated()">
	<p><sec:authentication property="principal.student_name" var="principal_name"></sec:authentication></p>
</sec:authorize>
<sec:authorize access="isAuthenticated()">
	<p><sec:authentication property="principal.username" var="principal_id"></sec:authentication></p>
</sec:authorize>

		<label>제목</label> <input type="text" name="title" id="title" value="${view.title }"/><br />
		
		<label>작성자</label>
		${principal_name } 
		
		
		
		<label>내용</label>
		<textarea rows="5" cols="50" name="content" id="content">${view.content }</textarea>
		<br />
		
		<button type="submit" id="save" name="save">
		<c:if test="${modify == null}">작성</c:if>
		<c:if test="${modify != null}">완료</c:if>
		</button>
		
		<div id="nav">
			<%@ include file="../include/nav.jsp"%>
		</div>

		<div>
			<input type="hidden" name="${_csrf.parameterName }"
				value="${_csrf.token }">
		</div>
		<div>
			<input type="hidden" name="principal_name" id="principal_name" value="${principal_name }">
			<input type="hidden" name="principal_id" id="principal_id" value="${principal_id }">
			<input type="hidden" name="modifyCheck" id="modifyCheck" value="${modify }">
		</div>
		
	
	<script src="<c:url value="/resources/ckeditor/ckeditor.js" />"></script>
	<script type="text/javascript">
     		$(document).ready(function(){
            	var header = $("meta[name='_csrf_header']").attr("content");
                var token = $("meta[name='_csrf']").attr("content");
                
                CKEDITOR.replace( 'content' ,{filebrowserUploadUrl:"/board/fileUpload?${_csrf.parameterName}=${_csrf.token}"});
                CKEDITOR.config.height = 500;
                
                 
                $("#save").click(function(){
                     
                    //에디터 내용 가져옴
                    var content = CKEDITOR.instances.content.getData();
                    var writer = $("#principal_name").val();		//권한가진 유저의 이름가져오기
                    var student_id = $('#principal_id').val();
					var url = "";
					
					if($("#modifyCheck").val() == ""){
						url = "/board/write?boardType=${boardType}&listType=${listType}"
					}else{
						url ="/board/modify?bno="+"${bno}&boardType=${boardType}&listType=${listType}"
					}

                    //널 검사
                    if($("#title").val().trim() == ""){
                        alert("제목을 입력하세요.");
                        $("#title").focus();
                        return false;
                    }
                     
                    /* if($("#writer").val().trim() == ""){
                        alert("작성자를 입력하세요.");
                        $("#writer").focus();
                        return false;
                    } */
                     
                    
                    //값 셋팅
                    var objParams = {
                    	   
                           title     : $("#title").val(),
                           writer :  writer,
                           content     : content,
                           student_id : student_id
                    };
                     
                    //ajax 호출
                    $.ajax({
                        url         :   url,
                        dataType    :   "json",
                        contentType :   "application/x-www-form-urlencoded; charset=UTF-8",
                        type        :   "post",
                        data        :   objParams,
                        success     :   function(retVal){
 
                            if(retVal.code == "OK") {
                                alert(retVal.message);
                                location.href = "/board/listPageSearch?num=1&boardType=${boardType}&listType=${listType}";  
                            } else {
                                alert(retVal.message);
                                /* location.href = ""; */
                            }
                             
                        },
                        error       :   function(request, status, error){
                            console.log("AJAX_ERROR");
                        },
                        beforeSend:function(xhr){
							xhr.setRequestHeader(header, token);
                            }
                    });
                     
                });
                 
            });
        </script>


</body>
</html>