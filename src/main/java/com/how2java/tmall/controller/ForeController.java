package com.how2java.tmall.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.how2java.tmall.comparator.ProductAllComparator;
import com.how2java.tmall.comparator.ProductDateComparator;
import com.how2java.tmall.comparator.ProductPriceComparator;
import com.how2java.tmall.comparator.ProductReviewComparator;
import com.how2java.tmall.comparator.ProductSaleCountCamparator;
import com.how2java.tmall.pojo.Category;
import com.how2java.tmall.pojo.Order;
import com.how2java.tmall.pojo.OrderItem;
import com.how2java.tmall.pojo.Product;
import com.how2java.tmall.pojo.PropertyValue;
import com.how2java.tmall.pojo.Review;
import com.how2java.tmall.pojo.User;
import com.how2java.tmall.service.CategoryService;
import com.how2java.tmall.service.OrderItemService;
import com.how2java.tmall.service.OrderService;
import com.how2java.tmall.service.ProductImageService;
import com.how2java.tmall.service.ProductService;
import com.how2java.tmall.service.PropertyValueService;
import com.how2java.tmall.service.ReviewService;
import com.how2java.tmall.service.UserService;

@Controller
public class ForeController {
	@Autowired
	CategoryService categoryService;
	@Autowired
	ProductService productService;
	@Autowired
	UserService userService;
	@Autowired
	ReviewService reviewService;
	@Autowired
	PropertyValueService PropertyValueService;
	@Autowired
	ProductImageService productImageService;
	@Autowired
	OrderItemService orderItemService;
	@Autowired
	OrderService orderService;
	
	@RequestMapping("/forehome")
	public String home(Model model){
		List<Category> categories=categoryService.list();
		//将查询到的分类集合填充产品
		productService.fill(categories);
		//填充每一行
		productService.fillByRow(categories);
		model.addAttribute("cs", categories);
		return "fore/home";
	}
	
	@RequestMapping("/forelogin")
	public String login(Model model,User user,HttpSession session){
		User u=userService.get(user.getName(),user.getPassword());
		if(u==null){
			model.addAttribute("msg", "用户或密码错误");
			return "fore/login";
		}
		session.setAttribute("user", u);
		return "redirect:/forehome";
	}
	
	@RequestMapping("forelogout")
	public String out(HttpSession session){
		session.setAttribute("user", null);
		return "redirect:/forehome";
	}
	@RequestMapping("/foreregister")
	public String register(User user,Model model){
		boolean b=userService.isExist(user);
		if(userService.isExist(user)){
			model.addAttribute("msg", "用户已存在");
			return "fore/register";
		}
		//如果不存在，则将此用户插入到数据库中
		userService.add(user);
		return "redirce:registerSuccess";
	}
	
	@RequestMapping("/foreproduct")
	public String product(Integer pid,Model model){
		//首先将产品进行查询
		Product product=productService.get(pid);
		orderItemService.setSaleCount(product);
		reviewService.setReviewCount(product);
		productImageService.list(product);
		//然后进行对应的产品的评价进行查询
		List<Review> reviews=reviewService.listByPid(product.getId());
		//获取到对应的属性值
		List<PropertyValue> propertyValues=PropertyValueService.list(pid);
		model.addAttribute("pvs", propertyValues);
		model.addAttribute("reviews", reviews);
		model.addAttribute("p", product);
		return "fore/product";
	}
	
	
	@RequestMapping("/forecheckLogin")
	@ResponseBody
	public String checkLogin(HttpSession session){
		//从session中取出用户
		User user=(User) session.getAttribute("user");
		//判断用户是否为空
		if(user==null){
			return "false";
		}
		return "success";
	}
	@RequestMapping("/foreloginAjax")
	@ResponseBody
	public String loginAjax(User user,HttpSession session){
		User u=userService.get(user.getName(), user.getPassword());
		if(u==null){
			return "false";
		}
		session.setAttribute("user", u);
		//如果用户存在，则存放到session中
		return "success";
	}
	
	@RequestMapping("/forecategory")
	public String category(Model model,Integer cid,String sort){
		//获取到分类对象
		Category category=categoryService.get(cid);
		productService.fill(category);
		//填充sale和review
		orderItemService.setSaleCount(category.getProducts());
		reviewService.setReviewCount(category.getProducts());
		if(null!=sort){
			switch(sort){
			case "review":Collections.sort(category.getProducts(), new ProductReviewComparator());break;
			case "date":
				Collections.sort(category.getProducts(), new ProductDateComparator());break;
			case "saleCount":
				Collections.sort(category.getProducts(), new ProductSaleCountCamparator());break;
			case "price":Collections.sort(category.getProducts(), new ProductPriceComparator());break;
			case "all":Collections.sort(category.getProducts(), new ProductAllComparator());break;
			}
		}
		model.addAttribute("c", category);
		return "fore/category";
	}
	
