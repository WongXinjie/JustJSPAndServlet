package com.bookstores.servlet;
import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;


@WebServlet(name="LogoutServlet", urlPatterns="/logout")
public class LogoutServlet extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException{
		response.setContentType("text/html;charset=utf-8");
		HttpSession session = request.getSession();
		if(session.getAttribute("loginUser") != null){
			session.removeAttribute("loginUser");
		}
		if(session.getAttribute("loginManager") != null){
			session.removeAttribute("loginManager");
		}
		response.sendRedirect(request.getContextPath()+"/index.jsp");
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException{
		doGet(request, response);
	}

}
