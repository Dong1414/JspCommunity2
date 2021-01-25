package com.sbs.example.jspCommunity.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sbs.example.jspCommunity.container.Container;
import com.sbs.example.jspCommunity.dto.Member;
import com.sbs.example.jspCommunity.dto.ResultData;
import com.sbs.example.jspCommunity.service.EmailService;
import com.sbs.example.jspCommunity.service.MemberService;

public class UsrMemberController {
	private MemberService memberService;

	public UsrMemberController() {
		memberService = Container.memberService;
	}

	public String join(HttpServletRequest req, HttpServletResponse resp) {

		return "usr/member/join";
	}

	public String doJoin(HttpServletRequest req, HttpServletResponse resp) {

		String loginId = (String) req.getParameter("loginId");
		String loginPw = (String) req.getParameter("loginPwReal");
		String cellphoneNo = (String) req.getParameter("cellphoneNo");
		String name = (String) req.getParameter("name");
		String email = (String) req.getParameter("email");
		String nickname = (String) req.getParameter("nickname");

		Member member = memberService.getMemberLoginId(loginId);

		if (member != null) {
			req.setAttribute("alertMsg", loginId + "는 이미 존재하는 아이디 입니다.");
			req.setAttribute("historyBack", true);
			return "common/redirect";
		}

		Map<String, Object> joinArgs = new HashMap<>();
		joinArgs.put("loginId", loginId);
		joinArgs.put("loginPw", loginPw);
		joinArgs.put("cellphoneNo", cellphoneNo);
		joinArgs.put("name", name);
		joinArgs.put("email", email);
		joinArgs.put("nickname", nickname);

		memberService.join(joinArgs);

		req.setAttribute("alertMsg", nickname + "님이 회원이 가입되었습니다.");
		req.setAttribute("replaceUrl", String.format("../home/main"));
		
		EmailService emailService = Container.emailService;
		emailService.send((String)joinArgs.get("email"), "JSP커뮤니티입니다. 회원가입을 축하드립니다", "감사합니다.");
		
		return "common/redirect";
	}

	public String login(HttpServletRequest req, HttpServletResponse resp) {
		return "usr/member/login";
	}

	public String doLogin(HttpServletRequest req, HttpServletResponse resp) {
		String loginId = (String) req.getParameter("loginId");
		String loginPw = (String) req.getParameter("loginPwReal");

		Member member = memberService.getMemberLoginId(loginId);

		if (member == null) {
			req.setAttribute("alertMsg", loginId + "은 존재하지 않는 아이디 입니다.");
			req.setAttribute("historyBack", true);
			return "common/redirect";
		}

		if (!member.getLoginPw().equals(loginPw)) {
			req.setAttribute("alertMsg", "비밀번호가 일치하지 않습니다.");
			req.setAttribute("historyBack", true);
			return "common/redirect";
		}

		int loginedMemberId = member.getId();
		HttpSession session = req.getSession();
		session.setAttribute("loginedMemberId", loginedMemberId);
		session.setAttribute("loginedMemberNickname", member.getNickname());

		req.setAttribute("alertMsg", member.getNickname() + "님이 로그인하였습니다.");
		req.setAttribute("replaceUrl", String.format("../home/main"));
		return "common/redirect";
	}

	public String doLogout(HttpServletRequest req, HttpServletResponse resp) {

		HttpSession session = req.getSession();
		session.removeAttribute("loginedMemberId");

		req.setAttribute("alertMsg", "로그아웃 되었습니다.");
		req.setAttribute("replaceUrl", "../home/main");
		return "common/redirect";
	}

	public String getLoginIdDup(HttpServletRequest req, HttpServletResponse resp) {
		String loginId = req.getParameter("loginId");

		Member member = memberService.getMemberLoginId(loginId);

		

		String resultCode = null;
		String msg = null;

		if (member != null) {
			resultCode = "F-1";
			msg = "이미 사용중인 로그인아이디 입니다.";
		} else if (loginId.equals("")) {
			resultCode = "F-2";
			msg = "공백은 입력 할 수 없습니다.";
		} else {
			resultCode = "S-1";
			msg = "사용가능한 로그인아이디 입니다.";
		}

		req.setAttribute("data", new ResultData(resultCode, msg, "loginId", loginId));
		return "common/json";
	}
	public String showFindLoginId(HttpServletRequest req, HttpServletResponse resp) {
		HttpSession session = req.getSession();

		if (session.getAttribute("loginedMemberId") != null) {
			req.setAttribute("alertMsg", "로그아웃 후 진행해주세요.");
			req.setAttribute("historyBack", true);
			return "common/redirect";
		}

		return "usr/member/findLoginId";
	}

	public String doFindLoginId(HttpServletRequest req, HttpServletResponse resp) {
		HttpSession session = req.getSession();

		if (session.getAttribute("loginedMemberId") != null) {
			req.setAttribute("alertMsg", "로그아웃 후 진행해주세요.");
			req.setAttribute("historyBack", true);
			return "common/redirect";
		}

		String name = req.getParameter("name");
		String email = req.getParameter("email");

		Member member = memberService.getMemberByNameAndEmail(name, email);

		if (member == null) {
			req.setAttribute("alertMsg", "일치하는 회원이 존재하지 않습니다.");
			req.setAttribute("historyBack", true);
			return "common/redirect";
		}

		req.setAttribute("alertMsg", String.format("로그인아이디는 %s 입니다.", member.getLoginId()));
		req.setAttribute("replaceUrl", "../member/login");
		return "common/redirect";
	}
	
	public String showFindLoginPw(HttpServletRequest req, HttpServletResponse resp) {
		HttpSession session = req.getSession();

		if (session.getAttribute("loginedMemberId") != null) {
			req.setAttribute("alertMsg", "로그아웃 후 진행해주세요.");
			req.setAttribute("historyBack", true);
			return "common/redirect";
		}

		return "usr/member/findLoginPw";
	}

	public String doFindLoginPw(HttpServletRequest req, HttpServletResponse resp) {
		HttpSession session = req.getSession();

		if (session.getAttribute("loginedMemberId") != null) {
			req.setAttribute("alertMsg", "로그아웃 후 진행해주세요.");
			req.setAttribute("historyBack", true);
			return "common/redirect";
		}

		String loginId = req.getParameter("loginId");
		String email = req.getParameter("email");

		Member member = memberService.getMemberLoginId(loginId);

		if (member == null) {
			req.setAttribute("alertMsg", "일치하는 회원이 존재하지 않습니다.");
			req.setAttribute("historyBack", true);
			return "common/redirect";
		}

		if (member.getEmail().equals(email) == false) {
			req.setAttribute("alertMsg", "회원이 이메일주소를 정확히 입력해주세요.");
			req.setAttribute("historyBack", true);
			return "common/redirect";
		}

		ResultData sendTempLoginPwToEmailRs = memberService.sendTempLoginPwToEmail(member);
		
		if ( sendTempLoginPwToEmailRs.isFail() ) {
			req.setAttribute("alertMsg", sendTempLoginPwToEmailRs.getMsg());
			req.setAttribute("historyBack", true);
			return "common/redirect";
		}

		req.setAttribute("alertMsg", sendTempLoginPwToEmailRs.getMsg());
		req.setAttribute("replaceUrl", "../member/login");
		return "common/redirect";
	}
	
	
}
