<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
  <%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="_csrf_header" content="${_csrf.headerName}" />
<meta name="_csrf" content="${_csrf.token}" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="/resources/css/reset.css">
<link rel="stylesheet" href="/resources/css/style.css">

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
	<sec:authorize access="isAuthenticated()">
	<p><sec:authentication property="principal.student_name" var="principal_name"></sec:authentication></p>
	</sec:authorize>
	
	<sec:authorize access="isAuthenticated()">
	<p><sec:authentication property="principal.username" var="principal_id"></sec:authentication></p>
	</sec:authorize>
	
	<div>
		<input type="hidden" name="principal_name" id="principal_name" value="${principal_name }">
		<input type="hidden" name="principal_id" id="principal_id" value="${principal_id }">
	</div>
	
	<div id="nav">
		<%@ include file="../include/nav.jsp"%>
	</div>

	

        <div id="wrapper">
            <div id="container">
                <div class="side-menu">
                    <p class="main-title">선택메뉴</p>
                    <ul class="category">
                        <c:forEach items="${listMenu }" var="listMenu">
                        	<li>
								<a href="/board/listPageSearch?num=1&boardType=${boardType }&listType=${listMenu.key}"> ${listMenu.value }</a>                        	
                        	</li>
                        </c:forEach>
                    </ul>
                </div>

                <div class="board-list">
                    <h2 id="container_title">선택된 카테고리</span></h2>
                <span class="path">홈 > 선택메뉴 > 세부선택메뉴</span>
                <!-- 게시물 읽기 시작 { -->
                <div class="tbl_frm01 tbl_wrap">
                    <table>
                        <tbody>
                            <tr>
                                <th scope="row">제목</th>
                                <td colspan="5">${view.title }</td>
                            </tr>
                            <tr>
                                <th>작성자</th>
                                <td>
                                    <span class="writer">${view.writer }</span></td>
                                <th>작성일</th>
                                <td>${date }</td>
                                <th>조회수</th>
                                <td>${view.viewCnt }</td>
                            </tr>
                            <tr>
                                <td colspan="6" class="td-content">
                                    <div id="board-content">
                                        <p>
                                            <span
                                                style="background-color:rgb(255,255,255);color:rgb(28,30,33);font-size:14px;">
                                               ${view.content }
                                               
                                               </span></p>
                                    </div>
                                </td>
                            </tr>
						</tbody>
					</table>
			<table  id="reply_area">
				<tr reply_type="all" style="display: none">
					<!-- 뒤에 댓글 붙이기 쉽게 선언 -->
					<td colspan="4"></td>
				</tr>
				<!-- 댓글이 들어갈 공간 -->
				<c:forEach var="replyList" items="${replyList}" varStatus="status">
					<!-- 댓글 -->
					<tr class="td-comment" reply_type="<c:if test="${replyList.depth == '0'}">main</c:if><c:if test="${replyList.depth == '1'}">sub</c:if>">
						<!-- 댓글의 depth 표시 -->
						
						<c:if test="${replyList.depth == '1'}">
					 	</c:if>
					 	 
						 <td width="13%" >
	                         <div class="comment-list">
	                             <p>
	                             	<c:if test="${replyList.depth == '1'}"> <span>     L</span> </c:if>
	                                 <span
	                                     style="background-color:rgb(255,255,255);color:rgb(28,30,33);font-size:14px; font-weight: bold;">
	                                     
	                                     ${replyList.reply_writer }</span></p>
	                         </div>
	                     </td>
	                     
	                     <td width="40%" style="text-align: left;">
	                          <div class="comment-list">
	                              <p>
	                                  <span
	                                      style="background-color:rgb(255,255,255);color:rgb(28,30,33);font-size:14px;">
	                                     ${replyList.reply_content}</span></p>
	                          </div>
	                      </td>
	                      <td width="7%" ></td>
	                      <td width="15%" style="text-align: left;">
	                          <div class="comment-list">
	                              <p>
	                                  <span
	                                      style="background-color:rgb(255,255,255);color:rgb(28,30,33);font-size:14px;">
	                                      ${replyList.register_datetime_real }</span></p>
	                          </div>
	                      </td>
						<td width="25%" style="text-align: left;">
	                         <div class="comment-list">
	                             <c:if test="${replyList.depth != '1'}">
									<button name="reply_reply" parent_id="${replyList.reply_id}"
											reply_id="${replyList.reply_id}">댓글
									</button>
										<!-- 첫 댓글에만 댓글이 추가, 대댓글 불가 -->
	                             </c:if>
		                       	<button name="reply_modify" parent_id="${replyList.parent_id}"
											r_type="<c:if test="${replyList.depth == '0'}">main</c:if><c:if test="${replyList.depth == '1'}">sub</c:if>"
											reply_id="${replyList.reply_id}" date_time="${replyList.register_datetime_real }">수정
								</button>
	                             
	                             <button name="reply_del"
									r_type="<c:if test="${replyList.depth == '0'}">main</c:if><c:if test="${replyList.depth == '1'}">sub</c:if>"
									reply_id="${replyList.reply_id}">삭제
								</button>
	                         </div>
	                     </td>
					</tr>
				</c:forEach>
			</table>
                        
                    <div id="comment">
                        <textarea cols="40" rows="3" id="reply_content" name="reply_content" placeholder="댓글을 입력하세요"></textarea>
                        <div id="commit">
                            <button id="reply_save" name="reply_save">댓글 등록</button>
                        </div>
                    </div>
                    <div id="back">
                    	<a href ="/board/listPageSearch?num=1&boardType=${boardType }&listType=${listType }"> &lt 게시물 목록</a>
                    </div>
                 								
                </div>
            </div>
        </div>
    </div>
	
	
	<script type="text/javascript">
		var model = [];
		model.bno = "${view.bno}";
		model.boardType = "${boardType}";
		model.listType= "${listType}";
	</script>
	
	<!-- sideMenu -->
	<script type="text/javascript">
		document.getElementById("searchBtn").onclick = function(){
			let searchType = document.getElementsByName("searchType")[0].value;
			let keyword = document.getElementsByName("keyword")[0].value;

			location.href = "/board/listPageSearch?num=1"+"&searchType="+searchType+"&keyword="+keyword+"&boardType="+"${boardType}"+"&listType="+"${listType}";
		}
	</script>
	
	<script type="text/javascript"
		src="http://code.jquery.com/jquery-1.11.3.js"></script>
	<script src="<c:url value="/resources/js/boardReply.js" />"></script>
	<!-- 글 수정 -->
	<script type="text/javascript">
		//게시물 삭제를 물어보는 부분
		function checkResult(){
			var result = confirm("게시물을 정말 삭제하시겠습니까?");
			
			if(!result) {
				return;
			}else{
				window.location.replace("/board/delete?bno=${view.bno }&boardType=${boardType}&listType=${listType}");
			}
		}

	</script>
	
</body>
</html>