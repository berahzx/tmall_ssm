package com.how2java.tmall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.how2java.tmall.mapper.OrderItemMapper;
import com.how2java.tmall.mapper.OrderMapper;
import com.how2java.tmall.mapper.UserMapper;
import com.how2java.tmall.pojo.Order;
import com.how2java.tmall.pojo.OrderExample;
import com.how2java.tmall.pojo.OrderItem;
import com.how2java.tmall.pojo.OrderItemExample;
import com.how2java.tmall.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService{

	@Autowired
	OrderMapper orderMapper;
	@Autowired
	OrderItemMapper orderItemMapper;
	@Autowired
	UserMapper userMapper;
	
	
	@Override
	public List<Order> list() {
		//查询出所有的订单集合
		OrderExample example=new OrderExample();
		example.setOrderByClause("id desc");
		List<Order> orders=orderMapper.selectByExample(example);
		return orders;
	}


	@Override
	public void add(Order order) {
		// TODO Auto-generated method stub
		orderMapper.insert(order);
	}


	@Override
	public List<Order> listByUser(Integer id) {
		// TODO Auto-generated method stub
		OrderExample example=new OrderExample();
		example.createCriteria().andUidEqualTo(id);
		example.setOrderByClause("id desc");
		List<Order> orders=orderMapper.selectByExample(example);
		return orders;
	}

	@Transactional(rollbackForClassName="Exception",propagation=Propagation.REQUIRED)
	@Override
	public float add(Order order, List<OrderItem> orderItems) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public Order get(Integer oid) {
		// TODO Auto-generated method stub
		return orderMapper.selectByPrimaryKey(oid);
	}


	@Override
	public void update(Order order) {
		// TODO Auto-generated method stub
		orderMapper.updateByPrimaryKeySelective(order);
	}


	@Override
	public List<Order> list(Integer uid, String excludeStatus) {
		// TODO Auto-generated method stub
		OrderExample example=new OrderExample();
		if(excludeStatus==null)
			example.createCriteria().andUidEqualTo(uid).andStatusNotEqualTo(OrderService.delete);
		else
			example.createCriteria().andUidEqualTo(uid).andStatusEqualTo(excludeStatus);
		example.setOrderByClause("id desc");
		List<Order> orders=orderMapper.selectByExample(example);
		return orders;
	}
	
	
}
