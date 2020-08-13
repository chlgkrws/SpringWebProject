<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시물 작성</title>
</head>
<body>

<form method="post" >
	<label>제목</label>
	<input type="text" name="title" id = "title"/><br/>
	
	<label>작성자</label>
	<input type="text" name="writer" id = "writer"/><br/>
	
	<label>내용</label>
	<textarea rows="5" cols="50" name="content" id = "content"></textarea><br/>
	
	<button type="summit" id="save" name="save">작성</button>
	
	<div id="nav">
	<%@ include file="../include/nav.jsp" %>
</div>
	<script type="text/javascript" src="http://code.jquery.com/jquery-1.11.3.js"></script>
    <script src="<c:url value="/resources/ckeditor/ckeditor.js" />"></script>
    <script type="text/javascript">
            $(document).ready(function(){
                 
                CKEDITOR.replace( 'content' );
                CKEDITOR.config.height = 500;
                
                 
                $("#save").click(function(){
                     
                    //에디터 내용 가져옴
                    var content = CKEDITOR.instances.content.getData();
                     
                    //널 검사
                    if($("#title").val().trim() == ""){
                        alert("제목을 입력하세요.");
                        $("#title").focus();
                        return false;
                    }
                     
                    if($("#writer").val().trim() == ""){
                        alert("작성자를 입력하세요.");
                        $("#writer").focus();
                        return false;
                    }
                     
                     
                    //값 셋팅
                    var objParams = {
                            /* <c:if test="${boardView.id != null}"> //있으면 수정 없으면 등록
                            id          : $("#board_id").val(),
                            </c:if> */
                            title     : $("#title").val(),
                            writer      : $("#writer").val(),
                            content     : content
                    };
                     
                    //ajax 호출
                    $.ajax({
                        url         :   "/board/write",
                        dataType    :   "json",
                        contentType :   "application/x-www-form-urlencoded; charset=UTF-8",
                        type        :   "post",
                        data        :   objParams,
                        success     :   function(retVal){
 
                            if(retVal.code == "OK") {
                                alert(retVal.message);
                                location.href = "/board/list";  
                            } else {
                                alert(retVal.message);
                                /* location.href = ""; */
                            }
                             
                        },
                        error       :   function(request, status, error){
                            console.log("AJAX_ERROR");
                        }
                    });
                     
                     
                });
                 
            });
        </script>
    
</form>
</body>
</html>