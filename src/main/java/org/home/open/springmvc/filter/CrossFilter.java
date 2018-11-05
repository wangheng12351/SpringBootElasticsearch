package org.home.open.springmvc.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public class CrossFilter implements Filter{
    public void init(FilterConfig filterConfig) throws ServletException {
 
    }
	@Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response= (HttpServletResponse) servletResponse;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Headers", "Authentication");
       /* response.setHeader("Access-Control-Allow-Methods","POST,GET,OPTIONS,DELETE");
        response.setHeader("Access-Control-Max-Age","3600");
        response.setHeader("Access-Control-Allow-Credentials","true");*/
        filterChain.doFilter(servletRequest,servletResponse);
    }
    public void destroy() {
 
    }

}
