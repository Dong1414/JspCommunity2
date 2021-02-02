package com.sbs.example.jspCommunity.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sbs.example.jspCommunity.container.Container;
import com.sbs.example.jspCommunity.dao.ArticleDao;
import com.sbs.example.jspCommunity.dto.Article;
import com.sbs.example.jspCommunity.dto.Board;
import com.sbs.example.jspCommunity.service.ArticleService;
import com.sbs.example.util.Util;

public class UsrArticleController extends Controller{
	private ArticleService articleService;

	public UsrArticleController() {
		articleService = Container.articleService;
	}

	public String showList(HttpServletRequest req, HttpServletResponse resp) {
		
		String searchKeyword = req.getParameter("searchKeyword");
		String searchKeywordType = req.getParameter("searchKeywordType");

		int itemsInAPage = 15;
		int page = Util.getAsInt(req.getParameter("page"), 1);
		int limitStart = (page - 1) * itemsInAPage;				
		
		int boardId = Integer.parseInt(req.getParameter("boardId"));

		Board board = articleService.getBoardById(boardId);
		req.setAttribute("board", board);

		int totalCount = articleService.getArticlesCountByBoardId(boardId, searchKeywordType, searchKeyword);
		List<Article> articles = articleService.getForPrintArticlesByBoardId(boardId, limitStart, itemsInAPage, searchKeywordType, searchKeyword);
		
		int totalPage = (int) Math.ceil(totalCount / (double) itemsInAPage);

		int pageBoxSize = 10;

		// 현재 페이지 박스 시작, 끝 계산

		int previousPageBoxesCount = (page - 1) / pageBoxSize;
		int pageBoxStartPage = pageBoxSize * previousPageBoxesCount + 1;
		int pageBoxEndPage = pageBoxStartPage + pageBoxSize - 1;

		if (pageBoxEndPage > totalPage) {
			pageBoxEndPage = totalPage;
		}

		// 이전버튼 페이지 계산
		int pageBoxStartBeforePage = pageBoxStartPage - 1;
		if (pageBoxStartBeforePage < 1) {
			pageBoxStartBeforePage = 1;
		}

		// 다음버튼 페이지 계산
		int pageBoxEndAfterPage = pageBoxEndPage + 1;

		if (pageBoxEndAfterPage > totalPage) {
			pageBoxEndAfterPage = totalPage;
		}

		// 이전버튼 노출여부 계산
		boolean pageBoxStartBeforeBtnNeedToShow = pageBoxStartBeforePage != pageBoxStartPage;
		// 다음버튼 노출여부 계산
		boolean pageBoxEndAfterBtnNeedToShow = pageBoxEndAfterPage != pageBoxEndPage;
		
		req.setAttribute("totalPage", totalPage);
		req.setAttribute("page", page);

		req.setAttribute("pageBoxStartBeforeBtnNeedToShow", pageBoxStartBeforeBtnNeedToShow);
		req.setAttribute("pageBoxEndAfterBtnNeedToShow", pageBoxEndAfterBtnNeedToShow);
		req.setAttribute("pageBoxStartBeforePage", pageBoxStartBeforePage);
		req.setAttribute("pageBoxEndAfterPage", pageBoxEndAfterPage);
		req.setAttribute("pageBoxStartPage", pageBoxStartPage);
		req.setAttribute("pageBoxEndPage", pageBoxEndPage);
		req.setAttribute("totalCount", totalCount);
		req.setAttribute("articles", articles);
		return "usr/article/list";
	}

	public String showDetail(HttpServletRequest req, HttpServletResponse resp) {
		int id = Util.getAsInt(req.getParameter("id"), 0);

		if (id == 0) {
			return msgAndBack(req, "게시물번호를 입력해주세요.");
		}

		Article article = articleService.getForPrintArticleById(id);
		
		if (article == null) {
			return msgAndBack(req, id + "번 게시물은 존재하지 않습니다.");
		}
		articleService.updateHit(id);
		req.setAttribute("article", article);

		int likeCount = articleService.getLikeCount(id);
		int hateCount = articleService.getHateCount(id);
		
		HttpSession session = req.getSession();
		session.setAttribute("isArticleId", id);
		session.setAttribute("likeCount", likeCount);
		session.setAttribute("hateCount", hateCount);
		return "usr/article/detail";
	}

	public String showWrite(HttpServletRequest req, HttpServletResponse resp) {
		int boardId = Util.getAsInt(req.getParameter("boardId"), 0);

		if (boardId == 0) {
			return msgAndBack(req, "게시판번호를 입력해주세요.");
		}
		
		Board board = articleService.getBoardById(boardId);
		if (board == null) {
			return msgAndBack(req, boardId + "번 게시판은 존재하지 않습니다.");
		}
		
		req.setAttribute("board", board);
		
		return "usr/article/write";
	}

