package com.how2java.tmall.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.how2java.tmall.mapper.CategoryMapper;
import com.how2java.tmall.mapper.OrderItemMapper;
import com.how2java.tmall.mapper.ProductImageMapper;
import com.how2java.tmall.mapper.ProductMapper;
import com.how2java.tmall.pojo.Category;
import com.how2java.tmall.pojo.Product;
import com.how2java.tmall.pojo.ProductExample;
import com.how2java.tmall.pojo.ProductImage;
import com.how2java.tmall.pojo.ProductImageExample;
import com.how2java.tmall.service.ProductImageService;
import com.how2java.tmall.service.ProductService;
@Service
public class ProductServiceImpl implements ProductService{
	@Autowired
	ProductMapper productMapper;
	@Autowired
	ProductImageMapper productImageMapper;
	@Autowired
	CategoryMapper categoryMapper;
	@Autowired
	OrderItemMapper orderItemMapper;
	@Override
	public List<Product> list(Integer cid) {
		// TODO Auto-generated method stub
		ProductExample example=new ProductExample();
		example.createCriteria().andCidEqualTo(cid);
		example.setOrderByClause("id desc");
		List<Product> products=productMapper.selectByExample(example);
		setCategory(products);
		return products;
	}
	@Override
	public void add(Product product) {
		// TODO Auto-generated method stub
		productMapper.insert(product);
	}
	@Override
	public Product get(Integer id) {
		// TODO Auto-generated method stub
		Product product=productMapper.selectByPrimaryKey(id);
		setFirstProductImage(product);
		setCategory(product);
		return product;
	}
	@Override
	public void update(Product product) {
		// TODO Auto-generated method stub
		productMapper.updateByPrimaryKey(product);
	}
	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		productMapper.deleteByPrimaryKey(id);
	}
	@Override
	public void setFirstProductImage(List<Product> products) {
		for(Product product:products){
			setFirstProductImage(product);
		}
	}
	public void setFirstProductImage(Product product){
		ProductImageExample example=new ProductImageExample();
		example.createCriteria().andPidEqualTo(product.getId()).andTypeEqualTo(ProductImageService.singleType);
		List<ProductImage> productImages=productImageMapper.selectByExample(example);
		if(!productImages.isEmpty()){
			product.setFirstProductImage(productImages.get(0));
		}
		return ;
	}
	@Override
	public void fill(Category category) {
		//根据分类查询出对应的产品
		List<Product> products=list(category.getId());
		setFirstProductImage(products);
		category.setProducts(products);
	}
	@Override
	public void fill(List<Category> categories) {
		for (Category category : categories) {
			fill(category);
		}
	}
	@Override
	public void fillByRow(List<Category> categories) {
		//每行要填充的数量
		int rowNum=5;
		for (Category category : categories) {
			//获取到存储在分类里面的产品
			List<Product> products=category.getProducts();
			List<List<Product>> productByRow=new ArrayList<>();
			int end;
			for(int i=0;i<products.size();i+=rowNum){
				end=i+rowNum;
				end=end<products.size()?end:products.size();
				productByRow.add(products.subList(i, end));
			}
			category.setProductsByRow(productByRow);
		}
	}
	
	public void setCategory(List<Product> products){
		for (Product product : products) {
			setCategory(product);
		}
	}
	private void setCategory(Product product) {
		// TODO Auto-generated method stub
		Category category=categoryMapper.selectByPrimaryKey(product.getCid());
		product.setCategory(category);
	}
	@Override
	public void fill(Category category, List<Product> products) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public List<Product> search(String keyword) {
		ProductExample example=new ProductExample();
		example.createCriteria().andNameLike("%"+keyword+"%");
		example.setOrderByClause("id desc");
		List<Product> products=productMapper.selectByExample(example);
		setFirstProductImage(products);
		setCategory(products);
		return products;
	}
}
