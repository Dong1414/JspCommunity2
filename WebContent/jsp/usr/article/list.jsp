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

<div>총 게시물 수 : ${totalCount}</div>
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
						<div class="article-list__cell-lookup">${article.hitsCount}</div>

					</div>
				</c:forEach>
			</main>
		</div>
		<div>
			<script>
				let DoSearchForm__submited = false;
				function DoSearchForm__submit(form) {
					if (DoSearchForm__submited) {
						alert('처리중입니다');
						return;
					}

					form.searchKeyword.value = form.searchKeyword.value.trim();

					if (form.searchKeyword.value.length == 0) {
						alert('검색어를 입력해주세요.');
						form.searchKeyword.focus();
						return;
					}

					form.submit();
					DoSearchForm__submited = true;
				}
			</script>
			<form onsubmit="DoSearchForm__submit(this); return false;">
				<input type="hidden" name="boardId" value="${param.boardId}" /> <select
					name="searchKeywordType">
					<option value="titleAndBody">제목+본문</option>
					<option value="title">제목</option>
					<option value="body">본문</option>
				</select>
				<script>
					const param__searchKeywordType = '${param.searchKeywordType}';

					if (param__searchKeywordType) {
						$('select[name="searchKeywordType"]').val(
								param__searchKeywordType);
					}
				</script>
				<input value="${param.searchKeyword}" type="text"
					name="searchKeyword" placeholder="검색어를 입력해주세요." /> <input
					type="submit" value="검색" />
			</form>
		</div>
		<style>
.red {
	color: red;
}
</style>
		<div class="article-page-menu">
			<div class="con">
				<ul class="flex flex-jc-c">
					<!--
	<c:set var="aUrl" value="?page=1&boardId=${param.boardId}&searchKeywordType=${param.searchKeywordType}&searchKeyword=${param.searchKeyword}" />
	<a href="${aUrl}">◀◀</a>
	-->

					<c:if test="${pageBoxStartBeforeBtnNeedToShow}">
						<c:set var="aUrl"
							value="?page=${pageBoxStartBeforePage}&boardId=${param.boardId}&searchKeywordType=${param.searchKeywordType}&searchKeyword=${param.searchKeyword}" />
						<a href="${aUrl}">◀</a>
					</c:if>
					<c:forEach var="i" begin="${pageBoxStartPage}"
						end="${pageBoxEndPage}" step="1">
						<c:set var="aClass" value="${page == i ? 'red' : ''}" />
						<c:set var="aUrl"
							value="?page=${i}&boardId=${param.boardId}&searchKeywordType=${param.searchKeywordType}&searchKeyword=${param.searchKeyword}" />
						<a class="${aClass}" href="${aUrl}">${i}</a>
					</c:forEach>

					<c:if test="${pageBoxEndAfterBtnNeedToShow}">
						<c:set var="aUrl"
							value="?page=${pageBoxEndAfterPage}&boardId=${param.boardId}&searchKeywordType=${param.searchKeywordType}&searchKeyword=${param.searchKeyword}" />
						<a href="${aUrl}">▶</a>
					</c:if>

					<!--
	<c:set var="aUrl" value="?page=${totalPage}&boardId=${param.boardId}&searchKeywordType=${param.searchKeywordType}&searchKeyword=${param.searchKeyword}" />
	<a href="${aUrl}">▶▶</a>
	-->

				</ul>
			</div>
		</div>
</section>

<%@ include file="../../part/foot.jspf"%>