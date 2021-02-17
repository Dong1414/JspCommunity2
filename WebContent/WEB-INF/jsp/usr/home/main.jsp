<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="pageTitle" value="메인화면" />
<%@ include file="../../part/head.jspf"%>
<title>Insert title here</title>

<section class="title-bar con-min-width visible-md-up">
	<h1 class="con"></h1>
</section>

<section class="mobile-title-bar con-min-width visible-sm-down">
	<h1 class="con"></h1>
</section>

<section class="section-1 con-min-width visible-md-up">
	<div class="con">
		<div class="sbs-2021-slider sbs-2021-slider-1">
			<div class="arrows">
				<div>
					<span>
						<i class="fas fa-caret-left"></i>
					</span>
				</div>
				<div>
					<span>
						<i class="fas fa-caret-right"></i>
					</span>
				</div>
			</div>
			<div class="items-wrap">
				<div class="items">
					<div class="active">
						<a class="bg free-image" href="../article/list?boardId=2">
							<span></span>
						</a>
					</div>
					<div>
						<a class="bg cook-image" href="../article/list?boardId=3">
							<span></span>
						</a>
					</div>
					<div>
						<a class="bg bug-image" href="../article/list?boardId=4">
							<span></span>
						</a>

					</div>
					<div>
						<a class="bg single-image" href="../article/list?boardId=5">
							<span></span>
						</a>

					</div>
					<div>
						<a class="bg tip-image" href="../article/list?boardId=6">
							<span></span>
						</a>

					</div>
					<div>
						<a class="bg question-image" href="../article/list?boardId=7">
							<span></span>
						</a>

					</div>
					<div>
						<a class="bg notice-image" href="../article/list?boardId=1">
							<span></span>
						</a>

					</div>
				</div>
			</div>
			<div class="pages-wrap">
				<div class="pages">
					<div class="active"></div>
					<div></div>
					<div></div>
					<div></div>
					<div></div>
					<div></div>
					<div></div>
				</div>
			</div>
		</div>
	</div>
</section>

<div class="main-layout flex con visible-md-up">
	<div class="articles con-850">
		<div class="flex">
			<div class="new new7_cook padding-15p">
				<ul>
					<li><a href="../article/list?boardId=2">
							자유 게시판
							<span>new</span>
						</a></li>
					<c:forEach items="${free}" var="free" begin="0" end="6">
						<li><a href="../article/detail?id=${free.id}">
								<i class="far fa-plus-square"></i>
								${free.title}
							</a></li>
					</c:forEach>
				</ul>
			</div>

			<div class="new new7_bug padding-15p">
				<ul>
					<li><a href="../article/list?boardId=3">
							요리 게시판
							<span>new</span>
						</a></li>
					<c:forEach items="${cook}" var="cook" begin="0" end="6">
						<li><a href="../article/detail?id=${cook.id}">
								<i class="far fa-plus-square"></i>
								${cook.title}
							</a></li>
					</c:forEach>
				</ul>
			</div>

			<div class="new new7_questions padding-15p">

				<ul>
					<li><a href="../article/list?boardId=4">
							벌레 게시판
							<span>new</span>
						</a></li>
					<c:forEach items="${bug}" var="bug" begin="0" end="6">
						<li><a href="../article/detail?id=${bug.id}">
								<i class="far fa-plus-square"></i>
								${bug.title}
							</a></li>
					</c:forEach>
				</ul>
			</div>
		</div>
		<div class="flex">
			<div class="new new7_cook padding-15p">

				<ul>
					<li><a href="../article/list?boardId=5">
							자취 게시판
							<span>new</span>
						</a></li>
					<c:forEach items="${single}" var="single" begin="0" end="6">
						<li><a href="../article/detail?id=${single.id}">
								<i class="far fa-plus-square"></i>${single.title}</a></li>
					</c:forEach>
				</ul>
			</div>


			<div class="new new7_bug padding-15p">

				<ul>
					<li><a href="../article/list?boardId=6">
							꿀팁 게시판
							<span>new</span>
						</a></li>
					<c:forEach items="${tip}" var="tip" begin="0" end="6">
						<li><a href="../article/detail?id=${tip.id}">
								<i class="far fa-plus-square"></i>
								${tip.title}
							</a></li>
					</c:forEach>
				</ul>
			</div>

			<div class="new new7_questions padding-15p">

				<ul>
					<li><a href="../article/list?boardId=7">
							질문 게시판
							<span>new</span>
						</a></li>
					<c:forEach items="${question}" var="question" begin="0" end="6">
						<li><a href="../article/detail?id=${question.id}">
								<i class="far fa-plus-square"></i>
								${question.title}
							</a></li>
					</c:forEach>
				</ul>
			</div>
		</div>
	</div>
	<div class="hot-articles">
		<ul>
			<li><i class="fab fa-hotjar"></i> 주간 HOT!</li>
			<c:forEach items="${hotArticle}" varStatus="status" var="article"
				begin="0" end="9">
				<li><a href="../article/detail?id=${article.id}" class="flex">
						<div class="num${status.count}">${status.count}</div>${article.title}</a></li>

			</c:forEach>
		</ul>
	</div>
