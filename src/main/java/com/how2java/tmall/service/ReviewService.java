package com.how2java.tmall.service;

import java.util.List;

import com.how2java.tmall.pojo.Product;
import com.how2java.tmall.pojo.Review;

public interface ReviewService {

	List<Review> list(Integer id);
	public void setReviewCount(Product product);
	public void setReviewCount(List<Product> product);
	List<Review> listByPid(Integer id);
	void add(Review review);
}
