package com.bookstores.servlet;
import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import com.bookstores.exception.InvalidException;
import com.bookstores.service.*;
import com.bookstores.utils.CommonUtils;

@WebServlet(name="DeleteCategoryServlet", urlPatterns="/manager/deletecategory")
public class DeleteCategoryServlet extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException{
		String cid = request.getParameter("cid");
		if(cid!= null && CommonUtils.isIneger(cid)){
			ManagerService managerService = new ManagerService();
			try{
				int categoryID = Integer.parseInt(cid);
				managerService.removeCategory(categoryID);
			}catch(InvalidException invalid){
				invalid.printStackTrace();
			}
		}
		String redirectURL = request.getContextPath()+"/manager/categories.jsp";
		response.sendRedirect(redirectURL);
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException{
		doGet(request, response);
	}
}
