package com.bookstores.servlet;
import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import com.bookstores.domain.*;
import com.bookstores.service.*;
import com.bookstores.exception.*;
import com.bookstores.utils.CommonUtils;

@WebServlet(name="DeleteBookServlet", urlPatterns="/manager/deletebook")
public class DeleteBookServlet extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException{
		String bid = request.getParameter("bid");
		if(bid!= null && CommonUtils.isIneger(bid)){
			ManagerService managerService = new ManagerService();
			BookService bookService = new BookService();
			try{
				int bookID = Integer.parseInt(bid);
				Book book = bookService.getBookByID(bookID);
				managerService.removeBook(book);
			}catch(InvalidException invalid){
				invalid.printStackTrace();
			}
		}
		String dispatcherURL = String.format("%s/books", request.getContextPath());
		response.sendRedirect(dispatcherURL);	
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException{
		doGet(request, response);
	}
}
