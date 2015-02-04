package com.bookstores.servlet;
import java.io.IOException;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import com.bookstores.domain.*;
import com.bookstores.service.*;
import com.bookstores.utils.CommonUtils;

@WebServlet(name="BookListServlet", urlPatterns="/books")
public class BookListServlet extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException{
		response.setContentType("text/html;charset=utf-8");
		String page = request.getParameter("page");
		String cid = request.getParameter("category");
		List<Book> bookList = null;
		BookService bookService = new BookService();
		List<Category> categoryList = bookService.getCategoryList();
		HttpSession session = request.getSession();
		session.setAttribute("categoryList", categoryList);
		
		if(cid != null && CommonUtils.isIneger(cid)){
			int categoryID = Integer.parseInt(cid);
			bookList = bookService.getBookListByCategoryID(categoryID);
		}else if(page != null && CommonUtils.isIneger(page)){
			int p = Integer.parseInt(page);
			Integer currentPage = new Integer(p);
			request.setAttribute("currentPage", currentPage);
			bookList = bookService.getBookList(p);
		}else{
			bookList = bookService.getBookList(1);
		}

		RequestDispatcher rd = null;
		if(session.getAttribute("loginManager") != null){
			rd = request.getRequestDispatcher("/manager/booklist.jsp");
		}else{
			rd = request.getRequestDispatcher("/books.jsp");
		}
		request.setAttribute("books", bookList);
		Integer paginator = bookService.getPaginator();
		request.setAttribute("paginator", paginator);
		rd.forward(request, response);
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException{
		doGet(request, response);
	}
}
