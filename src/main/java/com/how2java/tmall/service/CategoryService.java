package com.how2java.tmall.service;
import java.util.List;

import com.how2java.tmall.page.Page;
import com.how2java.tmall.pojo.Category;
public interface CategoryService{
    List<Category> list();
	void add(Category category);
	Category get(int id);
}