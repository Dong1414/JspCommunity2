package com.sbs.example.jspCommunity.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sbs.example.jspCommunity.container.Container;
import com.sbs.example.jspCommunity.dto.Member;
import com.sbs.example.jspCommunity.service.MemberService;

public class UsrMemberController {
	private MemberService memberService;

	public UsrMemberController() {
		memberService = Container.memberService;
	}

	public String join(HttpServletRequest req, HttpServletResponse resp) {

		HttpSession session = req.getSession();

		if (session.getAttribute("loginedMemberId") != null) {
			req.setAttribute("alertMsg", "로그아웃 후 진행해 주십시오.");
			req.setAttribute("historyBack", true);
			return "common/redirect";
		}

		return "usr/member/join";
	}

	public String doJoin(HttpServletRequest req, HttpServletResponse resp) {

		HttpSession session = req.getSession();

		if (session.getAttribute("loginedMemberId") != null) {
			req.setAttribute("alertMsg", "로그아웃 후 진행해 주십시오.");
			req.setAttribute("historyBack", true);
			return "common/redirect";
		}
		String loginId = (String) req.getParameter("loginId");
		String loginPw = (String) req.getParameter("loginPw");
		String cellPhonNo = (String) req.getParameter("cellPhonNo");
		String name = (String) req.getParameter("name");
		String email = (String) req.getParameter("email");
		String nickName = (String) req.getParameter("nickName");

		Member member = memberService.getMemberLoginId(loginId);

		if (member != null) {
			req.setAttribute("alertMsg", loginId + "는 이미 존재하는 아이디 입니다.");
			req.setAttribute("historyBack", true);
			return "common/redirect";
		}

		Map<String, Object> joinArgs = new HashMap<>();
		joinArgs.put("loginId", loginId);
		joinArgs.put("loginPw", loginPw);
		joinArgs.put("cellPhonNo", cellPhonNo);
		joinArgs.put("name", name);
		joinArgs.put("email", email);
		joinArgs.put("nickName", nickName);

		memberService.join(joinArgs);

		req.setAttribute("alertMsg", nickName + "님이 회원이 가입되었습니다.");
		req.setAttribute("replaceUrl", String.format("../home/main"));
		return "common/redirect";
	}

	public String login(HttpServletRequest req, HttpServletResponse resp) {
		HttpSession session = req.getSession();
		if (session.getAttribute("loginedMemberId") != null) {
			req.setAttribute("alertMsg", "로그아웃 후 진행해주세요.");
			req.setAttribute("historyBack", true);
			return "common/redirect";
		}

		return "usr/member/login";
	}

	public String doLogin(HttpServletRequest req, HttpServletResponse resp) {
		String loginId = (String) req.getParameter("loginId");
		String loginPw = (String) req.getParameter("loginPw");

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

		req.setAttribute("alertMsg", member.getNickname() + "님이 로그인하였습니다.");
		req.setAttribute("replaceUrl", String.format("../home/main"));
		return "common/redirect";
	}

	public String doLogout(HttpServletRequest req, HttpServletResponse resp) {
		HttpSession session = req.getSession();

		if (session.getAttribute("loginedMemberId") == null) {
			req.setAttribute("alertMsg", "이미 로그아웃 상태입니다.");
			req.setAttribute("historyBack", true);
			return "common/redirect";
		}
		session.removeAttribute("loginedMemberId");

		req.setAttribute("alertMsg", "로그아웃 되었습니다.");
		req.setAttribute("replaceUrl", "../home/main");
		return "common/redirect";
	}
}
