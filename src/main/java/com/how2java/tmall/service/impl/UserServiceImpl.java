package com.how2java.tmall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.how2java.tmall.mapper.UserMapper;
import com.how2java.tmall.pojo.User;
import com.how2java.tmall.pojo.UserExample;
import com.how2java.tmall.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	UserMapper userMapper;
	
	@Override
	public List<User> list() {
		// TODO Auto-generated method stub
		UserExample example=new UserExample();
		example.setOrderByClause("id desc");
		return userMapper.selectByExample(example);
	}
	
	@Override
	public boolean isExist(User user) {
		// TODO Auto-generated method stub
		User isUser=getByName(user.getName());
		if(isUser!=null)
			return true;
		return false;
	}

	@Override
	public User get(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getByName(String name) {
		UserExample example=new UserExample();
		example.createCriteria().andNameEqualTo(name);
		List<User> users=userMapper.selectByExample(example);
		if(users.isEmpty())
			return null;
		return users.get(0);
	}

	@Override
	public void add(User user) {
		// TODO Auto-generated method stub
		userMapper.insert(user);
	}

	@Override
	public User get(String name, String password) {
		// TODO Auto-generated method stub
		UserExample example=new UserExample();
		example.createCriteria().andNameEqualTo(name).andPasswordEqualTo(password);
		List<User> users=userMapper.selectByExample(example);
		if(users.isEmpty()){
			return null;
		}
		return users.get(0);
	}

}
