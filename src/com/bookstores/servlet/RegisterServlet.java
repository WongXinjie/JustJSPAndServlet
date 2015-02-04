package com.bookstores.servlet;
import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import com.bookstores.exception.*;
import com.bookstores.domain.*;
import com.bookstores.service.*;
import com.bookstores.utils.*;

@WebServlet(name="RegisterServlet", urlPatterns="/register")
public class RegisterServlet  extends HttpServlet{
	
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
		request.getRequestDispatcher("/register.jsp").forward(request, response);
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException{
		response.setContentType("text/html;charset=gbk");
		String role = request.getParameter("role");
		if(role != null && role.equals("manager")){
			managerRegister(request, response);
		}else{
			userRegister(request, response);
		}
	}
	
	public void userRegister(HttpServletRequest request, HttpServletResponse response) throws 
	ServletException, IOException {
		String email = request.getParameter("email");
		String name = request.getParameter("username");
		String passwordA = request.getParameter("password1");
		String passwordB = request.getParameter("password2");
		UserService userService = new UserService();
		RequestDispatcher rd= null;
		if(!UserValidUtil.valid(email, name, passwordA, passwordB)){
			rd = request.getRequestDispatcher("/register.jsp");
			request.setAttribute("role", "user");
			request.setAttribute("error", "–≈œ¢ÃÓ–¥¥ÌŒÛ!");
			rd.forward(request, response);
		}
		if(!passwordA.equals(passwordB)){
			rd = request.getRequestDispatcher("/register.jsp");
			request.setAttribute("role", "user");
			request.setAttribute("error", "√‹¬Î≤ª“ª÷¬!");
			rd.forward(request, response);
		}
		try{
			BookUser bookUser = new BookUser();
			bookUser.setEmail(email);
			bookUser.setBookUserName(name);
			bookUser.setPassword(passwordA);
			bookUser = userService.register(bookUser);
			HttpSession session = request.getSession();
			session.setAttribute("loginUser", bookUser);
			rd = request.getRequestDispatcher("/index.jsp");
			rd.forward(request, response);
		}
		catch(EmailTakenException dex){
			rd = request.getRequestDispatcher("/register.jsp");
			request.setAttribute("role", "user");
			request.setAttribute("error", "” œ‰“—◊¢≤·£¨«Î÷±Ω”µ«¬º!");
			rd.forward(request, response);
		}catch(InvalidException iex){
			rd = request.getRequestDispatcher("/register.jsp");
			request.setAttribute("role", "user");
			request.setAttribute("error", iex.getMessage());
			rd.forward(request, response);
		}
	}
	
	public void managerRegister(HttpServletRequest request, HttpServletResponse response) throws
	ServletException, IOException{
		
		String email = request.getParameter("email");
		String name = request.getParameter("username");
		String passwordA = request.getParameter("password1");
		String passwordB = request.getParameter("password2");
		ManagerService managerService = new ManagerService();
		RequestDispatcher rd= null;
		if(!UserValidUtil.valid(email, name, passwordA, passwordB)){
			rd = request.getRequestDispatcher("/register.jsp");
			request.setAttribute("role", "manager");
			request.setAttribute("error", "–≈œ¢ÃÓ–¥¥ÌŒÛ!");
			rd.forward(request, response);
		}
		if(!passwordA.equals(passwordB)){
			rd = request.getRequestDispatcher("/register.jsp");
			request.setAttribute("role", "manager");
			request.setAttribute("error", "√‹¬Î≤ª“ª÷¬!");
			rd.forward(request, response);
		}
		try{
			Manager manager = new Manager();
			manager.setEmail(email);
			manager.setManagerName(name);
			manager.setPassword(passwordA);
			System.out.println(manager.getEmail()+manager.getManagerName()+manager.getPassword());
			Manager loginManager = managerService.create(manager);
			HttpSession session = request.getSession();
			session.setAttribute("loginManger", loginManager);
			response.sendRedirect("/manager/booklist.jsp");
		}catch(EmailTakenException dex){
			rd = request.getRequestDispatcher("/register.jsp");
			request.setAttribute("role", "manager");
			request.setAttribute("error", "” œ‰“—◊¢≤·£¨«Î÷±Ω”µ«¬º!");
			rd.forward(request, response);
		}catch(InvalidException iex){
			rd = request.getRequestDispatcher("/register.jsp");
			request.setAttribute("role", "manager");
			request.setAttribute("error", "√‹¬Î¥ÌŒÛ!");
			rd.forward(request, response);
		}
	}
}
