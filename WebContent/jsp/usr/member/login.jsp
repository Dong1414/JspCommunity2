<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>	
<%@ include file="../../part/head.jspf"%>
<h1>로그인</h1>

<div>
	<form action="doLogin" method="POST">		
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
				<textarea placeholder="패스워드를 입력해주세요." name="loginPw" maxlength="5000"></textarea>				
			</div>
		</div>
		<hr />
			<div>
				<input type="submit" value="login" />
				<button type="button" onclick="history.back();">뒤로가기</button>
			</div>
		</div>
	</form>
</div>
<%@ include file="../../part/foot.jspf"%>