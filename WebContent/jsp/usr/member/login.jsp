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
<div class="body-content flex">
	<main class="flex-grow-1 flex-ai-c">
		
		<div class="title-bar padding-0-10 con-min-width">
			<h1 class="con">
				<span><i class="fas fa-sign-in-alt"></i></span> <span>로그인</span>
			</h1>
		</div>

		<div class="login-form-box form-box form-box-50 padding-0-10 con-min-width flex flex-ai-c min-height-100vh">
			<div class="block width-850 con">
			<div class = "flex flex-jc-c">
			<div class = "block con login-top-bar">
				<span class="">HOUSE</span>
			</div>
			</div>
			<form class="con block" name="doLogin" action="doLogin" method="POST"
				onsubmit="DoLoginForm__submit(this); return false;">
				<input type="hidden" name="loginPwReal" />
				<input type="hidden" name="afterLoginUrl" value="${param.afterLoginUrl}" />
				<table>
					<colgroup>
						<col width="150">
					</colgroup>
					<tbody>
						<tr>
							<th><span> ID </span></th>
							<td>
								<div>
									<input type="text" name="loginId" maxlength="50"
										placeholder="로그인아이디를 입력해주세요.">
								</div>
							</td>
						</tr>
						<tr>
							<th><span> PW </span></th>
							<td>
								<div>
									<input type="password" name="loginPw" maxlength="50"
										placeholder="로그인비밀번호를 입력해주세요.">
								</div>
							</td>
						</tr>
						<tr>
							<th><span> 로그인 </span></th>
							<td>
								<div>
									<div class="btn-wrap">
										<button type="submit" class="btn btn-success" href="#">LOGIN</button>
										<a class="btn btn-info" href="../member/join">회원가입</a>
										<a class="btn btn-info" href="../member/findLoginId">아이디찾기</a>
										<a class="btn btn-info" href="../member/findLoginPw">비밀번호찾기</a>
									</div>
								</div>
							</td>
						</tr>
					</tbody>
				</table>
			</form>
			</div>	
		</div>	
	</main>
</div>
<%@ include file="../../part/foot.jspf"%>