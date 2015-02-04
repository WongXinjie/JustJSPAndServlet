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

@WebServlet(name="AddCollectionBookServlet", urlPatterns="/user/addcollection")
public class AddCollectionServlet extends HttpServlet{
	
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
		HttpSession session = request.getSession(false);
		BookUser bookUser = (BookUser) session.getAttribute("loginUser");
		String bid = request.getParameter("bid");
		String result=null;
		String message = null;
		if(bid != null && CommonUtils.isIneger(bid)){
			UserService userService = new UserService();
			int bookUserID = bookUser.getBookUserID();
			int bookID = Integer.parseInt(bid);
			UserCollection collection = new UserCollection();
			collection.setBookUserID(bookUserID);
			collection.setBookID(bookID);
			try{
				collection = userService.addCollection(collection);
				result = "SUCCESS";
				message = "添加收藏成功!";
			}catch(InvalidException e){
				e.printStackTrace();
				result = "FAIL";
				message = e.getMessage();
			}
		}else{
			result = "FAIL";
			message = "参数错误!";
		}
		request.setCharacterEncoding("utf-8");
		PrintWriter printer = response.getWriter();
		String response_date = String.format("[{status:\"%s\", message:\"%s\"}]", result, message);
		printer.println(response_date);
	}
}
