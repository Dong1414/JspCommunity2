<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../../part/head.jspf"%>
<script src="https://cdnjs.cloudflare.com/ajax/libs/js-sha256/0.9.0/sha256.min.js"></script>
<div>
	<script>
	let DoModifyForm__submited = false;
	let DoModifyForm__checkedLoginId = "";
	

	// 폼 발송전 체크
	function DoModifyForm__submit(form) {
		if ( DoModifyForm__submited ) {
			alert('처리중입니다.');
			return;
		}
	
		form.loginId.value = form.loginId.value.trim();
	
		if ( form.loginId.value.length == 0 ) {
			alert('로그인 아이디를 입력해주세요.');
			form.loginId.focus();
			
			return;
		}
		
		form.loginPw.value = form.loginPw.value.trim();
	
		if ( form.loginPw.value.length == 0 ) {
			alert('로그인 비밀번호를 입력해주세요.');
			form.loginPw.focus();
			
			return;
		}
		
		form.loginPwConfirm.value = form.loginPwConfirm.value.trim();
	
		if ( form.loginPwConfirm.value.length == 0 ) {
			alert('로그인 비밀번호 확인을 입력해주세요.');
			form.loginPwConfirm.focus();
			
			return;
		}
		
		if ( form.loginPw.value != form.loginPwConfirm.value ) {
			alert('로그인 비밀번호가 일치하지 않습니다.');
			form.loginPwConfirm.focus();
			
			return;
		}
		
		form.name.value = form.name.value.trim();
	
		form.nickname.value = form.nickname.value.trim();
	
		if ( form.nickname.value.length == 0 ) {
			alert('별명을 입력해주세요.');
			form.nickname.focus();
			
			return;
		}
		
		form.email.value = form.email.value.trim();
	
		if ( form.email.value.length == 0 ) {
			alert('이메일을 입력해주세요.');
			form.email.focus();
			
			return;
		}
		
		form.cellphoneNo.value = form.cellphoneNo.value.trim();
	
		if ( form.cellphoneNo.value.length == 0 ) {
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
			<div class="join-detail">
				<main>
					<form action="doModify" method="POST"
						onsubmit="DoModifyForm__submit(this); return false;">
						<input type="hidden" name="loginPwReal" />
						<input type="hidden" name="id" value="${loginedMemberId}"/>
						<hr />
						<div>
							<div>로그인 아이디</div>
							<div>
								<input name="loginId" type="text" maxlength="50" readonly
									value="${member.loginId}" />							
							</div>
						</div>

						<hr />
						<div>
							<div>로그인 비번</div>
							<div>
								<input name="loginPw" type="password" maxlength="50"/>
							</div>
						</div>

						<hr />
						<div>
							<div>로그인 비밀번호 확인</div>
							<div>
								<input name="loginPwConfirm" type="password" maxlength="50" />
							</div>
						</div>
						<hr />
						<div>
							<div>이름</div>
							<div>
								<input name="name" type="text" maxlength="50" readonly value="${member.name}"/>
							</div>
						</div>
						<hr />
						<div>
							<div>별명</div>
							<div>
								<input name="nickname" type="text" maxlength="50" value="${member.nickname}"/>
							</div>
						</div>
						<hr />
						<div>
							<div>이메일</div>
							<div>
								<input name="email" type="email" maxlength="100" value="${member.email}" />
							</div>
						</div>
						<hr />
						<div>
							<div>전화번호</div>
							<div>
								<input name="cellphoneNo" type="tel" maxlength="100" value="${member.cellphoneNo}"/>
							</div>
						</div>
						<hr />
						<div>
							<div>수정</div>
							<div>
							<input type="submit" value="수정" onclick="if ( confirm('정말 수정하시겠습니까?') == false ) { return false; }"/>										
								<button type="button" onclick="history.back();">뒤로가기</button>
							</div>
						</div>
					</form>
				</main>
			</div>
		</div>
	</section>
</div>
<%@ include file="../../part/foot.jspf"%>