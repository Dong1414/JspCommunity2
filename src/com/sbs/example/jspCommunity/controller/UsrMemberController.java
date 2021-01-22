package com.sbs.example.jspCommunity.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sbs.example.jspCommunity.container.Container;
import com.sbs.example.jspCommunity.dto.Member;
import com.sbs.example.jspCommunity.service.EmailService;
import com.sbs.example.jspCommunity.service.MemberService;
import com.sbs.example.util.Util;

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
		
		EmailService emailService = Container.emailService;
		emailService.send((String)joinArgs.get("email"), "JSP커뮤니티입니다. 회원가입을 축하드립니다", "감사합니다.");
		
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

		Map<String, Object> rs = new HashMap<>();

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

		rs.put("resultCode", resultCode);
		rs.put("msg", msg);
		rs.put("loginId", loginId);

		req.setAttribute("data", Util.getJsonText(rs));
		return "common/pure";
	}
}
