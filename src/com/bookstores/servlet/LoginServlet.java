package com.bookstores.servlet;
import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import com.bookstores.exception.*;
import com.bookstores.domain.*;
import com.bookstores.service.*;

@WebServlet(name="LoginServlet", urlPatterns="/login")
public class LoginServlet  extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException{
		response.setContentType("text/html;charset=utf-8");
		String role = request.getParameter("role");
		if(role != null && role.equals("manager")){
			request.setAttribute("role", "manager");
		}else{
			request.setAttribute("role", "user");
		}
		request.getRequestDispatcher("/login.jsp").forward(request, response);
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException{
		response.setContentType("text/html;charset=gbk");
		String role = request.getParameter("role");
		if(role != null && role.equals("manager")){
			managerLogin(request, response);
		}else{
			userLogin(request, response);
		}	
	}
	
	public void userLogin(HttpServletRequest request, HttpServletResponse response) throws
	ServletException, IOException{
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		UserService userService = new UserService();
		RequestDispatcher rd= null;
		try{
			BookUser bookUser = userService.login(email, password);
			HttpSession session = request.getSession();
			session.setAttribute("loginUser", bookUser);
			response.sendRedirect(request.getContextPath()+"/index.jsp");
		}
		catch(UserDoesNotExistException dex){
			rd = request.getRequestDispatcher("/login.jsp");
			request.setAttribute("role", "user");
			request.setAttribute("error", "” œ‰Œ¥◊¢≤·!");
			rd.forward(request, response);
		}catch(InvalidException iex){
			rd = request.getRequestDispatcher("/login.jsp");
			request.setAttribute("role", "user");
			request.setAttribute("error", "√‹¬Î¥ÌŒÛ!");
			rd.forward(request, response);
		}	
	}
	
	public void managerLogin(HttpServletRequest request, HttpServletResponse response) throws 
	ServletException, IOException{
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		ManagerService managerService = new ManagerService();
		RequestDispatcher rd= null;
		try{
			Manager manager = managerService.login(email, password);
			HttpSession session = request.getSession();
			session.setAttribute("loginManager", manager);
			String redirectURL = request.getContextPath()+"/books";
			response.sendRedirect(redirectURL);
		}
		catch(DenyException dx){
			rd = request.getRequestDispatcher("/login.jsp");
			request.setAttribute("role", "manager");
			request.setAttribute("error", "” œ‰Œ¥◊¢≤·!");
			rd.forward(request, response);
		}catch(InvalidException iex){
			rd = request.getRequestDispatcher("/login.jsp");
			request.setAttribute("role", "manager");
			request.setAttribute("error", "√‹¬Î¥ÌŒÛ!");
			rd.forward(request, response);
		}
	}
	
	
}
