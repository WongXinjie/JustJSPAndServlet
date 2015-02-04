package com.bookstores.servlet;
import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.bookstores.domain.*;
import com.bookstores.service.*;
import com.bookstores.exception.*;
import com.bookstores.utils.CommonUtils;

@WebServlet(name="EditBookServlet", urlPatterns="/manager/book")
public class EditBookServlet extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException{
		response.setContentType("text/html;charset=utf-8");
		String bid = request.getParameter("bid");
		if(bid != null && CommonUtils.isIneger(bid)){
			int bookID = Integer.parseInt(bid);
			BookService bookService = new BookService();
			Book book = bookService.getBookByID(bookID);
			request.setAttribute("book", book);
		}
		RequestDispatcher rd = request.getRequestDispatcher("/manager/editbook.jsp");
		rd.forward(request, response);
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException{
		response.setContentType("text/html;charset=utf-8");
		String bid = request.getParameter("bid");
		String title = request.getParameter("title");
		String ISBN = request.getParameter("isbn");
		String per = request.getParameter("per");
		String pda = request.getParameter("pda");
		String des = request.getParameter("des");
		String price = request.getParameter("price");
		String cid = request.getParameter("cid");
		
		
		Book book = null;
		if(bid != null && CommonUtils.isIneger(bid)&& Integer.parseInt(bid)!= 0){
			BookService bookService = new BookService();
			int bookID = Integer.parseInt(bid);
			book = bookService.getBookByID(bookID);
		}else{
			book = new Book();
		}
		book.setTitle(title);
		book.setISBN(ISBN);
		book.setPublisher(per);
		book.setPublishedDate(pda);
		book.setDescription(des);
		
		ManagerService managerService = new ManagerService();
		RequestDispatcher rd = null;
		if(!(CommonUtils.notEmpty(title, ISBN, per, pda, des, price, cid) && CommonUtils.isIneger(cid)
				&& CommonUtils.isDouble(price))){
			rd = request.getRequestDispatcher("/manager/editbook.jsp");
			request.setAttribute("book", book);
			request.setAttribute("error", "²ÎÊý´íÎó!");
			rd.forward(request, response);
			return;
		}
		
		book.setPrice(Double.parseDouble(price));
		book.setCategroyID(Integer.parseInt(cid));
		try{
			managerService.addBook(book);
			String dispatcherURL = String.format("%s/books", request.getContextPath());
			response.sendRedirect(dispatcherURL);
		}catch(InvalidException e){
			rd = request.getRequestDispatcher("/manager/editbook.jsp");
			request.setAttribute("book", book);
			request.setAttribute("error", e.getMessage());
			rd.forward(request, response);
		}
	}
}