	@RequestMapping("/foresearch")
	public String search(Model model,String keyword){
		List<Product> products=productService.search(keyword);
		//设置评价和销量
		orderItemService.setSaleCount(products);
		reviewService.setReviewCount(products);
		model.addAttribute("ps", products);
		return "fore/searchResult";
	}
	
	@RequestMapping("forebuyone")
	public String buyone(Integer pid,Integer num,HttpSession session){
		//获取到session中的user
		User user=(User) session.getAttribute("user");
		boolean flag=false;
		Integer oiid=0;
		//通过uid进行对产品订单项进行查询
		List<OrderItem> orderItems=orderItemService.listByUser(user.getId());
		if(!orderItems.isEmpty()){
			for (OrderItem orderItem : orderItems) {
					if(orderItem.getPid()==pid){
						orderItem.setNumber(orderItem.getNumber()+num);
						orderItemService.update(orderItem);
						oiid=orderItem.getId();
						flag=true;
						break;
					}
			}
		}
		if(!flag){
			//创建一个订单项
			OrderItem orderItem=new OrderItem();
			//设置的对应的pid，uid,num
			orderItem.setPid(pid);
			orderItem.setNumber(num);
			orderItem.setUid(user.getId());
			orderItemService.add(orderItem);
			oiid=orderItem.getId();
		}
		return "redirect:/forebuy?oiid="+oiid;
	}
	
	@RequestMapping("/forebuy")
	public String buy(String[] oiid,Model model,HttpSession session){
		float total=0;
		List<OrderItem> ois=new ArrayList<>();
		for(Integer i=0;i<oiid.length;i++){
			Integer id=Integer.parseInt(oiid[i]);
			ois.add(orderItemService.get(id));
		}
		for (OrderItem orderItem : ois) {
			total+=orderItem.getNumber()*productService.get(orderItem.getPid()).getPromotePrice();
		}
		session.setAttribute("ois", ois);
		model.addAttribute("total", total);
		return "fore/buy";
	}
	
	@RequestMapping("/forecreateOrder")
	public String createOrder(Order order,HttpSession session){
		//获取到user对象
		User user=(User) session.getAttribute("user");
		float total=0;
		String orderCode=new SimpleDateFormat("yyMMddhhmmss").format(new Date())+new Random().nextInt(10000);
		order.setOrderCode(orderCode);
		order.setCreateDate(new Date());
		//从Session中获取到订单集合
		List<OrderItem> orderItems=(List<OrderItem>) session.getAttribute("ois");
		//及那个订单项加如到订单中
		order.setOrderItems(orderItems);
		order.setStatus(OrderService.waitPay);
		order.setUid(user.getId());
		//存到数据库中
		orderService.add(order);
		//然后将订单项中的oid进行更新
		for (OrderItem orderItem : orderItems) {
			orderItem.setOid(order.getId());
			orderItemService.update(orderItem);
			total+=orderItem.getNumber()*productService.get(orderItem.getPid()).getPromotePrice();
		}
		
		return "redirect:/forealipay?oid="+order.getId()+"&total="+total;
	}
	@RequestMapping("/foreaddCart")
	@ResponseBody
	public String addCart(Integer pid,Integer num,HttpSession session){
		//获取到对应的用户
		User user=(User) session.getAttribute("user");
		boolean flag=false;
		//获取到订单项集合
		List<OrderItem> orderItems=orderItemService.listByUser(user.getId());
		//判断订单集合是否为空
		if(!orderItems.isEmpty()){
			for (OrderItem orderItem : orderItems) {
				if(orderItem.getPid()==pid){
					orderItem.setNumber(orderItem.getNumber()+num);
					orderItemService.update(orderItem);
					flag=true;
					break;
				}
			}
		}
		if(!flag){
			OrderItem orderItem=new OrderItem();
			orderItem.setNumber(num);
			orderItem.setPid(pid);
			orderItem.setUid(user.getId());
			orderItemService.add(orderItem);
		}
		return "success";
	}
	
