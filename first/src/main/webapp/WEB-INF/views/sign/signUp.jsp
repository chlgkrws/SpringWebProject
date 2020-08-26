<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="_csrf_header" content="${_csrf.headerName}" />
<meta name="_csrf" content="${_csrf.token}" />
<meta name="author" content="JaeKeun-Lee">
<meta name="description" content="Web Project">
<meta name="keywords" content="Web Project">
<title>회원가입</title>

<link rel="stylesheet" href="/resources/css/reset.css">
<link rel="stylesheet" href="/resources/css/style.css">
        
</head>
<body>
	<div id="nav">
		<%@ include file="../include/nav.jsp"%>
	</div>
	<%-- <label>학번</label>
	<input type="text" name="student_id" id = "student_id"/><br/>
	
	<label>이름</label>
	<input type="text" name="student_name" id = "student_name"/><br/>
	
	<label>비밀 번호</label>
	<input type="password" name="student_password" id = "student_password"></input><br/>
	
	<div>
		<input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }">
	</div>
	<button type="submit" id="sign_up" name="sign_up">회원가입</button> --%>

	<div id="register-box">
            <p class="l_title">회원가입</p>
            <div id="fregisterform" >
                
                <input type="hidden" name="w" value="">
                <input type="hidden" name="url" value="">
                <input type="hidden" name="cert_type" value="">
                <input type="hidden" name="cert_no" value="">
    
                <p>이름</p>
                <input type="text" name="student_name" id = "student_name" tabindex="1" required>
    
                <p>학번</p>
                <input type="text" name="student_id" id = "student_id" tabindex="2" maxlength="15" required>

                <p>비밀번호</p>
                <input type="password" name="student_password" id = "student_password" tabindex="4" required>
    
                <p>비밀번호 확인</p>
                <input type="password" name="student_password" id = "student_password_reg" tabindex="5" required>
    
                <input type="checkbox" name="agree" value="1" tabindex="6"><label class="desc"><a href="#" target="_blank">이용약관</a>, <a href="#" target="_blank">개인정보취급방침</a>에 동의합니다.</label>
    
                <input class="submit" type="submit" id="sign_up" value="회원가입" tabindex="7">
            </div>
        </div>

<script type="text/javascript"
		src="http://code.jquery.com/jquery-1.11.3.js"></script>
		
		<script type="text/javascript">
		var header = $("meta[name='_csrf_header']").attr("content");
        var token = $("meta[name='_csrf']").attr("content");
			$(document).ready(function(){
				
				$("#sign_up").click(function(){

					//널 검사
					if($("#student_id").val().trim() == ""){
						alert("학번을 입력하세요");
						$("#student_id").focus();
						return false;	
					}
					if($("#student_name").val().trim() == ""){
						alert("이름을 입력하세요");
						$("#student_name").focus();
						return false;	
					}
					if($("#student_password").val().trim() == ""){
						alert("비밀번호를 입력하세요");
						$("#student_password").focus();
						return false;	
					}

					if($("#student_password").val().trim() != $("#student_password_reg").val().trim()){
						alert("비밀번호가 다릅니다.")
						return;
					}

					var objParams = {
								student_id : $("#student_id").val(),
								student_name : $("#student_name").val(),
								student_password : $("#student_password").val()	
							}
					$.ajax({
						url         :   "/sign/signUp",
                        dataType    :   "json",
                        contentType :   "application/x-www-form-urlencoded; charset=UTF-8",
                        type        :   "post",
                        data        :   objParams,
                        success     :   function(retVal){	 
                            if(retVal.code == "OK") {
                                alert(retVal.message);
                                location.href = "/sign/customLogin";  
                            } else {
                                alert(retVal.message);
                                location.href = "/sign/signUp";
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