</div>

<div class="mobile-main-layout con visible-sm-down">
	<div class="articles con">
		<div class="">
			<div class="new new7_cook padding-15p">
				<ul>
					<li><a href="../article/list?boardId=2">
							자유 게시판
							<span>new</span>
						</a></li>
					<c:forEach items="${free}" var="free" begin="0" end="6">
						<li><a href="../article/detail?id=${free.id}">
								<i class="far fa-plus-square"></i>
								${free.title}
							</a></li>
					</c:forEach>
				</ul>
			</div>

			<div class="new new7_bug padding-15p">
				<ul>
					<li><a href="../article/list?boardId=3">
							요리 게시판
							<span>new</span>
						</a></li>
					<c:forEach items="${cook}" var="cook" begin="0" end="6">
						<li><a href="../article/detail?id=${cook.id}">
								<i class="far fa-plus-square"></i>
								${cook.title}
							</a></li>
					</c:forEach>
				</ul>
			</div>

			<div class="new new7_questions padding-15p">

				<ul>
					<li><a href="../article/list?boardId=4">
							벌레 게시판
							<span>new</span>
						</a></li>
					<c:forEach items="${bug}" var="bug" begin="0" end="6">
						<li><a href="../article/detail?id=${bug.id}">
								<i class="far fa-plus-square"></i>
								${bug.title}
							</a></li>
					</c:forEach>
				</ul>
			</div>
		</div>
		<div class="">
			<div class="new new7_cook padding-15p">

				<ul>
					<li><a href="../article/list?boardId=5">
							자취 게시판
							<span>new</span>
						</a></li>
					<c:forEach items="${single}" var="single" begin="0" end="6">
						<li><a href="../article/detail?id=${single.id}">
								<i class="far fa-plus-square"></i>${single.title}</a></li>
					</c:forEach>
				</ul>
			</div>


			<div class="new new7_bug padding-15p">

				<ul>
					<li><a href="../article/list?boardId=6">
							꿀팁 게시판
							<span>new</span>
						</a></li>
					<c:forEach items="${tip}" var="tip" begin="0" end="6">
						<li><a href="../article/detail?id=${tip.id}">
								<i class="far fa-plus-square"></i>
								${tip.title}
							</a></li>
					</c:forEach>
				</ul>
			</div>

			<div class="new new7_questions padding-15p">

				<ul>
					<li><a href="../article/list?boardId=7">
							질문 게시판
							<span>new</span>
						</a></li>
					<c:forEach items="${question}" var="question" begin="0" end="6">
						<li><a href="../article/detail?id=${question.id}">
								<i class="far fa-plus-square"></i>
								${question.title}
							</a></li>
					</c:forEach>
				</ul>
			</div>
		</div>
	</div>
	<div class="hot-articles">
		<ul>
			<li><i class="fab fa-hotjar"></i> 주간 HOT!</li>
			<c:forEach items="${hotArticle}" varStatus="status" var="article"
				begin="0" end="9">
				<li><a href="../article/detail?id=${article.id}" class="flex">
						<div class="num${status.count}">${status.count}</div>${article.title}</a></li>

			</c:forEach>
		</ul>
	</div>
</div>

<%@ include file="../../part/foot.jspf"%>