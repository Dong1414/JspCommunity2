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
					<a href="modify?id=${article.id}">수정</a>
					<a
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


			<div class="like-hate con flex flex-jc-c ">
				<c:if test="${article.extra.actorCanLike}">
					<div class="like flex flex-ai-c basic">

						<a class="btn btn-primary"
							href="../like/doLike?relTypeCode=article&relId=${article.id}&redirectUrl=${encodedCurrentUrl}"
							onclick="if ( !confirm('`좋아요` 처리 하시겠습니까?') ) return false;">
							<span>
								<span>
									<i class="fas fa-thumbs-up"></i>
									<span> ${likeCount}</span>
								</span>
							</span>
						</a>
					</div>
				</c:if>
				<c:if test="${article.extra.actorCanCancelLike}">
					<div class="like flex flex-ai-c">
						<div class="flex flex-ai-c">
							<a class="btn btn-info"
								href="../like/doCancelLike?relTypeCode=article&relId=${article.id}&redirectUrl=${encodedCurrentUrl}"
								onclick="if ( !confirm('`좋아요`를 취소 처리 하시겠습니까?') ) return false;">
								<span>
									<span>
										<i class="far fa-thumbs-up"></i>
										<span> ${likeCount}</span>
									</span>
								</span>
							</a>
						</div>
						<div class="flex flex-ai-c">
						<a class="btn btn-danger" href="#" >
								<span>
							<span>
								<i class="far fa-thumbs-down"></i>
								<span> ${hateCount}</span>
							</span>
							</span>
							</a>
						</div>
					</div>
				</c:if>

				<c:if test="${article.extra.actorCanDislike}">
					<div class="Dislike flex flex-ai-c basic">

						<a class="btn btn-danger"
							href="../like/doDislike?relTypeCode=article&relId=${article.id}&redirectUrl=${encodedCurrentUrl}"
							onclick="if ( !confirm('`싫어요` 처리 하시겠습니까?') ) return false;">

							<span>
								<span>
									<i class="far fa-thumbs-down"></i>
									<span> ${hateCount}</span>
								</span>
							</span>
						</a>
					</div>
				</c:if>
				<c:if test="${article.extra.actorCanCancelDislike}">
					<div class="Dislike flex flex-ai-c">
						<div class="flex flex-ai-c">
							<a class="btn btn-danger" href="#">							
							<span>
							<span>
								<i class="far fa-thumbs-up"></i>
								<span> ${likeCount}</span>
								</span>
							</span>
							</a>
						</div>

						<div class="flex flex-ai-c">
							<a class="btn btn-info"
								href="../like/doCancelDislike?relTypeCode=article&relId=${article.id}&redirectUrl=${encodedCurrentUrl}"
								onclick="if ( !confirm('`싫어요`를 취소 처리 하시겠습니까?') ) return false;">
								<span>
									<span>
										<i class="far fa-thumbs-down"></i>
										<span> ${hateCount}</span>
									</span>
								</span>
							</a>
						</div>

					</div>
				</c:if>

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
			<form name="CommentForm" action="doComment?relTypeCode=article&relId=${article.id}&redirectUrl=${encodedCurrentUrl}" method="POST"
				onsubmit="DoDetailForm__submit(this); return false;">
				<input type="hidden" name="body" />
				<c:if test="${isLogined}">
					<script type="text/x-template"></script>
					<div class="toast-ui-editor-comment" style="width: 95%"></div>


					<div class="btn-wrap flex flex-jc-e">
						<button type="submit" class="btn btn-success" href="../article/doComment?relTypeCode=article&relId=${article.id}&redirectUrl=${encodedCurrentUrl}">작성</button>
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
								<th>
									<span> ${reply.memberId} </span>
								</th>
								<td>
									<script type="text/x-template">${reply.body}</script>
									<div class="toast-ui-viewer"></div>
								</td>
								<td>
									<div>${reply.updateDate}</div>
								</td>
								<c:if test="${sessionScope.loginedMemberId == reply.memberId}">
									<td>
										<a href="../article/doComment?relTypeCode=article&relId=${article.id}&redirectUrl=${encodedCurrentUrl}">수정</a>
									</td>
									<td>
										<a href="../article/doReplyDelete?relTypeCode=article&relId=${article.id}&redirectUrl=${encodedCurrentUrl}&id=${reply.id}">삭제</a>
									</td>
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