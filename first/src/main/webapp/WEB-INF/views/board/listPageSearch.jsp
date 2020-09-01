<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시물 목록</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="/resources/css/reset.css">
<link rel="stylesheet" href="/resources/css/style.css">
</head>
<body>
<div id="nav">
	<%@ include file="../include/nav.jsp" %>
</div>
	<%-- <table>
		<thead>
			<tr>
				<th>번호</th>
				<th>제목</th>
				<th>작성일</th>
				<th>작성자</th>
				<th>조회수</th>
			</tr>
		</thead>

		<tbody>
			<c:forEach items="${list}" var="list">
				<tr>
					<td>${list.bno}</td>
					<td>
						<a href="/board/view?bno=${list.bno }&boardType=${page.boardType}&listType=${page.listType}">${list.title }</a>					
					</td>
					<td>
						<fmt:formatDate value="${list.regDate}" pattern="yyyy-MM-dd"/>
					</td>
					<td>${list.writer }</td>
					<td>${list.viewCnt }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<div>
		<c:if test="${page.prev }">
			<span>[<a href ="/board/listPageSearch?num=${page.startPageNum-1 }${page.getSearchTypeKeyword()}&boardType=${boardType}&listType=${listType}">이전</a>]</span>
		</c:if>
		
		<c:forEach begin="${page.startPageNum }" end="${page.endPageNum }" var="num">
			<span>
				<c:if test="${select != num }">
					<a href="/board/listPageSearch?num=${num }${page.getSearchTypeKeyword()}&boardType=${boardType}&listType=${listType}">${num }</a>
				</c:if>
				<c:if test="${select == num }">
					<b>${num }</b>
				</c:if>
			</span>
		</c:forEach>
		
		<c:if test="${page.next }">
			<span>[<a href="/board/listPageSearch?num=${page.endPageNum + 1 }${page.getSearchTypeKeyword()}&boardType=${boardType}&listType=${listType}">다음</a>]</span>
		</c:if>
	</div>
	
	<div>
		<c:forEach begin="1" end="${pageNum }" var="num">
			<span>
				<a href="/board/listPage?num=${num }">${num}</a>
			</span>
		</c:forEach>
	</div>
	
	 <div>
		<select name="searchType">
			<option value="title" <c:if test="${page.searchType eq 'title' }">selected</c:if> >제목</option>
			<option value="content" <c:if test="${page.searchType eq 'content' }">selected</c:if>>내용</option>
			<option value="title_content" <c:if test="${page.searchType eq 'title_content' }">selected</c:if>>제목+내용</option>
			<option value="writer" <c:if test="${page.searchType eq 'writer' }">selected</c:if>>작성자</option>
		</select>
		<input type="text" class="submit" name="keyword" value='${page.keyword }' />
		
		<button type="button" id="searchBtn">검색</button>
	</div>  --%>
	
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

                
                <!--side menu-->

                <div class="board-list">
                    <h2 id="container_title">선택된 카테고리</h2>
                <span class="path">홈 > 선택메뉴 > 세부선택메뉴</span>
                <!-- 게시판 목록 시작 { -->
                <div class="tbl_wrap">
                    <table>
                        <thead>
                            <tr>
                                <th scope="col" class="td_num">번호</th>
                                <th scope="col" class="td_subject">제목</th>
                                <th scope="col" class="td_name">글쓴이</th>
                                <th scope="col" class="td_date">
                                    <a href="#" >날짜</a>
                                </th>
                                <th scope="col" class="td_num">
                                    <a href="#">조회</a>
                                </th>
                            </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${list}" var="list">
							<tr>
								<td>${list.bno}</td>
								<td>
									<a href="/board/view?bno=${list.bno }&boardType=${page.boardType}&listType=${page.listType}">${list.title }</a>					
								</td>
								<td>
									<fmt:formatDate value="${list.regDate}" pattern="yyyy-MM-dd"/>
								</td>
								<td>${list.writer }</td>
								<td>${list.viewCnt }</td>
							</tr>
						</c:forEach>
                            </tbody>
                    </table>
                </div>
                <!--board-list-->
				
				<!-- Search -->
				<div class="search">
                    <select name="searchType">
						<option value="title" <c:if test="${page.searchType eq 'title' }">selected</c:if> >제목</option>
						<option value="content" <c:if test="${page.searchType eq 'content' }">selected</c:if>>내용</option>
						<option value="title_content" <c:if test="${page.searchType eq 'title_content' }">selected</c:if>>제목+내용</option>
						<option value="writer" <c:if test="${page.searchType eq 'writer' }">selected</c:if>>작성자</option>
					</select>
                    <input type="text" class="submit" name="keyword" placeholder="검색어를 입력하세요."value='${page.keyword }' />
					<button type="button" id="searchBtn">검색</button>
                </div>
			
				<!-- //Search -->
              
                <div class="pg_wrap">
					<c:if test="${page.prev }">
						<span class="pg_page">[<a href ="/board/listPageSearch?num=${page.startPageNum-1 }${page.getSearchTypeKeyword()}&boardType=${boardType}&listType=${listType}">이전</a>]</span>
					</c:if>
					
					<c:forEach begin="${page.startPageNum }" end="${page.endPageNum }" var="num">
						<span class="pg_page">
							<c:if test="${select != num }">
								<a href="/board/listPageSearch?num=${num }${page.getSearchTypeKeyword()}&boardType=${boardType}&listType=${listType}">${num }</a>
							</c:if>
							<c:if test="${select == num }">
								<b>${num }</b>
							</c:if>
						</span>
					</c:forEach>
					
					<c:if test="${page.next }">
						<span class="pg_page">[<a href="/board/listPageSearch?num=${page.endPageNum + 1 }${page.getSearchTypeKeyword()}&boardType=${boardType}&listType=${listType}">다음</a>]</span>
					</c:if>
				</div>
            </div>
            <!--container-->
        </div>
        <!--wrapper-->
	
	</div>
	<script type="text/javascript">
		document.getElementById("searchBtn").onclick = function(){
			let searchType = document.getElementsByName("searchType")[0].value;
			let keyword = document.getElementsByName("keyword")[0].value;

			location.href = "/board/listPageSearch?num=1"+"&searchType="+searchType+"&keyword="+keyword+"&boardType="+"${boardType}"+"&listType="+"${listType}";
			
		}
	</script>
</body>
</html>