package com.bookstores.servlet;
import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import com.bookstores.domain.*;
import com.bookstores.exception.InvalidException;
import com.bookstores.service.*;
import com.bookstores.utils.CommonUtils;

@WebServlet(name="EditCategoryServlet", urlPatterns="/manager/category")
public class EditCategoryServlet extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException{
		response.setContentType("text/html;charset=utf-8");
		String caid = request.getParameter("caid");
		if(caid != null && CommonUtils.isIneger(caid)){
			int categoryID = Integer.parseInt(caid);
			ManagerService managerService = new ManagerService();
			try{
				Category category = managerService.queryCategroyByID(categoryID);
				request.setAttribute("category", category);
			}catch(InvalidException e){
			}
		}
		RequestDispatcher rd = request.getRequestDispatcher("/manager/editcategory.jsp");
		rd.forward(request, response);
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException{
		response.setContentType("text/html;charset=utf-8");
		String caid = request.getParameter("caid");
		String name = request.getParameter("name");
		
		Category category = null;
		ManagerService  managerService = new ManagerService();
		if(caid != null && CommonUtils.isIneger(caid) && Integer.parseInt(caid)!= 0){
			int categoryID = Integer.parseInt(caid);
			try{				
				category = managerService.queryCategroyByID(categoryID);
			}catch(InvalidException e){
				e.printStackTrace();
			}
		}else{
			category = new Category();
		}
		
		RequestDispatcher rd = null;
		if(!CommonUtils.notEmpty(name)){
			rd = request.getRequestDispatcher("/manager/editcategory.jsp");
			rd.forward(request, response);
		}
		
		category.setName(name);
		Category insertCategory = null;
		try{
			insertCategory = managerService.addCategory(category);
			String redirectURL = request.getContextPath()+"/manager/categories.jsp";
			response.sendRedirect(redirectURL);
		}catch(InvalidException e){
			rd = request.getRequestDispatcher("/manager/editcategory.jsp");
			request.setAttribute("category", insertCategory);
			request.setAttribute("error", e.getMessage());
			rd.forward(request, response);
		}
	}
}
