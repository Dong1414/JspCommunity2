<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>	
<%@ include file="../../part/head.jspf"%>

<script>
	function check() {
		
		  if(join.loginId.value == "") {

		    alert("ID를 입력해주세요.");

		    join.loginId.focus();

		    return false;

		  }

		  else if(join.loginPw.value == "") {

		    alert("PASSWORD를 입력해 주세요.");

		    join.loginPw.focus();

		    return false;

		  }

		  else if(join.name.value == "") {

			    alert("이름를 입력해 주세요.");

			    join.name.focus();

			    return false;

			  }

		  else if(join.nickName.value == "") {

			    alert("닉네임를 입력해 주세요.");

			    join.nickName.focus();

			    return false;

			  }

		  else if(join.cellPhonNo.value == "") {

			    alert("전화번호를 입력해 주세요.");

			    join.cellPhonNo.focus();

			    return false;

			  }

		  else if(join.email.value == "") {

			    alert("이메일을 입력해 주세요.");

			    join.email.focus();

			    return false;

			  }

		  else return true;

		}
	</script>
<h1>회원가입</h1>

<div> 
	<form name="join" action="doJoin" method="POST" onsubmit="return check();">		
		<hr />
		<div>
			<div>loginId</div>
			<div>
				<input name="loginId" type="text" maxlength="50"
					placeholder="아이디를 입력해주세요." />
					
			</div>
		</div>

		<hr />

		<div>
			<div>loginPw</div>
			<div>
				<input placeholder="패스워드를 입력해주세요." type="password" name="loginPw" maxlength="50"></input>				
			</div>
		</div>
		<hr />
		<div>
			<div>이름</div>
			<div>
				<input placeholder="이름을 입력해주세요." type="text" name="name" maxlength="50"></input>
			</div>
		</div>
		<hr />
		<div>
			<div>닉네임</div>
			<div>
				<input placeholder="닉네임을 입력해주세요." type="text" name="nickName" maxlength="50"></input>
			</div>
		</div>
		<hr />
		<div>
			<div>휴대폰</div>
			<div>
				<input placeholder="전화번호를 입력해주세요." type="number" name="cellPhonNo" maxlength="50"></input>
			</div>
		</div>
		
		<hr />
		<div>
			<div>e-mail</div>
			<div>
				<input placeholder="e-mail을 입력해주세요." type="email" name="email" maxlength="50"></input>
		</div>
			<div>
				<input type="submit" value="가입"  />
				<button type="button" onclick="history.back();">뒤로가기</button>
			</div>
		</div>
	</form>
	
</div>
<%@ include file="../../part/foot.jspf"%>