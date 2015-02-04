package com.bookstores.servlet;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import com.bookstores.domain.*;
import com.bookstores.service.*;
import com.bookstores.exception.*;
import com.bookstores.utils.CommonUtils;

@WebServlet(name="AddToCartServlet", urlPatterns="/user/addtocart")
public class AddToCartServlet extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException{
		doPost(request, response);
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException{
		response.setContentType("text/plain;charset=utf-8");
		
		HttpSession session = request.getSession();
		BookUser bookUser = (BookUser) session.getAttribute("loginUser");
		String bid = request.getParameter("bid");
		String result=null;
		String message = null;
		if(bid == null || !CommonUtils.isIneger(bid)){
			result = "FAIL";
			message = "参数错误!";
		}else{
			int bookID = Integer.parseInt(bid);
			UserService userService = new UserService();
			BookService bookService = new BookService();
			String bookName = bookService.getBookByID(bookID).getTitle();
			BookOrder bookOrder = (BookOrder)session.getAttribute("cart");
			//如果购物车为空，则初始化购物车。
			if(bookOrder == null){
				bookOrder = new BookOrder();
				bookOrder.setBookUserID(bookUser.getBookUserID());
				try{					
					bookOrder = userService.createCart(bookOrder);
				}catch(InvalidException e){
					result = "FAIL";
					message= e.getMessage();
					e.printStackTrace();
				}
			}
			OrderBooks orderBooks = null;
			try{
				orderBooks = userService.queryOrderBooksByID(bookID, bookOrder.getBookOrderID());
				int originVal = orderBooks.getCount();
				orderBooks.setCount(originVal+1);
				try{					
					userService.updateOrderBooks(orderBooks, originVal);
				}catch(InvalidException e){
					result = "FAIL";
					message = e.getMessage();
					e.printStackTrace();
				}
			}catch(EmptyException e){
				orderBooks = new OrderBooks();
				orderBooks.setBookID(bookID);
				orderBooks.setOrderID(bookOrder.getBookOrderID());
				orderBooks.setBookName(bookName);
				orderBooks.setCount(1);
				try{
					userService.addToCart(bookOrder, orderBooks);
				}catch(InvalidException ie){
					ie.printStackTrace();
				}
			}
			try{				
				bookOrder = userService.queryCartByID(bookOrder.getBookOrderID());
				session.setAttribute("cart", bookOrder);
				result = "SUCCESS";
				message = "添加到购物车成功!";
			}catch(InvalidException e){
				result = "FAIL";
				message = e.getMessage();
			}
		}
		request.setCharacterEncoding("utf-8");
		PrintWriter printer = response.getWriter();
		String response_date = String.format("[{status:\"%s\", message:\"%s\"}]", result, message);
		printer.println(response_date);
	}
}
