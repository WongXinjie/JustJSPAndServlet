package com.bookstores.servlet;
import java.io.IOException;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import com.bookstores.domain.*;
import com.bookstores.exception.InvalidException;
import com.bookstores.service.*;

@WebServlet(name="CollectionListServlet", urlPatterns="/user/collection")
public class CollectionListServlet extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException{
		HttpSession session = request.getSession(false);
		BookUser bookUser = (BookUser)session.getAttribute("loginUser");
		List<UserCollection> collectionList = null;
		UserService userService = new UserService();
		try{
			collectionList = userService.queryCollectionByUserID(bookUser.getBookUserID());
		}catch(InvalidException e){
			e.printStackTrace();
			collectionList = new ArrayList<>();
		}
		request.setAttribute("collections", collectionList);
		request.getRequestDispatcher("/user/collection.jsp").forward(request, response);
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException{
		doGet(request, response);
	}
}
