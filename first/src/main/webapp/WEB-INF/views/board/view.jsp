<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시물 조회</title>

</head>
<style>
textarea {
	width: 100%;
}

.reply_reply {
	border: 2px solid #FF50CF;
}

.reply_modify {
	border: 2px solid #FFBB00;
}
</style>
<body>

	<div id="nav">
		<%@ include file="../include/nav.jsp"%>
	</div>


	<h2>${view.title }</h2>

	<hr />
	<div class="writer">
		<span>작성자 : </span> ${view.writer }"
	</div>

	<hr />
	<div class="content" style="height:auto; overflow:auto;" >${view.content }</div>
	<hr />
	<div>

	 <a href="/board/modify?bno=${view.bno }">게시물 수정</a>, <a
			href="/board/delete?bno=${view.bno }">게시물 삭제</a>
	</div>

	<p id="bno" name="bno" value="${view.bno }">${view.bno }</p>


	<c:if test="${!replyList.isEmpty()}">
		<table border="1" width="1200px" id="reply_area">
			<tr reply_type="all" style="display: none">
				<!-- 뒤에 댓글 붙이기 쉽게 선언 -->
				<td colspan="4"></td>
			</tr>
			<!-- 댓글이 들어갈 공간 -->
			<c:forEach var="replyList" items="${replyList}" varStatus="status">
				<tr reply_type="<c:if test="${replyList.depth == '0'}">main</c:if><c:if test="${replyList.depth == '1'}">sub</c:if>">
					<!-- 댓글의 depth 표시 -->
					<td width="820px"><c:if test="${replyList.depth == '1'}"> → </c:if>${replyList.reply_content}
					</td>
					<td width="100px">${replyList.reply_writer}
					</td>
					<td width="100px"><input type="password"
						id="reply_password_${replyList.reply_id}" style="width: 100px;"
						maxlength="10" placeholder="패스워드" />
					</td>
					<td align="center"><c:if test="${replyList.depth != '1'}">
						
						<button name="reply_reply" parent_id="${replyList.reply_id}"
								reply_id="${replyList.reply_id}">댓글
						</button>
							<!-- 첫 댓글에만 댓글이 추가, 대댓글 불가 -->
						</c:if>
						<button name="reply_modify" parent_id="${replyList.parent_id}"
							r_type="<c:if test="${replyList.depth == '0'}">main</c:if><c:if test="${replyList.depth == '1'}">sub</c:if>"
							reply_id="${replyList.reply_id}">수정
						</button>
						<button name="reply_del"
							r_type="<c:if test="${replyList.depth == '0'}">main</c:if><c:if test="${replyList.depth == '1'}">sub</c:if>"
							reply_id="${replyList.reply_id}">삭제
						</button>
						
					</td>
				</tr>
			</c:forEach>
			</table>
	</c:if>
	<!-- 댓글등록 -->
	
	<table border="1" width="1200px" bordercolor="#46AA46">
		<tr>
			<td width="500px">이름: <input type="text" id="reply_writer"
				name="reply_writer" style="width: 170px;" maxlength="10"
				placeholder="작성자" /> 패스워드: <input type="password"
				id="reply_password" name="reply_password" style="width: 170px;"
				maxlength="10" placeholder="패스워드" />
				<button id="reply_save" name="reply_save">댓글 등록</button>
			</td>
		</tr>
		<tr>
			<td><textarea id="reply_content" name="reply_content" rows="4"
					cols="50" placeholder="댓글을 입력하세요."></textarea></td>
		</tr>
	</table>


	<script type="text/javascript">
		var model = [];
		model.bno = "${view.bno}";
		
	</script>
	<script type="text/javascript"
		src="http://code.jquery.com/jquery-1.11.3.js"></script>
	<script src="<c:url value="/resources/js/boardReply.js" />"></script>
	<!-- 글 수정 -->
	<script type="text/javascript">
		$(document).ready(function(){
			var bno = ${view.bno};
			var writer = ${view.writer};
			var content = ${view.content};
			var title = ${view.title};

			var objParams = {
				bno :bno,
				writer : writer,
				content : content,
				title : title
			};

			$.ajax({
				url : "/board/modify",
				dataType :"json",
				contentType :"application/x-www-form-urlencoded; charset=UTF-8",
				type : "post",
				data : objParams,
				success : function(retVal){
					if(retVal.code !="OK"){
						alert(retVal.message);						
					}else{
						location.href = "/board/write"
					}
					
				}
			});
		});
	</script>
	
</body>
</html>