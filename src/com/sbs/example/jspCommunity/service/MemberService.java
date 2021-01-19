package com.sbs.example.jspCommunity.service;

import java.util.List;
import java.util.Map;

import com.sbs.example.jspCommunity.container.Container;
import com.sbs.example.jspCommunity.dao.MemberDao;
import com.sbs.example.jspCommunity.dto.Member;

public class MemberService {

	private MemberDao memberDao;
	
	public MemberService() {
		memberDao = Container.memberDao;
	}

	public List<Member> getForPrintMembers() {
		return memberDao.getForPrintMembers();
	}

	public int join(String loginId, String loginPw, int cellPhonNo, String name, String email, String nickName) {
		return memberDao.join(loginId,loginPw,cellPhonNo,name,email, nickName);
	}

	public Member getMemberLoginId(String loginId) {
		return memberDao.getMemberLoginId(loginId);
	}

	public void join(Map<String, Object> joinArgs) {
		memberDao.join(joinArgs);
		
	}



}
