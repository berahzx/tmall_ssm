package com.how2java.tmall.service;

import java.util.List;

import com.how2java.tmall.pojo.Order;
import com.how2java.tmall.pojo.OrderItem;
import com.how2java.tmall.pojo.Product;

public interface OrderItemService {
	 void fill(List<Order> orders);
	 void fill(Order order);
	void setSaleCount(Product product);
	public void setSaleCount(List<Product> products);
	List<OrderItem> listByUser(Integer uid);
	void update(OrderItem orderItem);
	void add(OrderItem orderItem);
	OrderItem get(Integer id);
	List<OrderItem> listByUserAndOrder(Integer id, Integer id2);
	void delete(Integer oiid);
}
