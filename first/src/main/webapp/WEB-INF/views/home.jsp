<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
  <%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
	   <meta
           name="viewport"
           content="user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, width=device-width">
       <meta name="author" content="JaeKeun-Lee">
       <meta name="description" content="Web Project">
       <meta name="keywords" content="Web Project">

		<!-- style -->
        <link rel="stylesheet" href="/resources/css/reset.css">
        <link rel="stylesheet" href="/resources/css/style.css">
        <link rel="stylesheet" href="/resources/css/slick.css">
        <link rel="stylesheet" href="/resources/css/lightgallery.css">


        <!-- 웹 폰트 -->
        <link
            href="https://fonts.googleapis.com/css?family=Nanum+Gothic"
            rel="stylesheet">
        <link
            href="https://fonts.googleapis.com/css?family=Nanum+Brush+Script"
            rel="stylesheet">
        <link href="https://fonts.googleapis.com/css?family=Abel" rel="stylesheet">

        <link href="https://fonts.googleapis.com/css2?family=Noto+Sans&display=swap" rel="stylesheet">

        <!-- HTLM5shiv ie6~8 -->
        <!--[if lt IE 9]> <script src="js/html5shiv.min.js"></script> <script
        type="text/javascript"> alert("현재 당신이 보는 브라우저는 지원하지 않습니다. 최신 브라우저로 업데이트해주세요!");
        </script> <![endif]-->
	<title>Home</title>
