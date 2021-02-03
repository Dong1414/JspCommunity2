package com.sbs.example.jspCommunity.service;

import java.util.List;
import java.util.Map;

import com.sbs.example.jspCommunity.container.Container;
import com.sbs.example.jspCommunity.dao.ArticleDao;
import com.sbs.example.jspCommunity.dto.Article;
import com.sbs.example.jspCommunity.dto.Board;
import com.sbs.example.jspCommunity.dto.Reply;

public class ArticleService {
	private ArticleDao articleDao;

	public ArticleService() {
		articleDao = Container.articleDao;
	}

	public Article getForPrintArticleById(int id) {
		return articleDao.getForPrintArticleById(id);
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

	public void addReple(int memberId, int articleId, String body) {
		articleDao.addReple(memberId, articleId, body);
		
	}

	public boolean likeCheck(int articleId, int memberId) {
		return articleDao.likeCheck(articleId,memberId);
		
	}
	public boolean hateCheck(int articleId, int memberId) {		
		return articleDao.hateCheck(articleId,memberId);
	}

	public void deleteReple(int replyId) {
		articleDao.deleteReple(replyId);
		
	}

	public Reply getReply(int replyId) {
		return articleDao.getReply(replyId);
	}
}
