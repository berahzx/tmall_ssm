package com.how2java.tmall.service;

import java.util.List;

import com.how2java.tmall.pojo.Product;
import com.how2java.tmall.pojo.ProductImage;

public interface ProductImageService {
	public static final String singleType="type_single";
	public static final String detailType="type_detail";
	List<ProductImage> list(Product product);
	void add(ProductImage productImage);
	void delete(Integer id);
	ProductImage get(Integer id);

}
