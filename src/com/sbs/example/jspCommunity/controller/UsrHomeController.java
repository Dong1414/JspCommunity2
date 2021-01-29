package com.sbs.example.jspCommunity.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.taglibs.standard.lang.jstl.parser.ParseException;

import com.sbs.example.jspCommunity.container.Container;
import com.sbs.example.jspCommunity.dto.Member;
import com.sbs.example.jspCommunity.service.MemberService;

public class UsrHomeController {
	public String showMain(HttpServletRequest req, HttpServletResponse resp) {
		MemberService memberService = Container.memberService;

		Member member = memberService.getMemberById(1);

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		try {
			Date date = df.parse(member.getUpdateDate());
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);

		} catch (ParseException e) {
			e.printStackTrace();
		}

		req.setAttribute("data", member.getId());

		return "common/pure";
	}
}
