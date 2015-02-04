package com.bookstores.filter;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import com.bookstores.domain.Category;
import com.bookstores.service.BookService;

import java.io.*;
import java.util.List;

@WebFilter(filterName="encodingfilter", urlPatterns="/*")
public class EncodingFilter implements Filter{
	private String targetEncoding = "UTF-8";
	protected FilterConfig filterConfig;
	
	public void init(FilterConfig config) throws ServletException{
		this.filterConfig = config;
		if(config.getInitParameter("encoding") != null && !config.getInitParameter("encoding").equals("")){
			this.targetEncoding = config.getInitParameter("encoding");
		}
	}

	public void destroy(){
		this.filterConfig = null;
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException{
		HttpServletRequest req= (HttpServletRequest) request;
		HttpServletResponse rep = (HttpServletResponse) response;
		HttpSession session = req.getSession();
		BookService bookService = new BookService();
		List<Category> categoryList = bookService.getCategoryList();
		session.setAttribute("categoryList", categoryList);
		req.setCharacterEncoding(this.targetEncoding);
		rep.setContentType("text/html);charset="+this.targetEncoding);
		chain.doFilter(request, response);
	}
}

