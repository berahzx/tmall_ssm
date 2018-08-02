package com.how2java.tmall.service.impl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.how2java.tmall.mapper.CategoryMapper;
import com.how2java.tmall.page.Page;
import com.how2java.tmall.pojo.Category;
import com.how2java.tmall.pojo.CategoryExample;
import com.how2java.tmall.service.CategoryService;
@Service
public class CategoryServiceImpl  implements CategoryService {
    @Autowired
    CategoryMapper categoryMapper;
    public List<Category> list(){
    	CategoryExample categoryExample=new CategoryExample();
    	categoryExample.setOrderByClause("id desc");
        return categoryMapper.selectByExample(categoryExample);
    }
	@Override
	public void add(Category category) {
		// TODO Auto-generated method stub
		categoryMapper.insert(category);
	}
	@Override
	public Category get(int id) {
		// TODO Auto-generated method stub
		return categoryMapper.selectByPrimaryKey(id);
	};
 
}