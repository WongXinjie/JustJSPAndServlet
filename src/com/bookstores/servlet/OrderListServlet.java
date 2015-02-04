package com.bookstores.servlet;
import java.io.IOException;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import com.bookstores.domain.*;
import com.bookstores.exception.InvalidException;
import com.bookstores.service.*;

@WebServlet(name="OrderListServlet", urlPatterns="/orders")
public class OrderListServlet extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException{
		HttpSession session = request.getSession(false);
		if(session.getAttribute("loginManager") != null){
			managerViewOrders(request, response);
		}else{
			userViewOrders(request, response);
		}
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException{
		doGet(request, response);
	}
	
	public void managerViewOrders(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException{
		String status = request.getParameter("status");
		int UNPROCESS = 1;
		int PROCESSED = 2;
		ManagerService managerService = new ManagerService();
		List<BookOrder> bookOrderList = null;
		if(status != null && status.equals("processed")){
			try{
				bookOrderList = managerService.getBookOrderList(PROCESSED);
			}catch(InvalidException e){
				e.printStackTrace();
			}
		}else if(status != null && status.equals("unprocess")){
			try{
				bookOrderList = managerService.getBookOrderList(UNPROCESS);
			}catch(InvalidException e){
				e.printStackTrace();
			}
		}else{
			try{
				bookOrderList = managerService.getBookOrderList(UNPROCESS);
				List<BookOrder> tmpList = managerService.getBookOrderList(PROCESSED);
				bookOrderList.addAll(tmpList);
			}catch(InvalidException e){
				e.printStackTrace();
			}
		}
		request.setAttribute("orders", bookOrderList);
		request.getRequestDispatcher("/manager/orders.jsp").forward(request, response);
	}
	
	public void userViewOrders(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException{
		HttpSession session = request.getSession(false);
		BookUser bookUser = (BookUser)session.getAttribute("loginUser");
		UserService userService = new UserService();
		String status = request.getParameter("status");
		int UNPAY = 0;
		int PAYED = 1;
		List<BookOrder> bookOrderList = null;
		if(status != null && status.equals("unpay")){
			bookOrderList = userService.viewOrderByStatus(bookUser, UNPAY);
		}else if(status != null && status.equals("payed")){
			bookOrderList = userService.viewOrderByStatus(bookUser, PAYED);
		}else{
			bookOrderList = userService.viewOrderByStatus(bookUser, UNPAY);
			List<BookOrder> tmpList = userService.viewOrderByStatus(bookUser, PAYED); 
			bookOrderList.addAll(tmpList);
		}
		request.setAttribute("orders", bookOrderList);
		request.getRequestDispatcher("/user/history.jsp").forward(request, response);
	}
}
