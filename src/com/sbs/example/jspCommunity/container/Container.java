package com.sbs.example.jspCommunity.container;

import com.sbs.example.jspCommunity.controller.AdmMemberController;
import com.sbs.example.jspCommunity.controller.UsrArticleController;
import com.sbs.example.jspCommunity.controller.UsrMemberController;
import com.sbs.example.jspCommunity.dao.ArticleDao;
import com.sbs.example.jspCommunity.dao.MemberDao;
import com.sbs.example.jspCommunity.service.ArticleService;
import com.sbs.example.jspCommunity.service.MemberService;

public class Container {	
	
	public static ArticleDao articleDao;
	public static MemberDao memberDao;
		
	public static ArticleService articleService;
	public static MemberService memberService;
	
	public static UsrArticleController usrArticleController;
	public static AdmMemberController admMembrController;
	public static UsrMemberController usrMemberController;

	
	
	
	
	static {
		
		memberDao = new MemberDao();
		articleDao = new ArticleDao();
		
		memberService = new MemberService();
		articleService = new ArticleService();
		
		admMembrController = new AdmMemberController();
		usrMemberController = new UsrMemberController();
		usrArticleController = new UsrArticleController();
	}
}
