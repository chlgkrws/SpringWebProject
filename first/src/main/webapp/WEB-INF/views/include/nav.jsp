<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
   <%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


   
   <ul>
   <li>
   			<a href="/board/listPageSearch?num=1">글 목록(페이징 + 검색)</a>
   		</li>
   		<li>
   			<a href="/board/listPage?num=1">글 목록(페이징)</a>
   		</li>
   		<li>
   			<a href="/board/list">글 목록</a>
   		</li>
   		<li>
   			<a href="/board/write">글 작성</a>
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
   </ul>