<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="pageTitle" value="${article.extra__boardName} 게시물 상세페이지" />
<c:set var="memberID" value="${article.memberId}"/>
<%@ include file="../../part/head.jspf"%>
<section class="section-1 con">
	<h1>${pageTitle}</h1>
</section>

<section class="section-2">
	<div class="con">
		<div class="article-detail">
			<header class="detail-title">
				<div class="flex flex-jc-c detail-div">
					<div class="article-detail__cell-id">${article.id}</div>
					<div class="article-detail__cell-title">${article.title}</div>
					<div class="article-detail__cell-writer">${article.extra__writer}</div>
					<div class="article-detail__cell-regDate">${article.regDate}</div>
				</div>
			</header>
			
			
			<c:if test="${sessionScope.loginedMemberId == memberID}">
				<div class="text-align-right">
					<a href="modify?id=${article.id}">수정</a> <a
						onclick="if ( confirm('정말 삭제하시겠습니까?') == false ) { return false; }"
						href="doDelete?id=${article.id}">삭제</a>
				</div>
			</c:if>
			
			<main>
				<div>${article.body}</div>
			</main>
		</div>


		<hr />

		<div>
			<a href="list?boardId=${article.boardId}">목록</a>
		</div>
	</div>
</section>

<%@ include file="../../part/foot.jspf"%>