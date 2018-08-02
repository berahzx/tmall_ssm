package com.how2java.tmall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.how2java.tmall.page.Page;
import com.how2java.tmall.pojo.Order;
import com.how2java.tmall.service.OrderItemService;
import com.how2java.tmall.service.OrderService;

@Controller
@RequestMapping("")
public class OrderController {

	@Autowired
	OrderService orderService;
	@Autowired
	OrderItemService orderItemService;
	
	@RequestMapping("admin_order_list")
	public String list(Model model,Page page){
		PageHelper.offsetPage(page.getStart(), page.getCount());
		List<Order> orders=orderService.list();
		int total=(int) new PageInfo<>(orders).getTotal();
		page.setTotal(total);
		orderItemService.fill(orders);
		model.addAttribute("page", page);
		model.addAttribute("os", orders);
		return "admin/listOrder";
	}
}
