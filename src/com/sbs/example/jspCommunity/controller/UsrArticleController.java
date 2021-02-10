package com.sbs.example.jspCommunity.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sbs.example.jspCommunity.container.Container;
import com.sbs.example.jspCommunity.dto.Article;
import com.sbs.example.jspCommunity.dto.Board;
import com.sbs.example.jspCommunity.dto.Member;
import com.sbs.example.jspCommunity.dto.Reply;
import com.sbs.example.jspCommunity.service.ArticleService;
import com.sbs.example.jspCommunity.service.LikeService;
import com.sbs.example.jspCommunity.service.MemberService;
import com.sbs.example.jspCommunity.service.ReplyService;
import com.sbs.example.util.Util;

public class UsrArticleController extends Controller {
	private ArticleService articleService;
	private ReplyService replyService;
	private MemberService memberService;
	private LikeService likeService;

	public UsrArticleController() {
		articleService = Container.articleService;
		replyService = Container.replyService;
		memberService = Container.memberService;
		likeService = Container.likeService;
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
		List<Article> articles = articleService.getForPrintArticlesByBoardId(boardId, limitStart, itemsInAPage,
				searchKeywordType, searchKeyword);

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

		Cookie viewCookie=null;
		Cookie[] cookies=req.getCookies();
		System.out.println("cookie : "+cookies);
		
		if(cookies !=null) {

			for (int i = 0; i < cookies.length; i++) {
				//System.out.println("쿠키 이름 : "+cookies[i].getName());
               
               //만들어진 쿠키들을 확인하며, 만약 들어온 적 있다면 생성되었을 쿠키가 있는지 확인
				if(cookies[i].getName().equals("|"+id+"|")) {
					System.out.println("if문 쿠키 이름 : "+cookies[i].getName());
				
               //찾은 쿠키를 변수에 저장
					viewCookie=cookies[i];
				}
			}			
			
		}else {
			System.out.println("cookies 확인 로직 : 쿠키가 없습니다.");
		}
		
		//만들어진 쿠키가 없음을 확인
		if(viewCookie==null) {
	        
         	System.out.println("viewCookie 확인 로직 : 쿠키 없당");
			
           try {
           
           	//이 페이지에 왔다는 증거용(?) 쿠키 생성
				Cookie newCookie=new Cookie("|"+id+"|","readCount");
				newCookie.setMaxAge(60*60*24);	
				resp.addCookie(newCookie);
               
               //쿠키가 없으니 증가 로직 진행
				articleService.updateHit(id);
               
			} catch (Exception e) {
				System.out.println("쿠키 넣을때 오류 나나? : "+e.getMessage());
				e.getStackTrace();

			}
		
       //만들어진 쿠키가 있으면 증가로직 진행하지 않음
		}else {
			System.out.println("viewCookie 확인 로직 : 쿠키 있당");
			String value=viewCookie.getValue();
			System.out.println("viewCookie 확인 로직 : 쿠키 value : "+value);
		}
		
		Member loginedMember = (Member) req.getAttribute("loginedMember");
		Article article = articleService.getForPrintArticleById(id, loginedMember);

		if (article == null) {
			return msgAndBack(req, id + "번 게시물은 존재하지 않습니다.");
		}
		
		List<Reply> replies = replyService.getForPrintReplies("article", article.getId());
		req.setAttribute("replies", replies);
		
		req.setAttribute("article", article);

		HttpSession session = req.getSession();

		session.setAttribute("isArticleId", id);

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

	public String doComment(HttpServletRequest req, HttpServletResponse resp) {
		String relTypeCode = req.getParameter("relTypeCode");
		HttpSession session = req.getSession();
		int memberId = (int) session.getAttribute("loginedMemberId");
		int articleId = (int) session.getAttribute("isArticleId");
		String body = req.getParameter("body");
		articleService.addReple(memberId, articleId, body);

		if (relTypeCode == null) {
			return msgAndBack(req, "관련데이터코드를 입력해주세요.");
		}

		int relId = Util.getAsInt(req.getParameter("relId"), 0);

		if (relId == 0) {
			return msgAndBack(req, "관련데이터번호를 입력해주세요.");
		}

		int actorId = (int) req.getAttribute("loginedMemberId");

		return msgAndReplace(req, "댓글이 작성되었습니다.", req.getParameter("redirectUrl"));
	}

	public String showReplyModify(HttpServletRequest req, HttpServletResponse resp) {
		HttpSession session = req.getSession();

		int modifyMode = Util.getAsInt(req.getParameter("id"), 0);
		session.setAttribute("modifyMode", modifyMode);

		String relTypeCode = req.getParameter("relTypeCode");
		if (relTypeCode == null) {
			return msgAndBack(req, "관련데이터코드를 입력해주세요.");
		}

		int relId = Util.getAsInt(req.getParameter("relId"), 0);

		if (relId == 0) {
			return msgAndBack(req, "관련데이터번호를 입력해주세요.");
		}

		return msgAndReplace(req, "댓글을 수정하세요.", req.getParameter("redirectUrl"));
	}

	public String doReplyModify(HttpServletRequest req, HttpServletResponse resp) {
		HttpSession session = req.getSession();

		int replyId = Integer.parseInt(req.getParameter("replyId"));
		String body = req.getParameter("body");

		String relTypeCode = req.getParameter("relTypeCode");
		if (relTypeCode == null) {
			return msgAndBack(req, "관련데이터코드를 입력해주세요.");
		}

		int relId = Util.getAsInt(req.getParameter("relId"), 0);

		if (relId == 0) {
			return msgAndBack(req, "관련데이터번호를 입력해주세요.");
		}
		int modifyMode = 0;
		session.setAttribute("modifyMode", modifyMode);

		return msgAndReplace(req, "댓글이 수정되었습니다.", req.getParameter("redirectUrl"));
	}

	public String doCancelModifyReply(HttpServletRequest req, HttpServletResponse resp) {
		HttpSession session = req.getSession();

		String relTypeCode = req.getParameter("relTypeCode");
		if (relTypeCode == null) {
			return msgAndBack(req, "관련데이터코드를 입력해주세요.");
		}

		int relId = Util.getAsInt(req.getParameter("relId"), 0);

		if (relId == 0) {
			return msgAndBack(req, "관련데이터번호를 입력해주세요.");
		}
		int modifyMode = 0;
		session.setAttribute("modifyMode", modifyMode);

		return msgAndReplace(req, "댓글 수정이 취소되었습니다.", req.getParameter("redirectUrl"));
	}

	public String doReplyDelete(HttpServletRequest req, HttpServletResponse resp) {

		HttpSession session = req.getSession();
		int memberId = (int) session.getAttribute("loginedMemberId");
		int replyId = Util.getAsInt(req.getParameter("id"), 0);

		Reply reply = articleService.getReply(replyId);

		if (reply != null) {
			articleService.deleteReple(replyId);
		}
		String relTypeCode = req.getParameter("relTypeCode");
		if (relTypeCode == null) {
			return msgAndBack(req, "관련데이터코드를 입력해주세요.");
		}

		int relId = Util.getAsInt(req.getParameter("relId"), 0);

		if (relId == 0) {
			return msgAndBack(req, "관련데이터번호를 입력해주세요.");
		}

		int actorId = (int) req.getAttribute("loginedMemberId");

		return msgAndReplace(req, "댓글이 삭제되었습니다.", req.getParameter("redirectUrl"));
	}

}
