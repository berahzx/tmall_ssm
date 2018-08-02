package com.how2java.tmall.service;

import java.util.List;

import com.how2java.tmall.pojo.Property;

public interface PropertyService {
	List<Property> list(Integer cid);
	void add(Property property);
	void delete(Integer id);
	Property get(Integer id);
	void update(Property property);

}
