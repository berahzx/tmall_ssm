package com.how2java.tmall.controller;
 
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.how2java.tmall.page.Page;
import com.how2java.tmall.pojo.Category;
import com.how2java.tmall.service.CategoryService;
import com.how2java.tmall.upload.UploadImageFile;
import com.how2java.utils.ImageUtil;
 
@Controller
@RequestMapping("")
public class CategoryController {
    @Autowired
    CategoryService categoryService;
  
    @RequestMapping("admin_category_list")
    public String list(Model model,Page page){
    	PageHelper.offsetPage(page.getStart(), page.getCount());
        List<Category> cs= categoryService.list();
    	int total=(int) new PageInfo<>(cs).getTotal();
    	page.setTotal(total);
        model.addAttribute("cs", cs);
        model.addAttribute("page", page);
        return "admin/listCategory";
    }
    @RequestMapping("admin_category_add")
    public String add(Category category,UploadImageFile image,HttpSession session) throws IllegalStateException, IOException{
    	categoryService.add(category);
    	//首先通过session获取到真实的路径
    	String realPath=session.getServletContext().getRealPath("img/category");
    	//创建一个文件
    	File file=new File(realPath, category.getId()+".jpg");
    	//判断父目录是否存在
    	if(!file.getParentFile().exists()){
    		file.getParentFile().mkdirs();
    	}
    	image.getImage().transferTo(file);
    	BufferedImage bufferedImage=ImageUtil.change2jpg(file);
    	ImageIO.write(bufferedImage, "jpg", file);
    	return "redirect:/admin_category_list";
    }
}