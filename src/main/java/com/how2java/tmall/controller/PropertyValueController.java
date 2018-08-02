package com.how2java.tmall.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.how2java.tmall.page.Page;
import com.how2java.tmall.pojo.Category;
import com.how2java.tmall.pojo.Product;
import com.how2java.tmall.pojo.Property;
import com.how2java.tmall.pojo.PropertyValue;
import com.how2java.tmall.service.CategoryService;
import com.how2java.tmall.service.ProductService;
import com.how2java.tmall.service.PropertyService;
import com.how2java.tmall.service.PropertyValueService;

@Controller
@RequestMapping("")
public class PropertyValueController {
	@Autowired
	CategoryService categoryService;
	@Autowired
	ProductService productService;
	@Autowired
	PropertyService propertyService;
	@Autowired
	PropertyValueService propertyValueService;
	@RequestMapping("admin_propertyValue_edit")
	public String list(int pid,Model model){
		Product product=productService.get(pid);
		Category category=categoryService.get(product.getCid());
		product.setCategory(category);
		//通过product中的cid获取到对那个的属性
		List<Property> properties=propertyService.list(product.getCid());
		//初始化属性值，进新添加的属性可以及时的更新到属性值列表当中
		propertyValueService.init(properties, pid);
		//进行List查询
		List<PropertyValue> propertyValues=propertyValueService.list(pid);
		//将集合传到model中
		model.addAttribute("pvs", propertyValues);
		model.addAttribute("p", product);
		return "admin/editPropertyValue";
	}
	@RequestMapping("admin_propertyValue_update")
	@ResponseBody
	public String update(PropertyValue propertyValue){
		propertyValueService.update(propertyValue);
		return "success";
	}
}
