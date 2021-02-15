package com.sbs.example.jspCommunity.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sbs.example.jspCommunity.container.Container;
import com.sbs.example.jspCommunity.dto.Article;
import com.sbs.example.jspCommunity.service.ArticleService;

public class UsrHomeController extends Controller{
	private ArticleService articleService;
	
	public UsrHomeController() {
		articleService = Container.articleService;
	}
	public String showMain(HttpServletRequest req, HttpServletResponse resp) {
		
		List<Article> notice = articleService.getBoardByArticles(1);
		List<Article> free = articleService.getBoardByArticles(2);
		List<Article> cook = articleService.getBoardByArticles(3);
		List<Article> bug = articleService.getBoardByArticles(4);
		List<Article> single = articleService.getBoardByArticles(5);
		List<Article> tip = articleService.getBoardByArticles(6);
		List<Article> question = articleService.getBoardByArticles(7);
		
		List<Article> hotArticle = articleService.getArticles();
		
		MiniComparator comp = new MiniComparator();
		Collections.sort(hotArticle,comp);
		
		req.setAttribute("hotArticle", hotArticle);
		req.setAttribute("notice", notice);
		req.setAttribute("free", free);
		req.setAttribute("cook", cook);
		req.setAttribute("bug", bug);
		req.setAttribute("single", single);
		req.setAttribute("tip", tip);
		req.setAttribute("question", question);
		
		return "usr/home/main";
	}


}
