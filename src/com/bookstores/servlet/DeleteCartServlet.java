package com.bookstores.servlet;
import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import com.bookstores.domain.*;
import com.bookstores.service.*;
import com.bookstores.exception.*;
import com.bookstores.utils.CommonUtils;

@WebServlet(name="DeleteCartServlet", urlPatterns="/user/deletecart")
public class DeleteCartServlet extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException{
		String caid = request.getParameter("caid");
		HttpSession session = request.getSession();
		BookUser bookUser = (BookUser)session.getAttribute("loginUser");
		if(caid!= null && CommonUtils.isIneger(caid)){
			UserService userService = new UserService();
			try{
				int cartID = Integer.parseInt(caid);
				int bookUserID = bookUser.getBookUserID();
				BookOrder bookOrder = userService.queryCartByID(cartID);
				if(bookUserID == bookOrder.getBookUserID()){
					userService.removeCart(bookOrder);
					session = request.getSession();
					session.removeAttribute("cart");
				}
			}catch(InvalidException invalid){
				invalid.printStackTrace();
			}
		}
		String redirectURL=request.getContextPath()+"/index.jsp";
		response.sendRedirect(redirectURL);
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException{
		doGet(request, response);
	}
}
