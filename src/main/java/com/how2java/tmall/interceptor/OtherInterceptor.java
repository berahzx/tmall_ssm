package com.how2java.tmall.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.how2java.tmall.pojo.Category;
import com.how2java.tmall.pojo.OrderItem;
import com.how2java.tmall.pojo.User;
import com.how2java.tmall.service.CategoryService;
import com.how2java.tmall.service.OrderItemService;

public class OtherInterceptor implements HandlerInterceptor{

	@Autowired
	CategoryService categoryService;
	@Autowired
	OrderItemService orderItemService;
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		//通过分类service获取到分类的集合
		List<Category> categories=categoryService.list();
		//将分类放在session中
		HttpSession session=request.getSession();
		session.setAttribute("cs", categories);
		//获取到contextPath路劲
		String contextPath=session.getServletContext().getContextPath();
		//存放到session域中
		session.setAttribute("contextPath", contextPath);
		//取出存放在session中的user对象
		User user=(User) session.getAttribute("user");
		if(user!=null){
			//通过userid取出对应的所有订单项
			List<OrderItem> orderItems=orderItemService.listByUser(user.getId());
			int totalNumber=0;
			//判断是否为空
			if(!orderItems.isEmpty()){
				//如果不为空，则进行遍历，然后相加数量
				for (OrderItem orderItem : orderItems) {
					totalNumber+=orderItem.getNumber();
				}
			}
			//然后将得到的总数存放到session中
			session.setAttribute("cartTotalItemNumber", totalNumber);
		}
		return ;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}
