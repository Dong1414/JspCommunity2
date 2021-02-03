<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="pageTitle" value="${article.extra__boardName} 게시물 상세페이지" />
<c:set var="memberID" value="${article.memberId}" />
<%@ include file="../../part/head.jspf"%>

<script>
	let DoDetailForm__submited = false;
	let DoDetailForm__checkedLoginId = "";

	// 폼 발송전 체크
	function DoDetailForm__submit(form) {
		if (DoDetailForm__submited) {
			alert('처리중입니다.');
			return;
		}

		const editor = $(form).find('.toast-ui-editor-comment').data(
				'data-toast-editor');
		const body = editor.getMarkdown().trim();

		if (body.length == 0) {
			alert('내용을 입력해주세요.');
			editor.focus();

			return;
		}

		form.body.value = body;

		form.submit();
		DoDetailForm__submited = true;
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
					<a href="doLike"> <c:if test="${likeCheck}">
							<i class="fas fa-thumbs-up"></i>
						</c:if> <c:if test="${likeCheck == false}">
							<i class="far fa-thumbs-up"></i>
						</c:if> <c:if test="${isLogined == false}">
							<i class="far fa-thumbs-up"></i>
						</c:if> <span>${likeCount}</span></a>
				</div>
				<div class="hate flex flex-ai-c">
					<a href="doHate"> <c:if test="${hateCheck}">
							<i class="fas fa-thumbs-down"></i>
						</c:if> <c:if test="${hateCheck == false}">
							<i class="far fa-thumbs-down"></i>
						</c:if> <c:if test="${isLogined == false}">
							<i class="far fa-thumbs-down"></i>
						</c:if> <span class="rec_count">${hateCount}</span></a>
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
			<span>전체 댓글: </span>
		</div>
		<div class="comment-body">
			<form name="CommentForm" action="doComment" method="POST"
				onsubmit="DoDetailForm__submit(this); return false;">
				<input type="hidden" name="body" />
				<c:if test="${isLogined}">
					<script type="text/x-template"></script>
					<div class="toast-ui-editor-comment" style="width: 95%"></div>


					<div class="btn-wrap flex flex-jc-e">
						<button type="submit" class="btn btn-success" href="#">작성</button>
					</div>
				</c:if>
			</form>
		</div>
		<div class="comment-main con">
			<c:if test="${replys.size() < 1}">
				<script type="text/x-template">등록된 댓글이 없습니다.</script>
				<div class="toast-ui-viewer"></div>
			</c:if>
			<c:if test="${replys.size() > 0}">
				<table>
					<colgroup>
						<col width="100">
						<col width="800">
						<col width="200">
						<col width="70">
						<col width="70">
					</colgroup>
					<tbody>

						<c:forEach items="${replys}" var="reply">
							<tr>
								<th><span> ${reply.memberId} </span></th>
								<td><script type="text/x-template">${reply.body}</script>
									<div class="toast-ui-viewer"></div></td>
								<td>
									<div>${reply.updateDate}</div>
								</td>
								<c:if test="${sessionScope.loginedMemberId == reply.memberId}">
									<td><a href="doReplyModify?id=${reply.id}">수정</a></td>
									<td><a href="doReplyDelete?id=${reply.id}">삭제</a></td>
								</c:if>
							</tr>
						</c:forEach>

					</tbody>
				</table>
			</c:if>

		</div>


	</div>


</section>
<%@ include file="../../part/foot.jspf"%>