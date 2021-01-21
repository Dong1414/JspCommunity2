<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../../part/head.jspf"%>

<script>
	function check() {

		if (doLogin.loginId.value == "") {

			alert("ID를 입력해주세요.");

			doLogin.loginId.focus();

			return false;

		}

		else if (doLogin.loginPw.value == "") {

			alert("PASSWORD를 입력해 주세요.");

			doLogin.loginPw.focus();

			return false;

		} else
			return true;
	}
</script>
<section class="section-1 con">
	<h1>로그인</h1>
</section>
<section class="section-2">
	<div class="con">
		<div class="login-detail">
			<main>
				<form name="doLogin" action="doLogin" method="POST"
					onsubmit="return check();">
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