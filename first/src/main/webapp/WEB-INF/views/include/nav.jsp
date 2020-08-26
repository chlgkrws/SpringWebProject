<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
   <%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


   
  <%--  <ul>
   		<li>
   			<a href="/">home</a>
   		</li>
  		<li>
   			<a href="/board/listPageSearch?num=1">글 목록(페이징 + 검색)</a>
   		</li>
   		<!-- <li>
   			<a href="/board/listPage?num=1">글 목록(페이징)</a>
   		</li>
   		<li>
   			<a href="/board/list">글 목록</a>
   		</li> -->
   		
   		<li>
   			<a href="/board/write?boardType=${boardType}&listType=${listType}">글 작성</a>
   		</li>
   		<sec:authorize access="isAnonymous()">
  		<li>
  			<a href="/sign/signUp">회원가입</a>
  		</li>
  		</sec:authorize>
  		<sec:authorize access="isAnonymous()">
  		<li>
  			<a href="/sign/customLogin">로그인</a>
  		</li>
  		</sec:authorize>
  		<sec:authorize access="isAuthenticated()">
  		<li>
  			<a href="/sign/logout">로그아웃</a>
  		</li>
  		</sec:authorize>
   </ul> --%>
   
   
  
   
   
   <header>
            <div class="header">
                <div class="title">
                    <div class="container">
                        <div class="mainmenu">
                            <ul >
                                <li><a href="/" class="logo" ><img class="logo" src="/resources/icon/favicon.ico"> </a></li> 

                                <li>
                                    <a href="#">학교생활</a>
                                    <ul id="submenu">
                                        <li>
                                            <a href="/board/listPageSearch?num=1&boardType=school&listType=notice">공지사항</a>
                                        </li>
                                        <li>
                                            <a href="/board/listPageSearch?num=1&boardType=school&listType=tip">졸업&수업 꿀팁</a>
                                        </li>
                                    </ul>
                                </li>
                                
                                <li>
                                    <a href="#">게시판</a>
                                    <ul id="submenu">
                                        <li>
                                            <a href="/board/listPageSearch?num=1&boardType=board&listType=free">자유게시판</a>
                                        </li>
                                        <li>
                                            <a href="/board/listPageSearch?num=1&boardType=board&listType=study">수업게시판</a>
                                        </li>
                                        <li>
                                            <a href="/board/listPageSearch?num=1&boardType=board&listType=qna">Q&A</a>
                                        </li>
                                    </ul>
                                </li>
                                
                                <li>
                                    <a href="#">전공 서적</a>
                                    <ul id="submenu">
                                        <li>
                                            <a href="/board/listPageSearch?num=1&boardType=share&listType=share">서적나눔</a>
                                        </li>
                                    </ul>
                                </li>
                            </ul>
                        </div>

                        <div id="tophead">
                            <ul >
                                <li>
                                    <a href="#">CONTACT</a>
                                </li>
                                <sec:authorize access="isAnonymous()">
						  		<li id="layer">
                                    <a href="/sign/customLogin">LOGIN</a>
                                </li>
						  		</sec:authorize>
						  		<sec:authorize access="isAuthenticated()">
						  		<li>
						  			<a href="/sign/logout">LOGOUT</a>
						  		</li>
						  		</sec:authorize>
                                <li>
                                    <a href="/">HOME</a>
                                </li>
                                <li>
                            		<sec:authorize access="isAuthenticated()">
										<a><sec:authentication property="principal.student_name"></sec:authentication>님 어서오세요</a>
									</sec:authorize>
                            	</li>
                            </ul>
                        </div>
                    </div>  
                </div>
            </div>
        </header>
        
        
        
        <!--  <table border="1" >
   	<tr> 
   		<td>
   			<p>학교생활</p>
   		</td>
   		<td>
   			<p>게시판</p>
   		</td>
   		<td>
   			<p>전공 서적</p>
   		</td>
   	</tr>
   	<tr >
   		<td>
   			<a href="/board/listPageSearch?num=1&boardType=school&listType=notice">공지</a>
   			<hr/>
   			<a href="/board/listPageSearch?num=1&boardType=school&listType=tip">졸업 수업 꿀팁</a>
   		</td>
   		<td>
   			<a href="/board/listPageSearch?num=1&boardType=board&listType=free">자유게시판</a>
   			<hr/>
   			<a href="/board/listPageSearch?num=1&boardType=board&listType=study">수업게시판</a>
   			<hr/>
   			<a href="/board/listPageSearch?num=1&boardType=board&listType=qna">Q & A</a>
   		</td>
   		<td>
   			<a href="/board/listPageSearch?num=1&boardType=share&listType=share">서적 나눔</a>
   		</td>
   	</tr>
   </table> -->