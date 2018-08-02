package com.how2java.tmall.interceptor;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.how2java.tmall.pojo.User;

public class LoginInterceptor implements HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		HttpSession session=request.getSession();
		String[] noNeedAuthPage = new String[]{
                "home",
                "checkLogin",
                "register",
                "loginAjax",
                "login",
                "product",
                "category",
                "search"};
		//获取到uri
		String uri=request.getRequestURI();
		//将/tmall_ssm去掉
		uri=StringUtils.remove(uri, "/tmall_ssm");
		//判断uri是否以/fore开头
		if(uri.startsWith("/fore")){
			//去掉fore
			String method=StringUtils.remove(uri, "/fore");
			//判断noNeedAuthPage中是否包含
			if(!Arrays.asList(noNeedAuthPage).contains(method)){
				//若果不包含在内，则取出user对象判断是否为null
				User user=(User) session.getAttribute("user");
				if(user==null){
					response.sendRedirect("loginPage");
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}


}
