package com.how2java.tmall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.how2java.tmall.mapper.PropertyMapper;
import com.how2java.tmall.pojo.Property;
import com.how2java.tmall.pojo.PropertyExample;
import com.how2java.tmall.service.PropertyService;
@Service
public class PropertyServiceImpl implements PropertyService{
	@Autowired
	PropertyMapper propertyMapper;
	@Override
	public List<Property> list(Integer cid) {
		// TODO Auto-generated method stub
		PropertyExample example=new PropertyExample();
		example.createCriteria().andCidEqualTo(cid);
		example.setOrderByClause("id desc");
		return propertyMapper.selectByExample(example);
	}
	@Override
	public void add(Property property) {
		// TODO Auto-generated method stub
		propertyMapper.insert(property);
	}
	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		propertyMapper.deleteByPrimaryKey(id);
	}
	@Override
	public Property get(Integer id) {
		// TODO Auto-generated method stub
		return propertyMapper.selectByPrimaryKey(id);
	}
	@Override
	public void update(Property property) {
		// TODO Auto-generated method stub
		propertyMapper.updateByPrimaryKey(property);
	}

}
