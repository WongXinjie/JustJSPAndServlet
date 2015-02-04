package com.bookstores.servlet;
import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import com.bookstores.domain.*;
import com.bookstores.dao.*;
import com.bookstores.service.*;
import com.bookstores.exception.*;
import com.bookstores.utils.CommonUtils;

@WebServlet(name="DeleteOrderBooksServlet", urlPatterns="/user/deleteorderbook")
public class DeleteOrderBooksServlet extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException{
		HttpSession session = request.getSession();
		BookUser bookUser = (BookUser)session.getAttribute("loginUser");
		BookOrder bookOrder = (BookOrder)session.getAttribute("cart");
		String obid = request.getParameter("obid");
		if(obid != null && CommonUtils.isDouble(obid)){
			int orderBooksID = Integer.parseInt(obid);
			int orderID = bookOrder.getBookOrderID();
			UserService userService = new UserService();
			OrderBooks orderBooks = null;
			try{
				OrderBooksDao orderBooksDao = new OrderBooksDao();
				orderBooks = orderBooksDao.queryByID(orderBooksID);
				bookOrder = userService.queryCartByID(orderID);
				if(bookUser.getBookUserID() == bookOrder.getBookUserID()){
					userService.removeOrderBooks(orderBooks);
				}
			}catch(EmptyException e){
				e.printStackTrace();
			}catch(InvalidException e){
				e.printStackTrace();
			}
		}
		String redirectURL = request.getContextPath()+"/user/viewcart?caid="+bookOrder.getBookOrderID();
		response.sendRedirect(redirectURL);
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException{
		doGet(request, response);
	}
}
