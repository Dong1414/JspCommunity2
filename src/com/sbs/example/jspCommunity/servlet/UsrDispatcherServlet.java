package com.sbs.example.jspCommunity.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sbs.example.jspCommunity.container.Container;
import com.sbs.example.jspCommunity.controller.UsrArticleController;
import com.sbs.example.jspCommunity.controller.UsrHomeController;
import com.sbs.example.jspCommunity.controller.UsrLikeController;
import com.sbs.example.jspCommunity.controller.UsrMemberController;
import com.sbs.example.jspCommunity.controller.UsrReplyController;

@WebServlet("/usr/*")
public class UsrDispatcherServlet extends DispatcherServlet {
	@Override
	protected String doAction(HttpServletRequest req, HttpServletResponse resp, String controllerName,
			String actionMethodName) {

		int loginedMemberId = -1;
		boolean isLogined = false;

		HttpSession session = req.getSession();

		String jspPath = null;
		if (session.getAttribute("loginedMemberId") != null) {
			loginedMemberId = (int) session.getAttribute("loginedMemberId");
			isLogined = true;
		}
		if (controllerName.equals("home")) {
			UsrHomeController homeController = Container.homeController;

			if (actionMethodName.equals("main")) {
				jspPath = homeController.showMain(req, resp);

			}
		} else if (controllerName.equals("member")) {
			UsrMemberController usrMemberController = Container.usrMemberController;
			if (actionMethodName.equals("join")) {
				jspPath = usrMemberController.join(req, resp);
			} else if (actionMethodName.equals("doJoin")) {
				jspPath = usrMemberController.doJoin(req, resp);
			} else if (actionMethodName.equals("login")) {
				jspPath = usrMemberController.login(req, resp);
			} else if (actionMethodName.equals("doLogin")) {
				jspPath = usrMemberController.doLogin(req, resp);
			} else if (actionMethodName.equals("doLogout")) {
				jspPath = usrMemberController.doLogout(req, resp);
			} else if (actionMethodName.equals("getLoginIdDup")) {
				jspPath = usrMemberController.getLoginIdDup(req, resp);
			} else if (actionMethodName.equals("findLoginId")) {
				jspPath = usrMemberController.showFindLoginId(req, resp);
			} else if (actionMethodName.equals("doFindLoginId")) {
				jspPath = usrMemberController.doFindLoginId(req, resp);
			} else if (actionMethodName.equals("findLoginPw")) {
				jspPath = usrMemberController.showFindLoginPw(req, resp);
			} else if (actionMethodName.equals("doFindLoginPw")) {
				jspPath = usrMemberController.doFindLoginPw(req, resp);
			} else if (actionMethodName.equals("modify")) {

				jspPath = usrMemberController.modify(req, resp);

			} else if (actionMethodName.equals("doModify")) {
				jspPath = usrMemberController.doModify(req, resp);
			}
		} else if (controllerName.equals("article")) {
			UsrArticleController usrArticleController = Container.usrArticleController;

			if (actionMethodName.equals("list")) {

				jspPath = usrArticleController.showList(req, resp);

			} else if (actionMethodName.equals("detail")) {
				jspPath = usrArticleController.showDetail(req, resp);
			} else if (actionMethodName.equals("modify")) {

				jspPath = usrArticleController.showModify(req, resp);

			} else if (actionMethodName.equals("doModify")) {
				jspPath = usrArticleController.doModify(req, resp);

			} else if (actionMethodName.equals("write")) {

				jspPath = usrArticleController.showWrite(req, resp);

			} else if (actionMethodName.equals("doWrite")) {
				jspPath = usrArticleController.doWrite(req, resp);
			} else if (actionMethodName.equals("doDelete")) {
				jspPath = usrArticleController.doDelete(req, resp, loginedMemberId);
			} else if (actionMethodName.equals("doCancelModifyReply")) {
				jspPath = usrArticleController.doCancelModifyReply(req, resp);
			}

		} else if (controllerName.equals("like")) {
			UsrLikeController likeController = Container.usrLikeController;

			if (actionMethodName.equals("doLike")) {
				jspPath = likeController.doLike(req, resp);
			} else if (actionMethodName.equals("doCancelLike")) {
				jspPath = likeController.doCancelLike(req, resp);
			} else if (actionMethodName.equals("doDislike")) {
				jspPath = likeController.doDislike(req, resp);
			} else if (actionMethodName.equals("doCancelDislike")) {
				jspPath = likeController.doCancelDislike(req, resp);
			}
		} else if (controllerName.equals("reply")) {
			UsrReplyController replyController = Container.usrReplyController;

			if (actionMethodName.equals("doWrite")) {
				jspPath = replyController.doWrite(req, resp);
			} else if (actionMethodName.equals("doDelete")) {
				jspPath = replyController.doDelete(req, resp);
			} else if (actionMethodName.equals("modify")) {
				jspPath = replyController.showModify(req, resp);
			} else if (actionMethodName.equals("doModify")) {
				jspPath = replyController.doModify(req, resp);
			} 		
		}
		return jspPath;
	}
}
