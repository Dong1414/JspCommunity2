<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="pageTitle" value="${article.extra__boardName} 게시물 상세페이지" />
<c:set var="memberID" value="${article.memberId}" />
<%@ include file="../../part/head.jspf"%>
<section class="section-1 con">
	<h1>${pageTitle}</h1>
</section>
<script>
$(function(){
	// 추천버튼 클릭시(추천 추가 또는 추천 제거)
	$(".like").click(function(){
		$.ajax({
			url: "../article/doLike",
            type: "POST",
            data: {
                no: ${article.id},
                id: '${id}'
            },
            success: function () {
		        recCount();
            },
		})
	})
	
	// 게시글 추천수
    function recCount() {
		$.ajax({
			url: "../article/doLikeCount",
            type: "POST",
            data: {
                no: $${article.id}
            },
            success: function (count) {
            	$(".rec_count").html(count);
            },
		})
    };
    recCount(); // 처음 시작했을 때 실행되도록 해당 함수 호출
</script>
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
			
			
			<div class="like-hate con flex flex-jc-c">
				<div class="like flex flex-ai-c"><a href="">좋아요<span class="rec_count"></span></a></div>
				<div class="hate flex flex-ai-c"><a href="#">싫어요<spanclass="rec_count"></span></a></div>
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
			<textarea style="width: 95%" rows="3" cols="30" id="comment"
				name="comment" placeholder="댓글을 입력하세요"></textarea>
		</div>
		<div class="btn-wrap flex flex-jc-e">
			<button type="submit" class="btn btn-success" href="#">등록</button>
		</div>
	</div>
</section>


<%@ include file="../../part/foot.jspf"%>