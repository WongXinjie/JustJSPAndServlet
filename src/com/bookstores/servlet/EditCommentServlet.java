package com.bookstores.servlet;
import java.util.Date;
import java.util.Locale;
import java.text.SimpleDateFormat;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import com.bookstores.domain.*;
import com.bookstores.exception.*;
import com.bookstores.service.*;
import com.bookstores.utils.CommonUtils;

@WebServlet(name="EditCommentServlet", urlPatterns="/user/comment")
public class EditCommentServlet extends HttpServlet{
	
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
		response.setContentType("application/json;charset=utf-8");
		HttpSession session = request.getSession();
		BookUser bookUser = (BookUser)session.getAttribute("loginUser");
		String bid = request.getParameter("bid");
		String cmid = request.getParameter("cmid");
		String content = request.getParameter("content");
		UserService userService = new UserService();
		BookUserComment comment = null;
		String result = null;
		String message = null;
		if(cmid == null || !CommonUtils.isIneger(cmid)){
			comment = new BookUserComment();
			comment.setBookID(Integer.parseInt(bid));
			comment.setBookUserID(bookUser.getBookUserID());
		}else{
			try{
				comment = userService.queryUserCommentByID(Integer.parseInt(cmid));
			}catch(EmptyException emt){
				comment = new BookUserComment();
				comment.setBookID(Integer.parseInt(bid));
				comment.setBookUserID(bookUser.getBookUserID());
			}
		}
		comment.setContent(content);
		SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.SIMPLIFIED_CHINESE);
		String updateTime = simpleFormat.format(new Date());
		comment.setUpdateTime(updateTime);
		if(comment.getBookUserCommentID() == 0){
			try{
				userService.addComment(comment);
				result = "SUCCESS";
				message = content;
			}catch(InvalidException e){
				result = "FAIL";
				message = "新增评论失败!";
			}
		}else{
			try{
				userService.updateComment(comment);
				result = "SUCCESS";
				message = content;
			}catch(InvalidException e){
				result = "FAIL";
				message = "修改评论失败!";
			}
		}
		request.setCharacterEncoding("utf-8");
		PrintWriter printer = response.getWriter();
		String response_data = String.format("[{\"status\":\"%s\", \"message\":\"%s\"}]", result, message);
		printer.println(response_data);
	}
}
