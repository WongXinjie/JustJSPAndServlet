package com.bookstores.servlet;
import java.io.IOException;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import com.bookstores.domain.*;
import com.bookstores.service.*;
import com.bookstores.exception.*;
import com.bookstores.utils.CommonUtils;

@WebServlet(name="PayCartServlet", urlPatterns="/user/paycart")
public class PayCartServlet extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException{
		String caid = request.getParameter("caid");
		HttpSession session = request.getSession();
		BookUser bookUser = (BookUser) session.getAttribute("loginUser");
		BookOrder cart = (BookOrder) session.getAttribute("cart");
		RequestDispatcher rd = null;
		if(!(caid != null && CommonUtils.isIneger(caid))){
			rd = request.getRequestDispatcher("/user/vieworder?caid="+cart.getBookOrderID());
			request.setAttribute("error", "参数错误!");
			rd.forward(request, response);
		}
		
		try{
			int cartID = Integer.parseInt(caid);
			int bookUserID = bookUser.getBookUserID();
			UserService userService = new UserService();
			BookOrder bookOrder= userService.queryCartByID(cartID);
			if(bookOrder.getBookUserID() != bookUserID){
				rd = request.getRequestDispatcher("/user/vieworder?caid="+cart.getBookOrderID());
				request.setAttribute("error", "权限不够!");
				rd.forward(request, response);
			}
			bookOrder = userService.payForCart(bookOrder);
			session.removeAttribute("cart");
			String redirectURL = request.getContextPath()+"/user/success.jsp";
			response.sendRedirect(redirectURL);
		}catch(InvalidException e){
			rd = request.getRequestDispatcher("/user/vieworder?caid="+cart.getBookOrderID());
			request.setAttribute("error", "支付出错!");
			rd.forward(request, response);
		}
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException{
		doGet(request, response);
	}
}
