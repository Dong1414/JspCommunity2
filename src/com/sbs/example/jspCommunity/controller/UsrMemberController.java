package com.sbs.example.jspCommunity.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sbs.example.jspCommunity.container.Container;
import com.sbs.example.jspCommunity.dto.Member;
import com.sbs.example.jspCommunity.dto.ResultData;
import com.sbs.example.jspCommunity.service.AttrService;
import com.sbs.example.jspCommunity.service.EmailService;
import com.sbs.example.jspCommunity.service.MemberService;
import com.sbs.example.util.Util;

public class UsrMemberController extends Controller{
	private MemberService memberService;
	private AttrService attrService;
	
	public UsrMemberController() {
		memberService = Container.memberService;
		attrService = Container.attrService;
	}

	public String join(HttpServletRequest req, HttpServletResponse resp) {

		return "usr/member/join";
	}

	public String doJoin(HttpServletRequest req, HttpServletResponse resp) {
		String loginId = req.getParameter("loginId");
		
		if (Util.isEmpty(loginId)) {
			return msgAndBack(req, "로그인아이디를 입력해주세요.");
		}
		
		String loginPw = (String) req.getParameter("loginPwReal");
		
		if (Util.isEmpty(loginPw)) {
			return msgAndBack(req, "로그인비번을 입력해주세요.");
		}

		String cellphoneNo = (String) req.getParameter("cellphoneNo");
		
		if (Util.isEmpty(cellphoneNo)) {
			return msgAndBack(req, "휴대전화번호를 입력해주세요.");
		}
		
		String name = (String) req.getParameter("name");
		
		if (Util.isEmpty(name)) {
			return msgAndBack(req, "이름을 입력해주세요.");
		}
		String email = (String) req.getParameter("email");
		
		if (Util.isEmpty(email)) {
			return msgAndBack(req, "이메일을 입력해주세요.");
		}
		String nickname = (String) req.getParameter("nickname");

		if (Util.isEmpty(nickname)) {
			return msgAndBack(req, "별명을 입력해주세요.");
		}
		
		Member member = memberService.getMemberLoginId(loginId);

		if (member != null) {
			return msgAndBack(req, "해당 로그인 아이디는 이미 사용중입니다.");
		}

		Map<String, Object> joinArgs = new HashMap<>();
		joinArgs.put("loginId", loginId);
		joinArgs.put("loginPw", loginPw);
		joinArgs.put("cellphoneNo", cellphoneNo);
		joinArgs.put("name", name);
		joinArgs.put("email", email);
		joinArgs.put("nickname", nickname);

		int newArticleId = memberService.join(joinArgs);
		EmailService emailService = Container.emailService;
		emailService.send((String)joinArgs.get("email"), "JSP커뮤니티입니다. 회원가입을 축하드립니다", "감사합니다.");
		
		return msgAndReplace(req, newArticleId + "번 회원이 생성되었습니다.", "../home/main");
		
		
	}

	public String login(HttpServletRequest req, HttpServletResponse resp) {
		return "usr/member/login";
	}

	public String doLogin(HttpServletRequest req, HttpServletResponse resp) {
		HttpSession session = req.getSession();
		
		String loginId = req.getParameter("loginId");

		if (Util.isEmpty(loginId)) {
			return msgAndBack(req, "로그인아이디를 입력해주세요.");
		}

		String loginPw = (String) req.getParameter("loginPwReal");
		
		if (Util.isEmpty(loginPw)) {
			return msgAndBack(req, "로그인비밀번호를 입력해주세요.");
		}
		
		Member member = memberService.getMemberLoginId(loginId);

		if (member == null) {
			return msgAndBack(req, "일치하는 회원이 존재하지 않습니다.");
		}
	

		if (!member.getLoginPw().equals(loginPw)) {
			return msgAndBack(req, "비밀번호가 일치하지 않습니다.");
		}

		int loginedMemberId = member.getId();
		
		session.setAttribute("loginedMemberId", loginedMemberId);
		session.setAttribute("loginedMemberNickname", member.getNickname());
		
		boolean isUsingTempPassword = memberService.getIsUsingTempPassword(member.getId());

		String alertMsg = String.format("%s님 환영합니다.", member.getNickname());
		String replaceUrl = "../home/main";

		if ( isUsingTempPassword ) {
			alertMsg = String.format("%s님은 현재 임시 비밀번호를 사용중입니다. 변경 후 이용해주세요.", member.getNickname());
			replaceUrl = "../member/modify";
		}	
		boolean date = memberService.dateCompareTo(member);

		if (date==true) {
			alertMsg = "비밀번호를 사용한지 90일이 지났습니다. 빠르게 변경해주세요.";			
			replaceUrl = "../member/modify";
		}
		return msgAndReplace(req, alertMsg, replaceUrl);
	}