	public String doWrite(HttpServletRequest req, HttpServletResponse resp) {

		int loginedMemberId = (int) req.getAttribute("loginedMemberId");
		int boardId = Util.getAsInt(req.getParameter("boardId"), 0);

		if (boardId == 0) {
			return msgAndBack(req, "게시판번호를 입력해주세요.");
		}

		Board board = articleService.getBoardById(boardId);

		if (board == null) {
			return msgAndBack(req, boardId + "번 게시판은 존재하지 않습니다.");
		}		
		
		String title = req.getParameter("title");
		
		if (Util.isEmpty(title)) {
			return msgAndBack(req, "제목을 입력해주세요.");
		}
		
		String body = req.getParameter("body");
		
		if (Util.isEmpty(body)) {
			return msgAndBack(req, "내용을 입력해주세요.");
		}
		
		Map<String, Object> writeArgs = new HashMap<>();
		writeArgs.put("memberId", loginedMemberId);
		writeArgs.put("boardId", boardId);
		writeArgs.put("title", title);
		writeArgs.put("body", body);

		int newArticleId = articleService.write(writeArgs);

		return msgAndReplace(req, newArticleId + "번 게시물이 생성되었습니다.", String.format("detail?id=%d", newArticleId));
	}

	public String doDelete(HttpServletRequest req, HttpServletResponse resp, int loginMemberId) {
		int id = Util.getAsInt(req.getParameter("id"), 0);

		if (id == 0) {
			return msgAndBack(req, "번호를 입력해주세요.");
		}

		Article article = articleService.getForPrintArticleById(id);

		if (article == null) {
			return msgAndBack(req, id + "번 게시물은 존재하지 않습니다.");
		}
		
		int loginedMemberId = (int) req.getAttribute("loginedMemberId");
		if (article.getMemberId() != loginedMemberId) {
			return msgAndBack(req, id + "번 게시물에 대한 권한이 없습니다.");
		}
		

		articleService.doDelete(id);
		int boardId = article.getBoardId();
		return msgAndReplace(req, id + "번 게시물이 삭제되었습니다.", String.format("list?boardId=%d", boardId));
	}

	public String showModify(HttpServletRequest req, HttpServletResponse resp) {
		int id = Util.getAsInt(req.getParameter("id"), 0);

		if (id == 0) {
			return msgAndBack(req, "번호를 입력해주세요.");
		}

		Article article = articleService.getForPrintArticleById(id);

		if (article == null) {
			return msgAndBack(req, id + "번 게시물은 존재하지 않습니다.");
		}

		int loginedMemberId = (int) req.getAttribute("loginedMemberId");

		if (article.getMemberId() != loginedMemberId) {
			return msgAndBack(req, id + "번 게시물에 대한 권한이 없습니다.");
		}
		
		Board board = articleService.getBoardById(article.getBoardId());

		req.setAttribute("article", article);
		req.setAttribute("board", board);

		return "usr/article/modify";
	}

	public String doModify(HttpServletRequest req, HttpServletResponse resp) {
		
		int id = Util.getAsInt(req.getParameter("id"), 0);

		if (id == 0) {
			return msgAndBack(req, "번호를 입력해주세요.");
		}

		Article article = articleService.getForPrintArticleById(id);

		if (article == null) {
			return msgAndBack(req, id + "번 게시물은 존재하지 않습니다.");
		}
		
		int loginedMemberId = (int) req.getAttribute("loginedMemberId");

		if (article.getMemberId() != loginedMemberId) {
			return msgAndBack(req, id + "번 게시물에 대한 권한이 없습니다.");
		}

		String title = req.getParameter("title");
		
		if (Util.isEmpty(title)) {
			return msgAndBack(req, "제목을 입력해주세요.");
		}
		
		String body = req.getParameter("body");
		
		if (Util.isEmpty(body)) {
			return msgAndBack(req, "내용을 입력해주세요.");
		}

		Map<String, Object> modifyArgs = new HashMap<>();
		modifyArgs.put("id", id);
		modifyArgs.put("title", title);
		modifyArgs.put("body", body);

		articleService.modify(modifyArgs);

		return msgAndReplace(req, id + "번 게시물이 수정되었습니다.", String.format("detail?id=%d", id));
	}

	public String doLike(HttpServletRequest req, HttpServletResponse resp) {
		HttpSession session = req.getSession();
		int memberId = (int) session.getAttribute("loginedMemberId");
		int articleId = (int) session.getAttribute("isArticleId");
		int i = articleService.likeUp(memberId,articleId);
		
		if(i == 0) {
			return msgAndReplace(req, "이미 좋아요를 누른 게시물 입니다.", String.format("detail?id=%d",articleId));
		}
		return "usr/article/detail";
	}

	public String doHate(HttpServletRequest req, HttpServletResponse resp) {
		HttpSession session = req.getSession();
		int memberId = (int) session.getAttribute("loginedMemberId");
		int articleId = (int) session.getAttribute("isArticleId");
		int i = articleService.hateUp(memberId,articleId);
		
		if(i == 0) {
			return msgAndReplace(req, "이미 싫어요를 누른 게시물 입니다.", String.format("detail?id=%d",articleId));
		}
		return "usr/article/detail";
	}

	public String doComment(HttpServletRequest req, HttpServletResponse resp) {
		HttpSession session = req.getSession();
		int memberId = (int) session.getAttribute("loginedMemberId");
		int articleId = (int) session.getAttribute("isArticleId");		
		String body = req.getParameter("body");
		articleService.addReple(memberId,articleId,body);	
		return "usr/article/detail";
	}
}
