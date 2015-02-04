package com.bookstores.filter;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import java.io.*;

@WebFilter(filterName="orderfilter", urlPatterns={"/vieworder", "/orders"})
public class OrderFilter implements Filter{

	public void init(FilterConfig config){
	}

	public void destroy(){
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException{
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession(false);
		
		if(session != null && (session.getAttribute("loginUser") != null || session.getAttribute("loginManager") != null)){
			chain.doFilter(request, response);
		}else{
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		}
	}
}