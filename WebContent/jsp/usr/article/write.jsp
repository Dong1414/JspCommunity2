<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="pageTitle" value="${board.name} 게시물 작성" />
<%@ include file="../../part/head.jspf"%>

<section class="section-1 con">
<h1>${pageTitle}</h1>
</section>
<script>
	function check() {

		if (doWrite.title.value == "") {

			alert("제목을 입력해주세요.");

			doWrite.title.focus();

			return false;

		}

		else if (doWrite.body.value == "") {

			alert("내용을 입력해 주세요.");

			doWrite.body.focus();

			return false;

		} else
			return true;
	}
</script>
<section class="section-2">
	<div class="con">
		<div class="join-detail">
			<main>
				<form name="doWrite" action="doWrite" method="POST"
					onsubmit="return check();">
					<input type="hidden" name="boardId" value="${board.id}" /> <input
						type="hidden" name="memberId" value="${loginedMemberId}" />

					<hr />
					<div>
						<div>제목</div>
						<div>
							<input class="input-title" name="title" type="text" maxlength="50"
								placeholder="제목을 입력해주세요." />
						</div>
					</div>

					<hr />

					<div>
						<div>내용</div>
						<div>
							<textarea class="textarea-body" placeholder="내용을 입력해주세요." name="body" maxlength="5000"></textarea>
						</div>
					</div>
					<hr />
					<div>
						<div>작성</div>
						<div>
							<input type="submit" value="작성" />
							<button type="button" onclick="history.back();">뒤로가기</button>
						</div>
					</div>
				</form>
			</main>
		</div>
	</div>
</section>
<%@ include file="../../part/foot.jspf"%>