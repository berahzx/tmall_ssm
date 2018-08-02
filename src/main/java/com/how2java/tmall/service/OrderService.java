package com.how2java.tmall.service;

import java.util.List;

import com.how2java.tmall.pojo.Order;
import com.how2java.tmall.pojo.OrderItem;

public interface OrderService {

	String waitPay = "waitPay";
    String waitDelivery = "waitDelivery";
    String waitConfirm = "waitConfirm";
    String waitReview = "waitReview";
    String finish = "finish";
    String delete = "delete";

	List<Order> list();
	void add(Order order);
	List<Order> listByUser(Integer id);
	float add(Order order,List<OrderItem> orderItems);
	Order get(Integer oid);
	void update(Order order);
	List<Order> list(Integer uid,String excludeStatus);

}
