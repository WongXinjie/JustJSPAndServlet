package com.bookstores.servlet;
import java.io.IOException;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import com.bookstores.domain.*;
import com.bookstores.service.*;
import com.bookstores.utils.CommonUtils;

@WebServlet(name="BookDetailServlet", urlPatterns="/book")
public class BookDetailServlet extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException{
		response.setContentType("text/html;charset=utf-8");
		String bid = request.getParameter("bid");
		int bookID = 1;
		if(bid != null && CommonUtils.isIneger(bid)){
			bookID = Integer.parseInt(bid);
		}
		BookService bookService = new BookService();
		Book book = bookService.getBookByID(bookID);
		List<BookUserComment> commentList = bookService.getCommentByBookID(bookID);
		RequestDispatcher rd = request.getRequestDispatcher("/detail.jsp");
		request.setAttribute("book", book);
		request.setAttribute("commentList", commentList);
		rd.forward(request, response);
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException{
		doGet(request, response);
	}
}
