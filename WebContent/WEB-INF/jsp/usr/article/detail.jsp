<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.sbs.example.util.Util"%>
<c:set var="pageTitle" value="${article.extra__boardName} 게시물 상세페이지" />
<c:set var="memberID" value="${article.memberId}" />
<%@ include file="../../part/head.jspf"%>
<script>
	$(function() {
		if ( param.focusReplyId ) {
			const $target = $('.reply-list-box tr[data-id="' + param.focusReplyId + '"]');
			$target.addClass('focus');
		
			setTimeout(function() {
				const targetOffset = $target.offset();
				
				$(window).scrollTop(targetOffset.top - 100);
				
				setTimeout(function() {
					$target.removeClass('focus');
				}, 1000);
			}, 1000);
		}
	});
</script>
<section class="mobile-title-bar con-min-width visible-sm-down">
	<h1 class="con"></h1>
</section>
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
					<div class="article-detail__cell-reg-date">${article.regDate}</div>
					<div class="article-detail__hit-count">
						<i class="fas fa-eye"></i> ${article.hitsCount}
					</div>
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


			<div class="like-hate con flex flex-jc-c ">
				<c:if test="${article.extra.actorCanLike}">
					<div class="like flex flex-ai-c basic">

						<a class="btn btn-primary"
							href="../like/doLike?relTypeCode=article&relId=${article.id}&redirectUrl=${encodedCurrentUrl}"
							onclick="if ( !confirm('`좋아요` 처리 하시겠습니까?') ) return false;">
							<span> <span> <i class="fas fa-thumbs-up"></i> <span>
										${article.extra__likeOnlyPoint}</span>
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
								<span> <span> <i class="far fa-thumbs-up"></i> <span>
											${article.extra__likeOnlyPoint}</span>
								</span>
							</span>
							</a>
						</div>
						<div class="flex flex-ai-c">
							<a class="btn btn-danger" href="#"> <span> <span>
										<i class="far fa-thumbs-down"></i> <span>
											${article.extra__dislikeOnlyPoint}</span>
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

							<span> <span> <i class="far fa-thumbs-down"></i> <span>
										${article.extra__dislikeOnlyPoint}</span>
							</span>
						</span>
						</a>
					</div>
				</c:if>
				<c:if test="${article.extra.actorCanCancelDislike}">
					<div class="Dislike flex flex-ai-c">
						<div class="flex flex-ai-c">
							<a class="btn btn-danger" href="#"> <span> <span>
										<i class="far fa-thumbs-up"></i> <span>
											${article.extra__likeOnlyPoint}</span>
								</span>
							</span>
							</a>
						</div>

						<div class="flex flex-ai-c">
							<a class="btn btn-info"
								href="../like/doCancelDislike?relTypeCode=article&relId=${article.id}&redirectUrl=${encodedCurrentUrl}"
								onclick="if ( !confirm('`싫어요`를 취소 처리 하시겠습니까?') ) return false;">
								<span> <span> <i class="far fa-thumbs-down"></i> <span>
											${article.extra__dislikeOnlyPoint}</span>
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
<div class="title-bar padding-0-10 con-min-width">
	<h1 class="con">
		<span> <i class="fas fa-newspaper"></i>
		</span> <span>댓글작성</span>
	</h1>
</div>

<c:if test="${isLogined == false}">
	<div
		class="article-reply-write-form-box form-box padding-0-10 con-min-width">
		<div class="con">
			<a class="udl hover-link"
				href="../member/login?afterLoginUrl=${encodedCurrentUrl}">로그인</a> 후
			이용해주세요.
		</div>
	</div>
