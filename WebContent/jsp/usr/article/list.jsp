<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<c:set var="pageTitle" value="${board.name} 게시물 리스트" />
<%@ include file="../../part/head.jspf"%>
<section class="section-1 con">
	<h1>${pageTitle}</h1>
</section>
<div class="con flex flex-jc-r">
	<a href="write?boardId=${param.boardId}">게시물 작성</a>
</div>
<section class="section-2">
	<div class="con">
		<div class="article-list">
			<header>
				<div class="flex flex-jc-c">
					<div class="article-list__cell-id">번호</div>
					<div class="article-list__cell-title">제목</div>
					<div class="article-list__cell-writer">작성자</div>
					<div class="article-list__cell-reg-date">날짜</div>
					<div class="article-list__cell-lookup">
						<i class="fas fa-eye"></i>
					</div>
				</div>
			</header>

			<main class=list-main>
				<c:forEach items="${articles}" var="article">
					<div>

						<div class="article-list__cell-id">${article.id}</div>
						<div class="article-list__cell-title">
							<a href="detail?id=${article.id}">${article.title}</a>
						</div>
						<div class="article-list__cell-writer">${article.extra__nickname}</div>
						<div class="article-list__cell-reg-date">${article.regDate}</div>
						<div class="article-list__cell-lookup">
							<i class="fas fa-eye"></i>
						</div>

					</div>
				</c:forEach>
			</main>
		</div>

		<div class="article-page-menu">
			<ul class="flex flex-jc-r">${list-page}
			</ul>
		</div>
	</div>
</section>

<%@ include file="../../part/foot.jspf"%>