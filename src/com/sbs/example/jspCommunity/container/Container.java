package com.sbs.example.jspCommunity.container;

import com.sbs.example.jspCommunity.controller.AdmMemberController;
import com.sbs.example.jspCommunity.controller.UsrArticleController;
import com.sbs.example.jspCommunity.controller.UsrHomeController;
import com.sbs.example.jspCommunity.controller.UsrLikeController;
import com.sbs.example.jspCommunity.controller.UsrMemberController;
import com.sbs.example.jspCommunity.controller.UsrReplyController;
import com.sbs.example.jspCommunity.dao.ArticleDao;
import com.sbs.example.jspCommunity.dao.AttrDao;
import com.sbs.example.jspCommunity.dao.LikeDao;
import com.sbs.example.jspCommunity.dao.MemberDao;
import com.sbs.example.jspCommunity.dao.ReplyDao;
import com.sbs.example.jspCommunity.service.ArticleService;
import com.sbs.example.jspCommunity.service.AttrService;
import com.sbs.example.jspCommunity.service.EmailService;
import com.sbs.example.jspCommunity.service.LikeService;
import com.sbs.example.jspCommunity.service.MemberService;
import com.sbs.example.jspCommunity.service.ReplyService;

public class Container {	

	public static ReplyService replyService;
	public static ReplyDao replyDao;
	public static UsrReplyController usrReplyController;
	
	
	public static ArticleDao articleDao;
	public static ArticleService articleService;	
	public static UsrArticleController usrArticleController;
	
	public static MemberDao memberDao;
	public static MemberService memberService;	
	public static UsrMemberController usrMemberController;
	
	public static AdmMemberController admMembrController;
	
	
	public static LikeDao likeDao;
	public static LikeService likeService;
	public static UsrLikeController usrLikeController;	
	
	public static AttrDao attrDao;
	public static AttrService attrService;
	
	public static UsrHomeController homeController;			
	
	public static EmailService emailService;

	static {
		
		
		
		attrDao = new AttrDao();
		likeDao = new LikeDao();
		memberDao = new MemberDao();
		articleDao = new ArticleDao();
		replyDao = new ReplyDao();
		
		attrService = new AttrService();
		likeService = new LikeService();
		emailService = new EmailService();
		memberService = new MemberService();
		articleService = new ArticleService();
		replyService = new ReplyService();
		
		usrLikeController = new UsrLikeController();
		admMembrController = new AdmMemberController();
		usrMemberController = new UsrMemberController();
		usrArticleController = new UsrArticleController();
		homeController = new UsrHomeController();
		usrReplyController = new UsrReplyController();
	}
}