</c:if>
<c:if test="${isLogined}">
	<div
		class="article-reply-write-form-box form-box padding-0-10 con-min-width">
		<script>
		let Reply__DoWriteForm__submited = false;
		let Reply__DoWriteForm__checkedLoginId = "";
		// 폼 발송전 체크
		function Reply__DoWriteForm__submit(form) {
			if ( Reply__DoWriteForm__submited ) {
				alert('처리중입니다.');
				return;
			}
				
			const editor = $(form).find('.toast-ui-editor').data('data-toast-editor');
			const body = editor.getMarkdown().trim();
			
			if ( body.length == 0 ) {
				alert('내용을 입력해주세요.');
				editor.focus();
				
				return;
			}
			
			form.body.value = body;
			form.submit();
			Reply__DoWriteForm__submited = true;
	}
	</script>

		<form class="con" action="../reply/doWrite" method="POST"
			onsubmit="Reply__DoWriteForm__submit(this); return false;">
			<input type="hidden" name="redirectUrl" value="${Util.getNewUrl(currentUrl, 'focusReplyId', '[NEW_REPLY_ID]')}" />
			 <input
				type="hidden" name="relTypeCode" value="article" /> <input
				type="hidden" name="relId" value="${article.id}" /> <input
				type="hidden" name="body" />

			<table>
				<colgroup>
					<col width="150">
				</colgroup>
				<tbody>
					<tr>
						<th><span>내용</span></th>
						<td>
							<div>
								<div>
									<script type="text/x-template"></script>
									<div class="toast-ui-editor" data-height="200"></div>
								</div>
							</div>
						</td>
					</tr>

					<tr>
						<th><span>작성</span></th>
						<td>
							<div>
								<div class="btn-wrap">
									<input class="btn btn-primary" type="submit" value="작성" />
									<button class="btn btn-info" type="button"
										onclick="history.back();">뒤로가기</button>
								</div>
							</div>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
</c:if>

<div class="reply-list-total-count-box padding-0-10 con-min-width">
	<div class="con">
		<div>
			<span>
				<i class="fas fa-clipboard-list"></i>
			</span>
			<span>댓글: </span>
			<span class="color-red"> ${replies.size()} </span>
		</div>
	</div>
</div>

<script>
let DoModifyForm__submited = false;
let DoModifyForm__checkedLoginId = "";
// 폼 발송전 체크
function DoModifyForm__submit(form) {
	if (DoModifyForm__submited) {
		alert('처리중입니다.');
		return;
	}
	const editor = $(form).find('.toast-ui-editor').data(
			'data-toast-editor');
	const body = editor.getMarkdown().trim();
	if (body.length == 0) {
		alert('내용dd을 입력해주세요.');
		editor.focus();
		return;
	}
	form.body.value = body;
	form.submit();
	DoModifyForm__submited = true;
}
</script>

