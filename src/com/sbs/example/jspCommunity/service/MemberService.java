package com.sbs.example.jspCommunity.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sbs.example.jspCommunity.App;
import com.sbs.example.jspCommunity.container.Container;
import com.sbs.example.jspCommunity.dao.MemberDao;
import com.sbs.example.jspCommunity.dto.Member;
import com.sbs.example.jspCommunity.dto.ResultData;
import com.sbs.example.util.Util;

public class MemberService {

	private MemberDao memberDao;
	private EmailService emailService;
	private AttrService attrService;
	
	public MemberService() {
		memberDao = Container.memberDao;
		emailService = Container.emailService;
		attrService = Container.attrService;
	}

	public List<Member> getForPrintMembers() {
		return memberDao.getForPrintMembers();
	}

	public Member getMemberLoginId(String loginId) {
		return memberDao.getMemberLoginId(loginId);
	}

	public int join(Map<String, Object> joinArgs) {
		return memberDao.join(joinArgs);
		
	}

	public Member getMemberById(int id) {
		return memberDao.getMemberById(id);
	}

	public Member getMemberByNameAndEmail(String name, String email) {
		return memberDao.getMemberByNameAndEmail(name, email);
	}
	public ResultData  sendTempLoginPwToEmail(Member actor) {
		// 메일 제목과 내용 만들기
		String siteName = App.getSite();
		String siteLoginUrl = App.getLoginUrl();
		String title = "[" + siteName + "] 임시 패스워드 발송";
		String tempPassword = Util.getTempPassword(6);
		String body = "<h1>임시 패스워드 : " + tempPassword + "</h1>";
		body += "<a href=\"" + siteLoginUrl + "\" target=\"_blank\">로그인 하러가기</a>";

		Map<String, Object> rs = new HashMap<>();
		
		//메일전송
		int sendRs = emailService.send(actor.getEmail(), title, body);

		if (sendRs != 1) {
			return new ResultData("F-1", "메일발송에 실패하였습니다.");
		}
		setTempPassword(actor, tempPassword);

		String resultMsg = String.format("고객님의 새 임시 패스워드가 %s (으)로 발송되었습니다.", actor.getEmail());
		return new ResultData("S-1", resultMsg, "email", actor.getEmail());
	}

	private void setTempPassword(Member actor, String tempPassword) {
		Map<String, Object> modifyParam = new HashMap<>();
		modifyParam.put("id", actor.getId());
		modifyParam.put("loginPw", Util.sha256(tempPassword));
		modify(modifyParam);
	
		setIsUsingTempPassword(actor.getId(), true);
		//릴타입코드,ID,타입코드,타입2코드 순
	}
	
	public void setIsUsingTempPassword(int actorId, boolean use) {
		attrService.setValue("member__" + actorId + "__extra__isUsingTempPassword", use, null);
	}

	public boolean getIsUsingTempPassword(int actorId) {
		return attrService.getValueAsBoolean("member__" + actorId + "__extra__isUsingTempPassword");
	}

	public int modify(Map<String, Object> args) {
		 return memberDao.modify(args);
	}

	public boolean dateCompareTo(Member member) {
		String dump = member.getUpdateDate().substring(0, 10);
		String[] dumpSet = dump.split("-");
		String date = "";
		
		for(int i = 0 ; i < dumpSet.length ; i++) {
			date += dumpSet[i];
		}
		
		String endDate = "";
		try {
			endDate = AddDate(date, 0, 0, 90);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Date day1 = new Date();
		Date day2 = null;		
		try {
			day2 = dateFormat.parse(endDate);
		} catch (ParseException e) {
			
		}
		System.out.println(day1);
		System.out.println(day2);
		int compare = day1.compareTo(day2);
		if(compare > 0) {
			return true;
		}
		else {
			return false;
		}
	}
	private String AddDate(String strDate, int year, int month, int day) throws Exception {
		SimpleDateFormat dtFormat = new SimpleDateFormat("yyyyMMdd");
		Calendar cal = Calendar.getInstance();
		Date dt = dtFormat.parse(strDate);
		cal.setTime(dt);
		cal.add(Calendar.YEAR, year);
		cal.add(Calendar.MONTH, month);
		cal.add(Calendar.DATE, day);
		return dtFormat.format(cal.getTime());
	}
}
