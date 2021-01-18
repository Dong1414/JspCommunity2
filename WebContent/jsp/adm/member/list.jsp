<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="pageTitle" value="회원리스트" />
<%@ include file="../../part/head.jspf"%>

<h1>회원리스트</h1>

<c:forEach var="member" items="${members}">
	<div>
		번호 : ${member.id}
		<br />
		이름 : ${member.name}
		<br />
		닉네임 : ${member.nickname}
		<hr />
	</div>
</c:forEach>

<%@ include file="../../part/foot.jspf"%>