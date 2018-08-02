package com.how2java.tmall.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.how2java.tmall.mapper.ProductImageMapper;
import com.how2java.tmall.pojo.Product;
import com.how2java.tmall.pojo.ProductImage;
import com.how2java.tmall.service.CategoryService;
import com.how2java.tmall.service.ProductImageService;
import com.how2java.tmall.service.ProductService;
import com.how2java.tmall.upload.UploadImageFile;
import com.how2java.utils.ImageUtil;

@Controller
@RequestMapping("")
public class ProductImageController {
	@Autowired
	ProductImageService productImageService;
	@Autowired
	ProductService productService;
	@Autowired
	CategoryService categoryService;
	@RequestMapping("admin_productImage_list")
	public String list(int pid,Model model){
		Product product=productService.get(pid);
		product.setCategory(categoryService.get(product.getCid()));
		//通过pid获取到对应的productImage
		List<ProductImage> productImages=productImageService.list(product);
		model.addAttribute("pisSingle", product.getSingleProductImage());
		model.addAttribute("pisDetail", product.getDetailProductImage());
		model.addAttribute("p", product);
		return "admin/listProductImage";
	}
	@RequestMapping("admin_productImage_add")
	public String add(ProductImage productImage,UploadImageFile imageFile,HttpSession httpSession) throws IllegalStateException, IOException{
		productImageService.add(productImage);
		String realPath="";
		String middlePath="";
		String smallPath="";
		//判断传过来的类型
		String type=productImage.getType();
		if(type.equals(ProductImageService.detailType)){
			realPath=httpSession.getServletContext().getRealPath("img/productDetail");
		}else{
			realPath=httpSession.getServletContext().getRealPath("img/productSingle");
		}
		File file=new File(realPath,productImage.getId()+".jpg");
		if(!file.getParentFile().exists()){
			file.getParentFile().mkdirs();
		}
		loadImage(file,imageFile);
		if(type.equals(ProductImageService.singleType)){
			middlePath=httpSession.getServletContext().getRealPath("img/productSingle_middle");
			smallPath=httpSession.getServletContext().getRealPath("img/productSingle_small");
			ImageUtil.resizeImage(file, 56, 56, new File(smallPath,productImage.getId()+".jpg"));
			ImageUtil.resizeImage(file, 217, 190, new File(middlePath,productImage.getId()+".jpg"));
		}
		
		return "redirect:/admin_productImage_list?pid="+productImage.getPid();
	}
	@RequestMapping("admin_productImage_delete")
	public String delete(int id,HttpSession session){
		ProductImage productImage=productImageService.get(id);
		productImageService.delete(id);
		String realPath="";
		String middlePath="";
		String smallPath="";
		//判断要删除的类型图片
		if(productImage.getType().equals(ProductImageService.singleType)){
			realPath=session.getServletContext().getRealPath("img/productSingle");
			middlePath=session.getServletContext().getRealPath("img/productSingle_middle");
			smallPath=session.getServletContext().getRealPath("img/productSingle_small");
			new File(middlePath,productImage.getId()+".jpg").deleteOnExit();
			new File(smallPath,productImage.getId()+".jpg").deleteOnExit();
		}else{
			realPath=session.getServletContext().getRealPath("img/productDetail");
		}	
		new File(realPath,productImage.getId()+".jpg").deleteOnExit();
		return "redirect:/admin_productImage_list?pid="+productImage.getPid();
	}
	public void loadImage(File file,UploadImageFile imageFile) throws IllegalStateException, IOException{
		imageFile.getImage().transferTo(file);
		BufferedImage bufferedImage=ImageUtil.change2jpg(file);
		ImageIO.write(bufferedImage, "jpg", file);
	}
}
