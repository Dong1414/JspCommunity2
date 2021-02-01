<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../../part/head.jspf"%>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/js-sha256/0.9.0/sha256.min.js"></script>
<script>
	let DoLoginForm__submited = false;
	function DoLoginForm__submit(form) {
		if (DoLoginForm__submited) {
			alert('처리중입니다.');
			return;
		}

		form.loginId.value = form.loginId.value.trim();

		if (form.loginId.value.length == 0) {
			alert('로그인 아이디를 입력해주세요.');
			form.loginId.focus();

			return;
		}

		form.loginPw.value = form.loginPw.value.trim();

		if (form.loginPw.value.length == 0) {
			alert('로그인 비밀번호를 입력해주세요.');
			form.loginPw.focus();

			return;
		}

		form.loginPwReal.value = sha256(form.loginPw.value);

		form.loginPw.value = "";

		form.submit();
		DoLoginForm__submited = true;
	}
</script>
<section class="section-1 con">
	<h1><i class="fas fa-sign-in-alt"></i></h1>
	<h1>로그인</h1>
</section>
<section class="section-2">
	<div class="con">
		<div class="login-detail">
			<main>
				<form name="doLogin" action="doLogin" method="POST"
					onsubmit="DoLoginForm__submit(this); return false;">
					<input type="hidden" name="loginPwReal" />
					<hr />
					<div>
						<div>ID</div>
						<div>
							<input name="loginId" type="text" maxlength="50"
								placeholder="아이디를 입력해주세요." />
						</div>
					</div>

					<hr />

					<div>
						<div>PW</div>
						<div>
							<input name="loginPw" type="password" maxlength="50"
								placeholder="패스워드를 입력해주세요." />
						</div>

						<hr />
						<div>
							<input type="submit" value="login" />
							<button type="button" onclick="history.back();">뒤로가기</button>
						</div>
					</div>
				</form>
			</main>
		</div>
	</div>
</section>
<%@ include file="../../part/foot.jspf"%>