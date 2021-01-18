<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>	
<%@ include file="../../part/head.jspf"%>
<h1>회원가입</h1>

<div>
	<form action="doJoin" method="POST">		
		<hr />
		<div>
			<div>loginId</div>
			<div>
				<input name="loginId" type="text" maxlength="50"
					placeholder="아이디를 입력해주세요." />
					<input type="submit" value="중복확인" />
			</div>
		</div>

		<hr />

		<div>
			<div>loginPw</div>
			<div>
				<textarea placeholder="패스워드를 입력해주세요." name="loginPw" maxlength="5000"></textarea>				
			</div>
		</div>
		<hr />
		<div>
			<div>이름</div>
			<div>
				<textarea placeholder="이름을 입력해주세요." name="name" maxlength="5000"></textarea>
			</div>
		</div>
		<hr />
		<div>
			<div>닉네임</div>
			<div>
				<textarea placeholder="닉네임을 입력해주세요." name="nickName" maxlength="5000"></textarea>
			</div>
		</div>
		<hr />
		<div>
			<div>휴대폰</div>
			<div>
				<textarea placeholder="전화번호를 입력해주세요." name="cellPhonNo" maxlength="5000"></textarea>
			</div>
		</div>
		
		<hr />
		<div>
			<div>e-mail</div>
			<div>
				<textarea placeholder="e-mail을 입력해주세요." name="email" maxlength="5000"></textarea>
		</div>
			<div>
				<input type="submit" value="가입" />
				<button type="button" onclick="history.back();">뒤로가기</button>
			</div>
		</div>
	</form>
</div>
<%@ include file="../../part/foot.jspf"%>