</head>
<body>


	<div id="nav">
		<%@ include file="include/nav.jsp"%>
	</div>	
	
		
      
       <main>
           <section id="contents">
               <div class="container">
                   <h2 class="ir_su">반응형 사이트 컨텐츠</h2>
                   <section id="cont_left">
                       <h3 class="ir_su">메뉴 및 게시판 컨텐츠 영역</h3>
                       <article class="column col1">
                           <h4 class="col_tit">Menu</h4>
                           <p class="col_desc">Category 분류</p>
                           <!-- 메뉴 -->
                           <div class="menu">
                               <ul>
                                   <li>
                                       <a href="/board/listPageSearch?num=1&boardType=school&listType=notice">공지사항
                                           <i class="fa fa-angle-double-right" aria-hidden="true"></i>
                                       </a>
                                   </li>
                                   <li>
                                       <a href="/board/listPageSearch?num=1&boardType=school&listType=tip">졸업&수업 꿀팁
                                           <i class="fa fa-angle-double-right" aria-hidden="true"></i>
                                       </a>
                                   </li>
                                   <li>
                                       <a href="/board/listPageSearch?num=1&boardType=board&listType=free">자유게시판
                                           <i class="fa fa-angle-double-right" aria-hidden="true"></i>
                                       </a>
                                   </li>
                                   <li>
                                       <a href="/board/listPageSearch?num=1&boardType=board&listType=study">수업게시판
                                           <i class="fa fa-angle-double-right" aria-hidden="true"></i>
                                       </a>
                                   </li>
                                   <li>
                                       <a href="/board/listPageSearch?num=1&boardType=board&listType=qna">Q&A
                                           <i class="fa fa-angle-double-right" aria-hidden="true"></i>
                                       </a>
                                   </li>
                                   <li>
                                       <a href="/board/listPageSearch?num=1&boardType=share&listType=share">서적나눔
                                           <i class="fa fa-angle-double-right" aria-hidden="true"></i>
                                       </a>
                                   </li>
                               </ul>
                           </div>
                           <!--//메뉴 -->
                       </article>
                       <!-- //col1 -->

                       <article class="column col2">
                           <h4 class="col_tit">Post</h4>
                           <p class="col_desc">조회수 높은 게시물, 최근 게시물</p>
                           <!-- 게시판 -->
                           <div class="notice1">
                               <h5>Best</h5>
                               <ul>
                               <c:forEach items="${highViewCnt }" var="highViewCnt">
                               		<li>
                                       <a href="/board/view?bno=${highViewCnt.bno }&boardType=${highViewCnt.boardType }&listType=${highViewCnt.listType }">${highViewCnt.title }</a>
                                   </li>
                               
                               </c:forEach>
                               </ul>
                               
                               <a href="#" class="more" title="더 보기">More
                                   <i class="fa fa-plus-circle" aria-hidden="true"></i>
                               </a> 
                           </div>
                           <!--//게시판 -->
                           <!-- 게시판2 -->
                           <div class="notice2 mt15">
                               <h5>Recent</h5>
                               <ul>
                                  <c:forEach items="${recentView }" var="recentView">
                               		<li>
                                       <a href="/board/view?bno=${recentView.bno }&boardType=${recentView.boardType }&listType=${recentView.listType }">${recentView.title }</a>
                                   </li>
                             	  </c:forEach>
                               </ul>
                               <a href="#" class="more" title="더 보기">More
                                   <i class="fa fa-plus-circle" aria-hidden="true"></i>
                               </a>
                           </div>
                           <!--//게시판2 -->
                       </article>
                       <!-- //col2 -->

                       <article class="column col3">
                           <h4 class="col_tit">Club</h4>
                           <p class="col_desc">동아리 홍보 / 부원 모집?.</p>
                           <!-- blog -->
                           <div class="blog1">
                               <h5 class="ir_su">Image1</h5>
                               <figure>
                                   <img src="/resources/img/blog4_@2.jpg" class="img-normal" alt="normal image">
                                   <img src="/resources/img/blog4_@2.jpg" class="img-retina" alt="retina image">
                                   <figcaption>융합 IT 학과 동아리 ()()() 입니다.</figcaption>
                               </figure>
                           </div>
                           <!--//blog -->
                           <!-- blog2 -->
                           <div class="blog2 mt15">
                               <div class="img-retina">
                                   <h5>Image2</h5>
                               </div>
                               <p>융합 IT 학과 동아리 ()()() 입니다.</p>
                           </div>
                           <!--//blog2 -->
                       </article>
                       <!-- //col3 -->
                   </section>
                   <!-- //cont_left -->

                   <section id="cont_center">
                       <h3 class="ir_su">반응형 사이트 가운데 컨텐츠</h3>
                       <article class="column col4">
                           <h4 class="col_tit">Image Slider</h4>
                           <p class="col_desc">게시물 썸네일?</p>
                           <!-- 이미지 슬라이드 -->
                           <div class="slider">
                               <div>
                                   <figure>
                                       <img src="/resources/img/slider001.jpg" alt="이미지1">
                                       <figcaption>
                                           <em>oo대회 수상</em>
                                           <span>대회 준비 기간 20.07.20 ~ 20.08.06</span></figcaption>
                                   </figure>
                               </div>
                               <div>
                                   <figure>
                                       <img src="/resources/img/slider001.jpg" alt="이미지2">
                                       <figcaption>
                                           <em>oo공모전 수상</em>
                                           <span>oo사 공모전 후기</span></figcaption>
                                   </figure>
                               </div>
                               <div>
                                   <figure>
                                       <img src="/resources/img/slider001.jpg" alt="이미지3">
                                       <figcaption>
                                           <em>융합IT 15학번 IT포트폴리오</em>
                                           </figcaption>
                                   </figure>
                               </div>
                           </div>
                           <!--//이미지 슬라이드 -->
                       </article>
                       <!-- //col4 -->

                       <article class="column col5">
                           <h4 class="col_tit">PortPolio / Report</h4>
                           <p class="col_desc">선배들의 취업 준비 포트폴리오, 수업 진행하면서 제출했던 과제, 리포트 참고자료</p>
                           <!-- lightbox -->
                           <div class="lightbox square clearfix">
                               <a href="/resources/img/light01_s.jpg"><img src="/resources/img/light01.jpg" alt="이미지">
                                   <em>blur</em>
                               </a>
                               <a href="/resources/img/light02_s.jpg"><img src="/resources/img/light02.jpg" alt="이미지">
                                   <em>brightness</em>
                               </a>
                               <a href="/resources/img/light03_s.jpg"><img src="/resources/img/light03.jpg" alt="이미지">
                                   <em>contrast</em>
                               </a>
                               <a href="/resources/img/light04_s.jpg"><img src="/resources/img/light04.jpg" alt="이미지">
                                   <em>grayscale</em>
                               </a>
                               <a href="/resources/img/light05_s.jpg"><img src="/resources/img/light05.jpg" alt="이미지">
                                   <em>hue-rotate</em>
                               </a>
                               <a href="/resources/img/light06_s.jpg"><img src="/resources/img/light06.jpg" alt="이미지">
                                   <em>invert</em>
                               </a>
                               <a href="/resources/img/light07_s.jpg"><img src="/resources/img/light07.jpg" alt="이미지">
                                   <em>opacity</em>
                               </a>
                               <a href="/resources/img/light08_s.jpg"><img src="/resources/img/light08.jpg" alt="이미지">
                                   <em>saturate</em>
                               </a>
                               <a href="/resources/img/light09_s.jpg"><img src="/resources/img/light09.jpg" alt="이미지">
                                   <em>sepia</em>
                               </a>
                               <a href="/resources/img/light10_s.jpg"><img src="/resources/img/light10.jpg" alt="이미지">
                                   <em>Mix</em>
                               </a>
                           </div>
                           <!--//lightbox -->
                       </article>
                       <!-- //col5 -->

                       <article class="column col6">
                           <h4 class="col_tit">Video</h4>
                           <p class="col_desc">영상을 보여주는 영역입니다.</p>
                           <!-- video -->
                           <!-- <video autoplay="autoplay" controls="controls" loop="loop"> <source
                           src="/resources/img/video.mp4" type="video/mp4"> </video> -->
                           <div class="video">
                               <iframe width="560" height="315" src="https://www.youtube.com/embed/yytWGELNeOI" frameborder="0" allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
                           </div>
                           <!--//video -->
                       </article>
                       <!-- //col6 -->
                   </section>
                   <!-- //cont_center -->
                   <section id="cont_right">
                       <h3 class="ir_su">반응형 사이트 오른쪽 컨텐츠</h3>
                       <article class="column col7">
                           <h4 class="col_tit">Effect1</h4>
                           <p class="col_desc">마우스 오버효과1</p>
                           <!-- side1 -->
                           <div class="side1">
                               <figure class="front">
                                   <img src="/resources/img/side1.jpg" alt="이미지2">
                               </figure>
                               <div class="back">
                                   <i class="fa fa-heart fa-4x" aria-hidden="true"></i>
                               </div>
                           </div>
                           <!-- side1//-->
                       </article>
                       <!-- //col7 -->

                       <article class="column col8">
                           <h4 class="col_tit">Effect2</h4>
                           <p class="col_desc">마우스 오버효과2</p>
                           <!-- side2 -->
                           <div class="side2">
                               <figure class="front">
                                   <img src="/resources/img/side2.jpg" alt="이미지2">
                                   <figcaption>
                                       <h3>Hover Effect</h3>
                                   </figcaption>
                               </figure >
                               <figure class="back">
                                   <img src="/resources/img/side4.jpg" alt="이미지2">
                                   <figcaption>
                                       <h3>Hover Effect</h3>
                                   </figcaption>
                               </figure>
                           </div>
                           <!-- side2//-->
                       </article>
                       <!-- //col8 -->

                       <article class="column col9">
                           <h4 class="col_tit">Effect3</h4>
                           <p class="col_desc">마우스 오버효과3</p>
                           <!-- side3 -->
                           <div class="side3">
                               <figure>
                                   <img src="/resources/img/side3.jpg" alt="이미지3">
                                   <figcaption>
                                       <h3>Hover<em>Effect</em>
                                       </h3>
                                   </figcaption>
                               </figure>
                           </div>
                           <!-- side3//-->
                       </article>
                       <!-- //col9 -->
                   </section>
                   <!-- //cont_right -->
               </div>
           </section>
           <!-- //contents -->
       </main>

       <footer id="footer">
           <div class="container">
               <div class="row">
                   <div class="footer">
                       <ul>
                           <li>
                               <a href="#">사이트 도움말</a>
                           </li>
                           <li>
                               <a href="#">사이트 이용약관</a>
                           </li>
                           <li>
                               <a href="#">사이트 운영원칙</a>
                           </li>
                           <li>
                               <a href="#">
                                   <strong>개인정보취급방침</strong>
                               </a>
                           </li>
                           <li>
                               <a href="#">책임의 한계와 법적고지</a>
                           </li>
                           <li>
                               <a href="#">게시중단요청서비스</a>
                           </li>
                           <li>
                               <a href="#">고객센터</a>
                           </li>
                       </ul>
                       <address>
                           Copyright &copy;
                           <a href="https://ljg96073.tistory.com">
                               <strong>JaeKeun-Lee</strong>
                           </a>
                           All Rights Reserved.
                       </address>
                   </div>
               </div>
           </div>
       </footer>
       <!-- //footer -->
		
       <!-- JavaScript Libraries -->
       <script src="/resources/js/jquery.min_1.12.4.js"></script>
       <script src="/resources/js/modernizr-custom.js"></script>
       <script src="/resources/js/slick.min.js"></script>
       <script src="/resources/js/lightgallery.min.js"></script>
       <script src="/resources/js/custom.js"></script>
       <script>
           //접기/펼치기
           $(".btn").click(function (e) {
               e.preventDefault();
               $(".nav").slideToggle();
               $(".btn").toggleClass("open");
               //var btn = $(".btn").find(">i").attr("class"); alert(btn);

               if ($(".btn").hasClass("open")) {
                   //open이 있을 때
                   $(".btn")
                       .find(">i")
                       .attr("class", "fa fa-angle-up");
               } else {
                   //open이 없을 때
                   $(".btn")
                       .find(">i")
                       .attr("class", "fa fa-angle-down");
               }
           });

           $(window).resize(function () {
               var wWidth = $(window).width();
               if (wWidth > 600) {
                   $(".nav").removeAttr("style");
               }
           });

           //라이트 박스
           $(".lightbox").lightGallery(
               {thumbnail: true, autoplay: true, pause: 3000, progressBar: true}
           );

           //이미지 슬라이더
           $(".slider").slick({
               dots: true,
               autoplay: true,
               autoplaySpeed: 3000,
               arrows: true,
               responsive: [
                   {
                       breakpoint: 768,
                       settings: {
                           autoplay: false
                       }
                   }
               ]
           });
       </script>

</body>
</html>
