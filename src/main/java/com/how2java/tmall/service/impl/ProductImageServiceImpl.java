package com.how2java.tmall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.how2java.tmall.mapper.ProductImageMapper;
import com.how2java.tmall.mapper.ProductMapper;
import com.how2java.tmall.pojo.Product;
import com.how2java.tmall.pojo.ProductImage;
import com.how2java.tmall.pojo.ProductImageExample;
import com.how2java.tmall.service.ProductImageService;
@Service
public class ProductImageServiceImpl implements ProductImageService{

	@Autowired
	ProductImageMapper productImageMapper;
	@Autowired
	ProductMapper productMapper;
	@Override
	public List<ProductImage> list(Product product) {
		ProductImageExample example=new ProductImageExample();
		example.createCriteria().andPidEqualTo(product.getId());
		example.setOrderByClause("id desc");
		List<ProductImage> productImages=productImageMapper.selectByExample(example);
		setImageList(product,productImages);
		return productImages;
	}
	
	//初始化图片的集合
	public void setImageList(Product product,List<ProductImage> productImages){
		for (ProductImage productImage : productImages) {
			if(productImage.getType().equals(ProductImageService.singleType)){
				product.getSingleProductImage().add(productImage);
			}else{
				product.getDetailProductImage().add(productImage);
			}
		}
	}

	@Override
	public void add(ProductImage productImage) {
		// TODO Auto-generated method stub
		productImageMapper.insert(productImage);
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		productImageMapper.deleteByPrimaryKey(id);
	}

	@Override
	public ProductImage get(Integer id) {
		// TODO Auto-generated method stub
		return productImageMapper.selectByPrimaryKey(id);
	}

}