<div class="reply-list-box response-list-box padding-0-10 con-min-width">
	<div class="con">
		<table>
			<colgroup>
				<col width="50">
				<col width="150">
				<col width="100">
				<col width="100">
				<col width="200">
			</colgroup>
			<thead>
				<tr>
					<th>번호</th>
					<th>날짜</th>
					<th>작성자</th>
					<th>좋아요</th>
					<th>비고</th>
					<th>내용</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${replies}" var="reply">
					<tr data-id="${reply.id}">
						<td>
							<span class="response-list-box__id">${reply.id}</span>
						</td>
						<td>
							<span class="response-list-box__reg-date">${reply.regDate}</span>
						</td>
						<td>
							<span class="response-list-box__writer">${reply.extra__writer}</span>
						</td>			
						
						<td>
							<c:if test="${article.extra.actorCanLike}">
							<span class="response-list-box__likeOnlyPoint">
								<a href="../like/doLike?relTypeCode=reply&relId=${reply.id}&redirectUrl=${encodedCurrentUrl}"
							onclick="if ( !confirm('`좋아요` 처리 하시겠습니까?') ) return false;">
									<i class="far fa-thumbs-up"></i>
								</a>
								<span> ${reply.extra__likeOnlyPoint} </span>
							</span>
							</c:if>
							
							<c:if test="${article.extra.actorCanCancelLike}">
							<span class="response-list-box__likeOnlyPoint">
								<a href="../like/doCancelLike?relTypeCode=reply&relId=${reply.id}&redirectUrl=${encodedCurrentUrl}"
								onclick="if ( !confirm('`좋아요`를 취소 처리 하시겠습니까?') ) return false;">
									<i class="fas fa-thumbs-up"></i>
								</a>
								<span> ${reply.extra__likeOnlyPoint} </span>
							</span>
							
								<span class="response-list-box__dislikeOnlyPoint">
									<span>
										<i class="far fa-thumbs-down"></i>
									</span>
									<span> ${reply.extra__dislikeOnlyPoint} </span>
								</span>
							</c:if>
							
								<c:if test="${article.extra.actorCanDislike}">
								<span class="response-list-box__dislikeOnlyPoint">
									<a href="../like/doDislike?relTypeCode=reply&relId=${reply.id}&redirectUrl=${encodedCurrentUrl}"
							onclick="if ( !confirm('`싫어요` 처리 하시겠습니까?') ) return false;">
										<i class="far fa-thumbs-down"></i>
									</a>
									<span> ${reply.extra__dislikeOnlyPoint} </span>
								</span>
								
								</c:if>
								
								<c:if test="${article.extra.actorCanCancelDislike}">
									<span class="response-list-box__likeOnlyPoint">
								<a href="#">
									<i class="far fa-thumbs-up"></i>
								</a>
								<span> ${reply.extra__likeOnlyPoint} </span>
							</span>
							
							<span class="response-list-box__dislikeOnlyPoint">
									<a href="../like/doCancelDislike?relTypeCode=reply&relId=${reply.id}&redirectUrl=${encodedCurrentUrl}"
								onclick="if ( !confirm('`싫어요`를 취소 처리 하시겠습니까?') ) return false;">
										<i class="fas fa-thumbs-down"></i>
									</a>
									<span> ${reply.extra__dislikeOnlyPoint} </span>
								</span>
							
								</c:if>
						</td>
																	
						<td>
						<c:if test="${loginedMemberId == reply.memberId}">
							<div>
								<a  
									onclick="if ( confirm('정말 수정하시겠습니까?') == false ) { return false; }"
									href="../reply/modify?id=${reply.id}&redirectUrl=${encodedCurrentUrl}">수정</a>
								<a 
									onclick="if ( confirm('정말 삭제하시겠습니까?') == false ) { return false; }"
									href="../reply/doDelete?id=${reply.id}&redirectUrl=${encodedCurrentUrl}">삭제</a>
							</div>
							</c:if>
						</td>
						
						<td>
							<script type="text/x-template">${reply.body}</script>
							<div class="toast-ui-viewer"></div>
						</td>
						<c:if test="${modifyMode == reply.id}">
							<tr>
							<td colspan="6">
								<form name="ModifyForm"
											action="../reply/doModify?id=${reply.id}&redirectUrl=${encodedCurrentUrl}"
											method="POST"
											onsubmit="DoModifyForm__submit(this); return false;">
											<input type="hidden" name="body" /> <input type="hidden"
												name="replyId" value="${reply.id}" />

											<script type="text/x-template">${reply.body}</script>
											<div class="toast-ui-editor" data-height="150"></div>
											<button type="submit" class="btn btn-success">작성</button>
											<a class="btn btn-info"
												href="../article/doCancelModifyReply?relTypeCode=article&relId=${article.id}&redirectUrl=${encodedCurrentUrl}"
												onclick="if ( !confirm('댓글 수정을 취소하시겠습니까?') ) return false;">취소</a>
										</form>
										</td>
								</tr>					
						</c:if>
						<td class="visible-sm-down">
							<div class="flex">
								<span class="response-list-box__id response-list-box__id--mobile">${reply.id}</span>
							</div>

							<div class="flex">
								<span class="response-list-box__likeOnlyPoint">
									<span>
										<i class="far fa-thumbs-up"></i>
									</span>
									<span> ${reply.extra__likeOnlyPoint} </span>
								</span>
								<span class="response-list-box__dislikeOnlyPoint">
									<span>
										<i class="far fa-thumbs-down"></i>
									</span>
									<span> ${reply.extra__dislikeOnlyPoint} </span>
								</span>
							</div>

							<div class="flex">
								<span
									class="response-list-box__writer response-list-box__writer--mobile">${reply.extra__writer}</span>
								<span>&nbsp;|&nbsp;</span>
								<span
									class="response-list-box__reg-date response-list-box__reg-date--mobile">${reply.regDate}</span>
							</div>
							<c:if test="${loginedMemberId == reply.memberId}">
							<div>
								<a  
									onclick="if ( confirm('정말 수정하시겠습니까?') == false ) { return false; }"
									href="../reply/modify?id=${reply.id}&redirectUrl=${encodedCurrentUrl}">수정</a>
								<a 
									onclick="if ( confirm('정말 삭제하시겠습니까?') == false ) { return false; }"
									href="../reply/doDelete?id=${reply.id}&redirectUrl=${encodedCurrentUrl}">삭제</a>
							</div>
							</c:if>
							<div>
							
								<script type="text/x-template">${reply.body}</script>
								<div class="toast-ui-viewer"></div>
							</div>
						</td>
						
					</tr>					
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>
<%@ include file="../../part/foot.jspf"%>