	public String doLogout(HttpServletRequest req, HttpServletResponse resp) {
		HttpSession session = req.getSession();
		session.removeAttribute("loginedMemberId");
		return msgAndReplace(req, "로그아웃 되었습니다.", "../home/main");
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

		return json(req, new ResultData(resultCode, msg, "loginId", loginId));
	}
	public String showFindLoginId(HttpServletRequest req, HttpServletResponse resp) {
		return "usr/member/findLoginId";
	}

	public String doFindLoginId(HttpServletRequest req, HttpServletResponse resp) {
		String name = req.getParameter("name");
		if (Util.isEmpty(name)) {
			return msgAndBack(req, "이름을 입력해주세요.");
		}
	
		String email = req.getParameter("email");
		if (Util.isEmpty(email)) {
			return msgAndBack(req, "이메일을 입력해주세요.");
		}
		
		Member member = memberService.getMemberByNameAndEmail(name, email);

		if (member == null) {
			return msgAndBack(req, "일치하는 회원이 존재하지 않습니다.");
		}

		return msgAndReplace(req, String.format("로그인아이디는 %s 입니다.", member.getLoginId()), "../member/login");
	}
	
	public String showFindLoginPw(HttpServletRequest req, HttpServletResponse resp) {
		
		return "usr/member/findLoginPw";
	}

	public String doFindLoginPw(HttpServletRequest req, HttpServletResponse resp) {
		String loginId = req.getParameter("loginId");
		if (Util.isEmpty(loginId)) {
			return msgAndBack(req, "로그인아이디를 입력해주세요.");
		}
		
		String email = req.getParameter("email");
		if (Util.isEmpty(email)) {
			return msgAndBack(req, "이메일을 입력해주세요.");
		}

		
		Member member = memberService.getMemberLoginId(loginId);

		if (member == null) {
			return msgAndBack(req, "일치하는 회원이 존재하지 않습니다.");
		}

		if (member.getEmail().equals(email) == false) {
			return msgAndBack(req, "회원이 이메일주소를 정확히 입력해주세요.");
		}

		ResultData sendTempLoginPwToEmailRs = memberService.sendTempLoginPwToEmail(member);
		
		if ( sendTempLoginPwToEmailRs.isFail() ) {
			return msgAndBack(req, sendTempLoginPwToEmailRs.getMsg());
		}

		return msgAndReplace(req, sendTempLoginPwToEmailRs.getMsg(), "../member/login");
	}

	public String modify(HttpServletRequest req, HttpServletResponse resp) {

		return "usr/member/modify";
	}

	public String doModify(HttpServletRequest req, HttpServletResponse resp) {
		
		int loginedMemberId = (int) req.getAttribute("loginedMemberId");

		String loginPw = req.getParameter("loginPwReal");

		if (loginPw != null && loginPw.length() == 0) {
			loginPw = null;
		}

		String name = req.getParameter("name");

		if (Util.isEmpty(name)) {
			return msgAndBack(req, "이름을 입력해주세요.");
		}

		String nickname = req.getParameter("nickname");

		if (Util.isEmpty(nickname)) {
			return msgAndBack(req, "별명을 입력해주세요.");
		}

		String email = req.getParameter("email");

		if (Util.isEmpty(email)) {
			return msgAndBack(req, "이메일을 입력해주세요.");
		}

		String cellphoneNo = req.getParameter("cellphoneNo");

		if (Util.isEmpty(cellphoneNo)) {
			return msgAndBack(req, "휴대전화번호를 입력해주세요.");
		}

		Map<String, Object> modifyParam = new HashMap<>();
		modifyParam.put("loginPw", loginPw);
		modifyParam.put("name", name);
		modifyParam.put("nickname", nickname);
		modifyParam.put("email", email);
		modifyParam.put("cellphoneNo", cellphoneNo);
		modifyParam.put("id", loginedMemberId);

		memberService.modify(modifyParam);

		if (loginPw != null) {
			memberService.setIsUsingTempPassword(loginedMemberId, false);
		}

		return msgAndReplace(req, "회원정보가 수정되었습니다.", "../home/main");
		
	}
	
	
}
