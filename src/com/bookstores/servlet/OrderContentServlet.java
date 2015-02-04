package com.bookstores.servlet;
import java.io.IOException;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import com.bookstores.domain.*;
import com.bookstores.exception.InvalidException;
import com.bookstores.service.*;
import com.bookstores.utils.CommonUtils;

@WebServlet(name="OrderContentServlet", urlPatterns="/vieworder")
public class OrderContentServlet extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException{
		String caid=request.getParameter("caid");
		BookOrder bookOrder = null;
		UserService userService = new UserService();
		HttpSession session = request.getSession();
		if(caid != null && CommonUtils.isIneger(caid)){
			int cartID = Integer.parseInt(caid);
			try{				
				bookOrder = userService.queryCartByID(cartID);
			}catch(InvalidException e){
				e.printStackTrace();
			}
		}else{
			bookOrder = (BookOrder) session.getAttribute("cart");
		}
		List<OrderBooks> orderBooksList=null;
		try{
			orderBooksList=userService.viewCartContent(bookOrder);
		}catch(InvalidException e){
			orderBooksList = new ArrayList<>();
		}
		
		request.setAttribute("cart", bookOrder);
		request.setAttribute("orderBooksList", orderBooksList);
		if(session.getAttribute("loginManager") != null){
			request.getRequestDispatcher("/manager/order.jsp").forward(request, response);
		}else{
			request.getRequestDispatcher("/user/cart.jsp").forward(request, response);
		}
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException{
		doGet(request, response);
	}
}
