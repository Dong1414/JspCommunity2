<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="pageTitle" value="메인화면" />
<%@ include file="../../part/head.jspf"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">

<title>Insert title here</title>

</head>

<body>
<!--
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/static/usrHomeMain.css" />
<script src="${pageContext.request.contextPath}/static/usrHomeMain.js" defer></script>
-->

<section class="title-bar con-min-width">
	<h1 class="con">
		${pageTitle}
	</h1>
</section>
</html>
<%@ include file="../../part/foot.jspf"%> 