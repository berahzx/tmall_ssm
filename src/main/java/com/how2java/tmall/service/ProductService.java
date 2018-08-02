package com.how2java.tmall.service;

import java.util.List;

import com.how2java.tmall.pojo.Category;
import com.how2java.tmall.pojo.Product;

public interface ProductService {
	List<Product> list(Integer cid);

	void add(Product product);

	Product get(Integer id);

	void update(Product product);

	void delete(Integer id);
	
	void setFirstProductImage(List<Product> products);
	
	void fill(Category category);
	
	void fill(List<Category> categories);
	
	void fillByRow(List<Category> categories);
	
	void fill(Category category,List<Product> products);
	

	List<Product> search(String keyword);
	
}
