package com.bookstores.servlet;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import com.bookstores.domain.*;
import com.bookstores.exception.InvalidException;
import com.bookstores.service.*;
import com.bookstores.utils.CommonUtils;

@WebServlet(name="ProcessCartServlet", urlPatterns="/manager/processcart")
public class ProcessCartServlet extends HttpServlet{
	
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
		String caid = request.getParameter("caid");
		String result = null;
		String message = null;
		
		if(caid == null || !CommonUtils.isIneger(caid)){
			result = "FAIL";
			message = "参数出错!";
		}else{
			int cartID = Integer.parseInt(caid);
			ManagerService managerService = new ManagerService();
			BookOrder bookOrder = managerService.queryOrderByID(cartID);
			try{
				managerService.processOrder(bookOrder);
				result = "SUCCESS";
				message = "处理订单成功!";
			}catch(InvalidException e){
				e.printStackTrace();
				result = "FAIL";
				message = "处理订单出错!";
			}
		}
		request.setCharacterEncoding("utf-8");
		PrintWriter printer = response.getWriter();
		String response_data = String.format("[{status:\"%s\", message:\"%s\"}]", result, message);
		printer.println(response_data);
	}
}

