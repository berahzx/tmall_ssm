package com.how2java.tmall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.how2java.tmall.mapper.PropertyMapper;
import com.how2java.tmall.mapper.PropertyValueMapper;
import com.how2java.tmall.pojo.Property;
import com.how2java.tmall.pojo.PropertyValue;
import com.how2java.tmall.pojo.PropertyValueExample;
import com.how2java.tmall.service.PropertyValueService;
@Service
public class PropertyValueServiceImpl implements PropertyValueService{

	@Autowired
	PropertyValueMapper propertyValueMapper;
	@Autowired
	PropertyMapper propertyMapper;
	@Override
	public void init(List<Property> properties,int pid) {
		// TODO Auto-generated method stub
		for(Property property:properties){
			PropertyValue propertyValue=get(pid,property.getId());
			if(propertyValue==null){
				propertyValue=new PropertyValue();
				propertyValue.setPid(pid);
				propertyValue.setPtid(propertyValue.getId());
				add(propertyValue);
			}
		}
	}

	@Override
	public PropertyValue get(Integer pid, Integer ptid){
		// TODO Auto-generated method stub
		PropertyValueExample propertyValueExample=new PropertyValueExample();
		propertyValueExample.createCriteria().andPidEqualTo(pid).andPtidEqualTo(ptid);
		propertyValueExample.setOrderByClause("id desc");
		List<PropertyValue> propertyValues=propertyValueMapper.selectByExample(propertyValueExample);
		if(propertyValues.isEmpty()){
			return null;
		}
		return propertyValues.get(0);
	}

	@Override
	public void add(PropertyValue propertyValue) {
		// TODO Auto-generated method stub
		propertyValueMapper.insert(propertyValue);
	}

	@Override
	public List<PropertyValue> list(Integer pid) {
		// TODO Auto-generated method stub
		PropertyValueExample example=new PropertyValueExample();
		example.createCriteria().andPidEqualTo(pid);
		example.setOrderByClause("id desc");
		List<PropertyValue> propertyValues= propertyValueMapper.selectByExample(example);
		//将存储在属性值表中的属性字段通过propertyMapper取出对应的属性进行存储
		for (PropertyValue propertyValue : propertyValues) {
			propertyValue.setProperty(propertyMapper.selectByPrimaryKey(propertyValue.getPtid()));
		}
		return propertyValues;
	}

	@Override
	public void update(PropertyValue propertyValue) {
		// TODO Auto-generated method stub
		//只修改propertyValue传入不为空的字段
		propertyValueMapper.updateByPrimaryKeySelective(propertyValue);
	}

}
