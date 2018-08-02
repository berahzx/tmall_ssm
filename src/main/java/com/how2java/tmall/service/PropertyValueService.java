package com.how2java.tmall.service;

import java.util.List;

import com.how2java.tmall.pojo.Property;
import com.how2java.tmall.pojo.PropertyValue;

public interface PropertyValueService {
	
	public void init(List<Property> properties,int pid);
	PropertyValue get(Integer pid,Integer ptid);
	void add(PropertyValue propertyValue);
	public List<PropertyValue> list(Integer pid);
	public void update(PropertyValue propertyValue);
}
