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
		
		return "usr/member/join";
	}

	public String doJoin(HttpServletRequest req, HttpServletResponse resp) {

		String loginId = (String) req.getParameter("loginId");
		String loginPw = (String) req.getParameter("loginPw");
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
		return "common/redirect";
	}

	public String login(HttpServletRequest req, HttpServletResponse resp) {
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

		String data = "";

		if ( member != null ) {
			data = "NO";
		}
		else {
			data = "YES";
		}

		req.setAttribute("data", data);
		return "common/pure";
	}
}
