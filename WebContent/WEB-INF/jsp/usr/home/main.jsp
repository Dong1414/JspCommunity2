<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="pageTitle" value="메인화면" />
<%@ include file="../../part/head.jspf"%>
<title>Insert title here</title>

<section class="title-bar con-min-width">
	<h1 class="con">${pageTitle}</h1>
</section>

<div class="main-layout flex con">
	<div class="articles border-red con-850">
		<div class="flex">
			<div class="new new7_cook border-red padding-15p">
				<ul>
					<li><a href="../article/list?boardId=2">자유 게시판 <span>new</span></a></li>
					<c:forEach items="${free}" var="free" begin="0" end="6">
						<li><a href="../article/detail?id=${free.id}"><i
								class="far fa-plus-square"></i> ${free.title}</a></li>
					</c:forEach>					
				</ul>
			</div>

			<div class="new new7_bug border-red padding-15p">
				<ul>
					<li><a href="../article/list?boardId=3">요리 게시판 <span>new</span></a></li>
					<c:forEach items="${cook}" var="cook" begin="0" end="6">
						<li><a href="../article/detail?id=${cook.id}"><i
								class="far fa-plus-square"></i> ${cook.title}</a></li>
					</c:forEach>
				</ul>
			</div>

			<div class="new new7_questions border-red padding-15p">

				<ul>
					<li><a href="../article/list?boardId=4">벌레 게시판 <span>new</span></a></li>
					<c:forEach items="${bug}" var="bug" begin="0" end="6">
						<li><a href="../article/detail?id=${bug.id}"><i
								class="far fa-plus-square"></i> ${bug.title}</a></li>
					</c:forEach>
				</ul>
			</div>
		</div>
		<div class="flex">
			<div class="new new7_cook border-red padding-15p">

				<ul>
					<li><a href="../article/list?boardId=5">자취 게시판 <span>new</span></a></li>
					<c:forEach items="${single}" var="single" begin="0" end="6">
						<li><a href="../article/detail?id=${single.id}"><i
								class="far fa-plus-square"></i>${single.title}</a></li>
					</c:forEach>
				</ul>
			</div>


			<div class="new new7_bug border-red padding-15p">

				<ul>
					<li><a href="../article/list?boardId=6">꿀팁 게시판 <span>new</span></a></li>
					<c:forEach items="${tip}" var="tip" begin="0" end="6">
						<li><a href="../article/detail?id=${tip.id}"><i
								class="far fa-plus-square"></i> ${tip.title}</a></li>
					</c:forEach>
				</ul>
			</div>

			<div class="new new7_questions border-red padding-15p">

				<ul>
					<li><a href="../article/list?boardId=7">질문 게시판 <span>new</span></a></li>
					<c:forEach items="${question}" var="question" begin="0" end="6">
						<li><a href="../article/detail?id=${question.id}"><i
								class="far fa-plus-square"></i> ${question.title}</a></li>
					</c:forEach>
				</ul>
			</div>
		</div>
	</div>
	<div class="hot-articles border-red">
		<ul>			
			<li><i class="fab fa-hotjar"></i> 주간 HOT! </li>
			<li><a class="flex"><div class="num1">1</div>${hotArticle.get(0).getHitsCount}</a></li>
			<li><a class="flex"><div class="num2">2</div></a></li>
			<li><a class="flex"><div class="num3">3</div></a></li>
			<li><a class="flex"><div class="num4">4</div></a></li>
			<li><a class="flex"><div class="num5">5</div></a></li>
			<li><a class="flex"><div class="num6">6</div></a></li>
			<li><a class="flex"><div class="num7">7</div></a></li>
			<li><a class="flex"><div class="num8">8</div></a></li>
			<li><a class="flex"><div class="num9">9</div></a></li>
			<li><a class="flex"><div class="num10">10</div></a></li>
		</ul>
	</div>
</div>


<%@ include file="../../part/foot.jspf"%>