package com.sbs.example.jspCommunity.container;

import com.sbs.example.jspCommunity.controller.usr.ArticleController;
import com.sbs.example.jspCommunity.controller.usr.MemberController;
import com.sbs.example.jspCommunity.dao.ArticleDao;
import com.sbs.example.jspCommunity.dao.MemberDao;
import com.sbs.example.jspCommunity.service.ArticleService;
import com.sbs.example.jspCommunity.service.MemberService;
import com.sbs.example.jspCommunity.sesstion.Sesstion;

public class Container {
	
	public static Sesstion sesstion;
	
	public static ArticleDao articleDao;
	public static MemberDao memberDao;
		
	public static ArticleService articleService;
	public static MemberService memberService;
	
	public static ArticleController articleController;
	public static MemberController memberController;
	
	
	
	static {
		
		sesstion = new Sesstion();
		
		memberDao = new MemberDao();
		articleDao = new ArticleDao();
		
		memberService = new MemberService();
		articleService = new ArticleService();
		
		memberController = new MemberController();
		articleController = new ArticleController();
	}
}
