package com.how2java.tmall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.how2java.tmall.mapper.OrderItemMapper;
import com.how2java.tmall.mapper.ProductImageMapper;
import com.how2java.tmall.mapper.ProductMapper;
import com.how2java.tmall.mapper.UserMapper;
import com.how2java.tmall.pojo.Order;
import com.how2java.tmall.pojo.OrderItem;
import com.how2java.tmall.pojo.OrderItemExample;
import com.how2java.tmall.pojo.Product;
import com.how2java.tmall.pojo.ProductImage;
import com.how2java.tmall.pojo.ProductImageExample;
import com.how2java.tmall.service.OrderItemService;
import com.how2java.tmall.service.ProductImageService;
@Service
public class OrderItemServiceImpl implements OrderItemService{

	@Autowired 
	UserMapper userMapper;
	@Autowired 
	OrderItemMapper orderItemMapper;
	@Autowired
	ProductMapper productMapper;
	@Autowired
	ProductImageMapper productImageMapper;
	@Override
	public void fill(List<Order> orders) {
			for (Order order : orders) {
				fill(order);
			}
	}

	@Override
	public void fill(Order order) {
		//定义一个所有订单中包含订单项的方法
			//通过订单id与用户id获取到对应的订单项集合
			OrderItemExample example=new OrderItemExample();
			example.createCriteria().andOidEqualTo(order.getId());
			example.createCriteria().andUidEqualTo(order.getId());
			order.setUser(userMapper.selectByPrimaryKey(order.getUid()));
			List<OrderItem> orderItems=orderItemMapper.selectByExample(example);
			setProduct(orderItems);
			float total=0;
			int totalNumber=0;
			for (OrderItem orderItem : orderItems) {
				total+=orderItem.getProduct().getPromotePrice()*orderItem.getNumber();
				totalNumber+=orderItem.getNumber();
			}
			order.setTotal(total);
			order.setTotalNumber(totalNumber);
			order.setOrderItems(orderItems);
	}
	
	public void setProduct(List<OrderItem> orderItems){
		for (OrderItem orderItem : orderItems) {
			setProduct(orderItem);
		}
	}
	
	public void setProduct(OrderItem orderItem){
		Product product=productMapper.selectByPrimaryKey(orderItem.getPid());
		if(product.getFirstProductImage()==null){
			ProductImageExample example=new ProductImageExample();
			example.createCriteria().andPidEqualTo(product.getId()).andTypeEqualTo(ProductImageService.singleType);
			List<ProductImage> productImages=productImageMapper.selectByExample(example);
			if(!productImages.isEmpty()){
				product.setFirstProductImage(productImages.get(0));
			}
		}
		orderItem.setProduct(product);
	}
	@Override
	public void setSaleCount(List<Product> products){
		for (Product product : products) {
			setSaleCount(product);
		}
	}

	@Override
	public void setSaleCount(Product product) {
		int saleCount=0;
		OrderItemExample example=new OrderItemExample();
		example.createCriteria().andPidEqualTo(product.getId());
		List<OrderItem> orderItems=orderItemMapper.selectByExample(example);
		if(!orderItems.isEmpty()){
			for (OrderItem orderItem : orderItems) {
				saleCount+=orderItem.getNumber();
			}
			product.setSaleCount(saleCount);
		}product.setSaleCount(0);
	}

	@Override
	public List<OrderItem> listByUser(Integer uid) {
		//根据用户的id获取到对应的订单项集合
		OrderItemExample example=new OrderItemExample();
		example.createCriteria().andUidEqualTo(uid).andOidIsNull();
		example.setOrderByClause("id desc");
		List<OrderItem> orderItems=orderItemMapper.selectByExample(example);
		setProduct(orderItems);
		return orderItems;
	}

	@Override
	public void update(OrderItem orderItem) {
		// TODO Auto-generated method stub
		orderItemMapper.updateByPrimaryKeySelective(orderItem);
	}

	@Override
	public void add(OrderItem orderItem) {
		// TODO Auto-generated method stub
		orderItemMapper.insert(orderItem);
	}

	@Override
	public OrderItem get(Integer id) {
		// TODO Auto-generated method stub
		OrderItem orderItem= orderItemMapper.selectByPrimaryKey((id));
		setProduct(orderItem);
		return orderItem;
	}

	@Override
	public List<OrderItem> listByUserAndOrder(Integer uid, Integer oid) {
		// TODO Auto-generated method stub
		OrderItemExample example=new OrderItemExample();
		example.createCriteria().andUidEqualTo(uid).andOidEqualTo(oid);
		example.setOrderByClause("id desc");
		List<OrderItem> orderItems=orderItemMapper.selectByExample(example);
		return orderItems;
	}

	@Override
	public void delete(Integer oiid) {
		// TODO Auto-generated method stub
		orderItemMapper.deleteByPrimaryKey(oiid);
	}
	
}
