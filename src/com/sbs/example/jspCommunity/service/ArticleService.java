package com.sbs.example.jspCommunity.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.sbs.example.jspCommunity.container.Container;
import com.sbs.example.jspCommunity.dao.ArticleDao;
import com.sbs.example.jspCommunity.dao.ReplyDao;
import com.sbs.example.jspCommunity.dto.Article;
import com.sbs.example.jspCommunity.dto.Board;
import com.sbs.example.jspCommunity.dto.Member;
import com.sbs.example.jspCommunity.dto.Reply;

public class ArticleService {
	private ArticleDao articleDao;
	private LikeService likeService;
	private ReplyDao replyDao;
	
	public ArticleService() {
		likeService = Container.likeService;
		articleDao = Container.articleDao;
		replyDao = Container.replyDao;
	}

	public Article getForPrintArticleById(int id) {
		return getForPrintArticleById(id, null);
	}
	
	public Article getForPrintArticleById(int id, Member actor) {
		Article article = articleDao.getForPrintArticleById(id);
	
		
		if (article == null) {
			return null;
		}

		if(actor != null) {
			updateInfoForPrint(article, actor);
		}
			return article;
	}
		
	private void updateInfoForPrint(Article article, Member actor) {
		System.out.println("기존 업데이트 실행");
		boolean actorCanLike = likeService.actorCanLike("article",article.getId(), actor);
		boolean actorCanCancelLike = likeService.actorCanCancelLike("article",article.getId(), actor);
		boolean actorCanDislike = likeService.actorCanDislike("article",article.getId(), actor);
		boolean actorCanCancelDislike = likeService.actorCanCancelDislike("article",article.getId(), actor);
		article.getExtra().put("actorCanLike", actorCanLike);
		article.getExtra().put("actorCanCancelLike", actorCanCancelLike);
		article.getExtra().put("actorCanDislike", actorCanDislike);
		article.getExtra().put("actorCanCancelDislike", actorCanCancelDislike);		
	}
	
	

	public Board getBoardById(int id) {
		return articleDao.getBoardById(id);
	}

	public int write(Map<String, Object> args) {
		return articleDao.write(args);
	}

	public int doDelete(int id) {
		return articleDao.doDelete(id);
	}

	public int modify(Map<String, Object> args) {
		return articleDao.modify(args);
	}

	public int getArticlesCountByBoardId(int boardId, String searchKeywordType, String searchKeyword) {
		return articleDao.getArticlesCountByBoardId(boardId, searchKeywordType, searchKeyword);
	}
	
	public List<Article> getForPrintArticlesByBoardId(int boardId, int limitStart, int limitCount, String searchKeywordType, String searchKeyword) {
		return articleDao.getForPrintArticlesByBoardId(boardId, limitStart, limitCount, searchKeywordType, searchKeyword);
	}

	public void updateHit(int id) {
		articleDao.updateHit(id);
	}

	public int getLikeCount(int articleId) {
		return articleDao.getLikeCount(articleId);
		
	}

	public int likeUp(int memberId, int articleId) {
	
		int i = articleDao.likeUp(memberId, articleId);
		if(i == 0) {
			articleDao.likeDown(memberId,articleId); 
		}
		return 1;		
	}

	public int hateUp(int memberId, int articleId) {
		int i = articleDao.hateUp(memberId, articleId);
		if(i == 0) {
			articleDao.hateDown(memberId,articleId);  
		}
		return 1;
		
	}

	public int getHateCount(int id) {
		return articleDao.getHateCount(id);
	}

	public boolean likeCheck(int articleId, int memberId) {
		return articleDao.likeCheck(articleId,memberId);
		
	}
	public boolean hateCheck(int articleId, int memberId) {		
		return articleDao.hateCheck(articleId,memberId);
	}

	
	public Article getArticleById(int id) {
		return articleDao.getArticleById(id);
	}
}
