package com.sbs.example.jspCommunity.controller.usr;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sbs.example.jspCommunity.container.Container;
import com.sbs.example.jspCommunity.dto.Article;
import com.sbs.example.jspCommunity.dto.Member;
import com.sbs.example.jspCommunity.service.MemberService;

public class MemberController {
	private MemberService memberService;
	
	public MemberController() {
		memberService = Container.memberService;
	}
	
	public String showList(HttpServletRequest req, HttpServletResponse resp) {
		List<Member> members = memberService.getForPrintMembers();
		
		req.setAttribute("members", members);
		
		return "adm/member/list";
	}

	public String join(HttpServletRequest req, HttpServletResponse resp) {
		return "usr/member/join";
	}

	public String doJoin(HttpServletRequest req, HttpServletResponse resp) {
		String loginId = (String) req.getParameter("loginId");
		String loginPw = (String)req.getParameter("loginPw");
		String cellPhonNo = (String)req.getParameter("cellPhonNo");
		String name = (String) req.getParameter("name");
		String email = (String) req.getParameter("email");
		String nickName = (String) req.getParameter("nickName");
		
		
		Member member = memberService.getMemberLoginId(loginId);
		
		if(member != null) {
			req.setAttribute("alertMsg", loginId + "는 이미 존재하는 아이디 입니다.");
			req.setAttribute("historyBack", true);
			return "common/redirect";
		}

		Map<String, Object> joinArgs = new HashMap<>();
		joinArgs.put("loginId", loginId);
		joinArgs.put("loginPw", loginPw);
		joinArgs.put("name", name);
		joinArgs.put("email", email);
		joinArgs.put("nickName", nickName);

		memberService.join(joinArgs);

		req.setAttribute("alertMsg", nickName + "님이 회원이 가입되었습니다.");
		req.setAttribute("replaceUrl", String.format("list?boardId=1"));
		return "common/redirect";
	}

	public String login(HttpServletRequest req, HttpServletResponse resp) {		
		return "usr/member/login";
	}

	public String doLogin(HttpServletRequest req, HttpServletResponse resp) {
		String loginId = (String) req.getParameter("loginId");
		String loginPw = (String) req.getParameter("loginPw");
		
		Member member = memberService.getMemberLoginId(loginId);
		
		if(member == null) {
			req.setAttribute("alertMsg", loginId + "은 존재하지 않는 아이디 입니다.");
			req.setAttribute("historyBack", true);
			return "common/redirect";
		}
		
		if(!member.getLoginPw().equals(loginPw)) {
			req.setAttribute("alertMsg", "비밀번호가 일치하지 않습니다.");
			req.setAttribute("historyBack", true);
			return "common/redirect";	
		}
		
		Container.sesstion.setloginMemberId(member.getId());
		req.setAttribute("alertMsg", member.getNickname() + "님이 로그인하였습니다.");
		req.setAttribute("replaceUrl", String.format("list?boardId=1"));
		return "common/redirect";		
	}
}
