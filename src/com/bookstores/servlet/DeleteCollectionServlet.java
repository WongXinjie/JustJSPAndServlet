package com.bookstores.servlet;
import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import com.bookstores.domain.*;
import com.bookstores.exception.InvalidException;
import com.bookstores.service.*;
import com.bookstores.utils.CommonUtils;

@WebServlet(name="DeleteCollecitonServlet", urlPatterns="/user/deletecollection")
public class DeleteCollectionServlet extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException{
		String coid = request.getParameter("coid");
		HttpSession session = request.getSession();
		BookUser bookUser = (BookUser)session.getAttribute("loginUser");
		if(coid!= null && CommonUtils.isIneger(coid)){
			UserService userService = new UserService();
			try{
				int collectionID = Integer.parseInt(coid);
				int bookUserID = bookUser.getBookUserID();
				UserCollection collection = userService.queryCollectionByID(collectionID);
				if(bookUserID ==  collection.getBookUserID()){
					userService.removeCollection(collection);
				}
			}catch(InvalidException invalid){
				invalid.printStackTrace();
			}
		}
		String redirectURL = request.getContextPath()+"/user/collection";
		response.sendRedirect(redirectURL);
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException{
		doGet(request, response);
	}
}
