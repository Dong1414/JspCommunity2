<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="pageTitle" value="${article.extra__boardName} 게시물 상세페이지" />
<c:set var="memberID" value="${article.memberId}" />
<%@ include file="../../part/head.jspf"%>

<script>
	let DoCommentForm__submited = false;
	function DoCommentForm__submit(form) {
		if (DoCommentForm__submited) {
			alert('처리중입니다.');
			return;
		}

		form.body.value = form.body.value.trim();

		if (form.body.value.length == 0) {
			alert('로그인 아이디를 입력해주세요.');
			form.body.focus();

			return;
		}

		form.submit();
		DoCommentForm__submited = true;
	}
</script>
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
					<div class="article-detail__cell-Commentr">${article.extra__writer}</div>
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

			<div class="detail-main con">
				<div class="detail-body">
					<script type="text/x-template">${article.body}</script>
					<div class="toast-ui-viewer"></div>
				</div>
			</div>


			<div class="like-hate con flex flex-jc-c">
				<div class="like flex flex-ai-c">
					<a href="../article/doLike"><i class="far fa-thumbs-up"></i> <span
						class="rec_count">${likeCount}</span></a>
				</div>
				<div class="hate flex flex-ai-c">
					<a href="../article/doHate"><i class="far fa-thumbs-down"></i>
						<span class="rec_count">${hateCount}</span></a>
				</div>
			</div>
			<div>
				<a href="list?boardId=${article.boardId}">목록</a>
			</div>
		</div>
	</div>
</section>
<section class="section-2 con-min-width">
	<div class="con reple-box">
		<div class="comments">
			<span>전체 댓글: 0개</span>
		</div>
		<div class="comment-body">			
			<form name="CommentForm" action="doComment" method="POST"
				onsubmit="DoCommentForm__submit(this); return false;">
				<input type="hidden" name="body" />
				
				<textarea name="body" style="width: 95%" rows="3" cols="30"  placeholder="댓글을 입력하세요"></textarea>
				
				<div class="btn-wrap flex flex-jc-e">
					<button type="submit" class="btn btn-success" href="#">LOGIN</button>
				</div>
			</form>
		</div>

	</div>

</section>
<%@ include file="../../part/foot.jspf"%>