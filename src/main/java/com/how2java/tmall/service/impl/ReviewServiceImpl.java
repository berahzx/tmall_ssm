package com.how2java.tmall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.how2java.tmall.mapper.ReviewMapper;
import com.how2java.tmall.pojo.Product;
import com.how2java.tmall.pojo.Review;
import com.how2java.tmall.pojo.ReviewExample;
import com.how2java.tmall.service.ReviewService;

@Service
public class ReviewServiceImpl implements ReviewService{
	@Autowired
	ReviewMapper reviewMapper;

	@Override
	public List<Review> list(Integer pid) {
		ReviewExample example=new ReviewExample();
		example.createCriteria().andPidEqualTo(pid);
		List<Review> reviews=reviewMapper.selectByExample(example);
		return reviews;
	}
	
	public void setReviewCount(Product product){
		 product.setReviewCount(list(product.getId()).size());
	}

	@Override
	public void setReviewCount(List<Product> product) {
		// TODO Auto-generated method stub
		for (Product product2 : product) {
			setReviewCount(product2);
		}
	}

	@Override
	public List<Review> listByPid(Integer pid) {
		// TODO Auto-generated method stub
		ReviewExample example=new ReviewExample();
		example.createCriteria().andPidEqualTo(pid);
		example.setOrderByClause("id desc");
		List<Review> reviews=reviewMapper.selectByExample(example);
		return reviews;
	}

	@Override
	public void add(Review review) {
		// TODO Auto-generated method stub
		reviewMapper.insert(review);
	}

}
