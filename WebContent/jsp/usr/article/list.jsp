<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<c:set var="pageTitle" value="${board.name} 게시물 리스트" />
<%@ include file="../../part/head.jspf"%>

	<div class="con title-bar padding-0-10 con-min-width">
		<h1>${pageTitle}</h1>
	</div>


			
	<div class="article-list-box con-min-width">
		<div class="con">
			<table>
				<colgroup>
					<col width="100">
					<col width="250">
					<col width="150">
					<col width="100">
					<col width="50">
				</colgroup>
				<thead>
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>날짜</th>
						<th>작성자</th>
						<th><i class="fas fa-eye"></i></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${articles}" var="article">
						<tr>
							<td><span class="article-list-box__id">${article.id}</span>
							</td>
							<td><a href="detail?id=${article.id}"
								class="article-list-box__title hover-link"> ${article.title}
							</a></td>
							<td><span class="article-list-box__reg-date">
									${article.regDate} </span></td>
							<td><span class="article-list-box__reg-date">
									${article.extra__nickname} </span></td>
							<td><span class="article-list-box__reg-date">
									${article.hitsCount} </span></td>
							<td class="visible-sm-down">
								<div class="flex">
									<span class="article-list-box__id article-list-box__id--mobile">${article.id}</span>

									<a href="detail?id=${article.id}"
										class="article-list-box__title article-list-box__title--mobile flex-grow-1 hover-link">${article.title}</a>
								</div>

								<div class="flex">
									<span
										class="article-list-box__Detailr article-list-box__Detailr--mobile">${article.extra__nickname}</span>
									<span>|</span> <span
										class="article-list-box__reg-date article-list-box__reg-date--mobile">${article.regDate}</span>
								</div>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
	<div class="article-btn-box padding-0-10 con-min-width">
		<div class="con btn-wrap flex flex-jc-e">
			<a class="btn btn-primary" href="write?boardId=${param.boardId}">글쓰기</a>
			<!-- <a class="btn btn-info" href="#">LIST</a> -->
		</div>
	</div>


<div class="con">
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
		<input value="${param.searchKeyword}" type="text" name="searchKeyword"
			placeholder="검색어를 입력해주세요." /> <input type="submit" value="검색" />
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