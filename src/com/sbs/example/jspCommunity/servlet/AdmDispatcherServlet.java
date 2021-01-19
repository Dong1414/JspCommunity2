package com.sbs.example.jspCommunity.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sbs.example.jspCommunity.container.Container;
import com.sbs.example.jspCommunity.controller.AdmMemberController;
import com.sbs.example.mysqlutil.MysqlUtil;

@WebServlet("/adm/*")
public class AdmDispatcherServlet extends DispatcherServlet {
	@Override
	protected String doAction(HttpServletRequest req, HttpServletResponse resp, String controllerName, String actionMethodName) {
		String jspPath = null;

		if (controllerName.equals("member")) {
			AdmMemberController admMembrController = Container.admMembrController;

			if (actionMethodName.equals("list")) {
				jspPath = admMembrController.showList(req, resp);					
			}
		}
		return jspPath;
	}
}