	@RequestMapping("/forecart")
	public String cart(HttpSession session,Model model){
		//从session取出用户
		User user=(User) session.getAttribute("user");
		if(user==null){
			return "redirect:/loginPage";
		}
		//根据用户取出对应的订单项
		List<OrderItem> orderItems=orderItemService.listByUser(user.getId());
		model.addAttribute("ois", orderItems);
		return "fore/cart";
	}
	
	
	@RequestMapping("/forechangeOrderItem")
	@ResponseBody
	public String changeOrderItem(Integer pid,Integer number,HttpSession session){
		//判断用户是否登陆
		User user=(User) session.getAttribute("user");
		if(user==null){
			return "fail";
		}
		//已登陆，则根据uid取出对应的所有订单项
		List<OrderItem> orderItems=orderItemService.listByUser(user.getId());
		//然后遍历所有的订单项
		for (OrderItem orderItem : orderItems) {
			//判断它的pid是否与传来的pid相同
			if(orderItem.getPid()==pid){
				orderItem.setNumber(+number);
				//更新到数据库中
				orderItemService.update(orderItem);
				break;
			}
		}
		return "success";
	}
	
	public String deleteOrderItem(Integer oiid,HttpSession session){
		//判断用户是否登陆
		User user=(User) session.getAttribute("user");
		if(user==null){
			return "fail";
		}
		//根据oiid删除对应的orderitem
		orderItemService.delete(oiid);
		return "success";
	}
	
	@RequestMapping("/forepayed")
	public String payed(Integer oid,Model model){
		//根据oid获取到对应的订单
		Order order=orderService.get(oid);
		//将订单下的对应的订单项取出放在相对应的集合中
		orderItemService.fill(order);
		//更新订单的支付时间与状态
		order.setPayDate(new Date());
		order.setStatus(OrderService.waitDelivery);
		//更新数据库中的订单状态
		orderService.update(order);
		//将订单对象放在model对象中
		model.addAttribute("o", order);
		return "fore/payed";
	}
	
	@RequestMapping("/forebought")
	public String bought(HttpSession session,Model model,String state){
		//通过session获取到user
		User user=(User) session.getAttribute("user");
		List<Order> orders=null;
		//通过uid获取到此用户所有的订单
		orders=orderService.list(user.getId(),state);
		orderItemService.fill(orders);
		model.addAttribute("os", orders);
		return "fore/bought";
	}
	
	//确认收货
	@RequestMapping("/foreconfirmPay")
	public String confirmPay(Integer oid,Model model){
		//通过oid获取到order
		Order order=orderService.get(oid);
		orderItemService.fill(order);
		model.addAttribute("o", order);
		return "fore/confirmPay";
	}
	@RequestMapping("/foreorderConfirmed")
	public String orderConfirmed(Integer oid){
		//通过oid获取到对应的订单
		Order order=orderService.get(oid);
		//然后修改订单信息
		order.setStatus(OrderService.waitReview);
		order.setConfirmDate(new Date());
		orderService.update(order);
		return "redirect:/forebought";
	}
	
	@RequestMapping("/forereview")
	public String review(Integer oid,Model model){
		//根据oid获取到订单
		Order order=orderService.get(oid);
		orderItemService.fill(order);
		List<OrderItem> orderItems=order.getOrderItems();
		Product product=productService.get(orderItems.get(0).getPid());
		//通过产品id获取到评价集
		List<Review> reviews=reviewService.listByPid(product.getId());
		//为产品设置评价数量和销量
		orderItemService.setSaleCount(product);
		reviewService.setReviewCount(product);
		model.addAttribute("p",product );
		model.addAttribute("reviews",reviews );
		model.addAttribute("o", order);
		return "fore/review";
	}
	
	@RequestMapping("/foredeleteOrder")
	@ResponseBody
	public String deleteOrder(Integer oid){
		//通过oid获取到对应的oid
		Order order=orderService.get(oid);
		//将order的statue设置成delete
		order.setStatus(OrderService.delete);
		//更新到数据库
		orderService.update(order);
		return "success";
	}
	
	@RequestMapping("/foredoreview")
	public String doreview(String content,Integer pid,Integer oid,HttpSession session){
		//通过session获取到user
		User user=(User) session.getAttribute("user");
		//创建一个评价对象
		Review review=new Review();
		review.setCreateDate(new Date());
		review.setContent(content);
		review.setPid(pid);
		review.setUid(user.getId());
		review.setUser(user);
		review.setProduct(productService.get(pid));
		//然后将评价保存在数据库中
		reviewService.add(review);
		//通过oid获取到订单对象
		Order order=orderService.get(oid);
		order.setStatus(OrderService.finish);
		orderService.update(order);
		return "redirect:/forereview?showonly=true&oid="+oid;
		
	}
}
