package com.sbs.example.jspCommunity.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sbs.example.jspCommunity.container.Container;
import com.sbs.example.jspCommunity.controller.UsrArticleController;
import com.sbs.example.jspCommunity.controller.UsrMemberController;
import com.sbs.example.mysqlutil.MysqlUtil;

@WebServlet("/usr/*")
public class UsrDispatcherServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html; charset=UTF-8");

		String requestUri = req.getRequestURI();
		String[] requestUriBits = requestUri.split("/");

		if (requestUriBits.length < 5) {
			resp.getWriter().append("올바른 요청이 아닙니다.");
			return;
		}

		String controllerName = requestUriBits[3];
		String actionMethodName = requestUriBits[4];

		MysqlUtil.setDBInfo("127.0.0.1", "sbsst", "sbs123414", "jspCommunity");

		String jspPath = null;

		if (controllerName.equals("member")) {
			UsrMemberController usrMemberController = Container.usrMemberController;
			if (actionMethodName.equals("join")) {
				jspPath = usrMemberController.join(req, resp);
			} else if (actionMethodName.equals("doJoin")) {
				jspPath = usrMemberController.doJoin(req, resp);
			} else if (actionMethodName.equals("login")) {
				jspPath = usrMemberController.login(req, resp);
			} else if (actionMethodName.equals("doLogin")) {
				jspPath = usrMemberController.doLogin(req, resp);
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
				jspPath = usrArticleController.doDelete(req, resp);
			}
		}

		MysqlUtil.closeConnection();

		RequestDispatcher rd = req.getRequestDispatcher("/jsp/" + jspPath + ".jsp");
		rd.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
