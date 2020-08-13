<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<form method="post">
	<label>학번</label>
	<input type="text" name="student_id" id = "student_id"/><br/>
	
	<label>이름</label>
	<input type="text" name="student_name" id = "student_name"/><br/>
	
	<label>비밀 번호</label>
	<input type="password" name="student_password" id = "student_password"></input><br/>
	
	<button type="summit" id="sign_up" name="sign_up">회원가입</button>
</form>

<script type="text/javascript"
		src="http://code.jquery.com/jquery-1.11.3.js"></script>
		<script type="text/javascript">
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
                                location.href = "redirect:/";  
                            } else {
                                alert(retVal.message);
                               location.reload();
                            }
                             
                        },
                        error       :   function(request, status, error){
                            console.log("AJAX_ERROR");
                        }


					});
						
					 
                     
				});

				


			});
		</script>
</body>
</html>