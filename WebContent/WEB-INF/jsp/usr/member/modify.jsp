<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../../part/head.jspf"%>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/js-sha256/0.9.0/sha256.min.js"></script>

<section class="mobile-title-bar con-min-width visible-sm-down">
	<h1 class="con"></h1>
</section>

<div class="title-bar padding-0-10 con-min-width">
	<h1 class="con">
		<span> <i class="fas fa-user"></i>
		</span> <span>회원정보수정</span>
	</h1>
</div>
<div class="modify-form-box form-box padding-0-10 con-min-width">
	<script>
		let DoModifyForm__submited = false;
		let DoModifyForm__checkedLoginId = "";

		// 폼 발송전 체크
		function DoModifyForm__submit(form) {
			if (DoModifyForm__submited) {
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

			form.loginPwConfirm.value = form.loginPwConfirm.value.trim();

			if (form.loginPwConfirm.value.length == 0) {
				alert('로그인 비밀번호 확인을 입력해주세요.');
				form.loginPwConfirm.focus();

				return;
			}

			if (form.loginPw.value != form.loginPwConfirm.value) {
				alert('로그인 비밀번호가 일치하지 않습니다.');
				form.loginPwConfirm.focus();

				return;
			}

			form.name.value = form.name.value.trim();

			form.nickname.value = form.nickname.value.trim();

			if (form.nickname.value.length == 0) {
				alert('별명을 입력해주세요.');
				form.nickname.focus();

				return;
			}

			form.email.value = form.email.value.trim();

			if (form.email.value.length == 0) {
				alert('이메일을 입력해주세요.');
				form.email.focus();

				return;
			}

			form.cellphoneNo.value = form.cellphoneNo.value.trim();

			if (form.cellphoneNo.value.length == 0) {
				alert('전화번호를 입력해주세요.');
				form.cellphoneNo.focus();

				return;
			}

			form.loginPwReal.value = sha256(form.loginPw.value);
			form.loginPw.value = "";
			form.loginPwConfirm.value = "";

			form.submit();
			DoJoinForm__submited = true;
		}
	</script>


	<section class="section-1">
		<div class="con">
			<div class="join-Write">
				<main>
					<form class="con" action="doModify" method="POST"
						onsubmit="DoModifyForm__submit(this); return false;">
						<input type="hidden" name="loginPwReal" /> <input type="hidden"
							name="id" value="${loginedMemberId}" />
						<table>
							<colgroup>
								<col width="150">
							</colgroup>
							<tbody>
								<tr>
									<th><span>로그인 아이디</span></th>
									<td>
										<div>
											<input name="loginId" type="text" maxlength="50" readonly
												value="${loginedMember.loginId}" />
										</div>
									</td>
								</tr>

								<tr>
									<th><span>로그인 비번</span></th>
									<td>
										<div>
											<input name="loginPw" type="password" maxlength="50"
												placeholder="로그인 비밀버호를 입력해주세요." />
										</div>
									</td>
								</tr>

								<tr>
									<th><span>비밀번호 확인</span></th>
									<td>
										<div>
											<input name="loginPwConfirm" type="password" maxlength="50"
												placeholder="로그인 비밀버호 확인을 입력해주세요." />
										</div>
									</td>
								</tr>

								<tr>
									<th><span>이름</span></th>
									<td>
										<div>
											<input name="name" type="text" maxlength="50"
												placeholder="이름을 입력해주세요." value="${loginedMember.name}" />
										</div>
									</td>
								</tr>

								<tr>
									<th><span>별명</span></th>
									<td>
										<div>
											<input name="nickname" type="text" maxlength="50"
												placeholder="별명을 입력해주세요." value="${loginedMember.nickname}" />
										</div>
									</td>
								</tr>

								<tr>
									<th><span>이메일</span></th>
									<td>
										<div>
											<input name="email" type="email" maxlength="100"
												placeholder="이메일을 입력해주세요." value="${loginedMember.email}" />
										</div>
									</td>
								</tr>

								<tr>
									<th><span>전화번호</span></th>
									<td>
										<div>
											<input name="cellphoneNo" type="tel" maxlength="100"
												placeholder="전화번호를 입력해주세요."
												value="${loginedMember.cellphoneNo}" />
										</div>
									</td>
								</tr>

								<tr>
									<th><span>회원정보수정</span></th>
									<td>
										<div>
											<div class="btn-wrap">
												<input class="btn text-white font-semibold py-2 px-4 rounded-lg shadow-md hover:bg-gray-400 focus:outline-none focus:ring-2 focus:ring-gray-700 focus:ring-offset-2 focus:ring-offset-gray-300" type="submit" value="수정" />
												<button class="btn btn-info" type="button"
													onclick="history.back();">뒤로가기</button>
											</div>
										</div>
									</td>
								</tr>
							</tbody>
						</table>
					</form>
				</main>
			</div>
		</div>
	</section>
</div>
<%@ include file="../../part/foot.jspf"%>