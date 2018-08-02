package com.how2java.tmall.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Product {
    private Integer id;

    private String name;

    private String subTitle;

    private Float orignalPrice;

    private Float promotePrice;

    private Integer stock;

    private Integer cid;

    private Date createDate;
    private ProductImage firstProductImage;
    private Category category;
    private Integer reviewCount;
    private Integer saleCount;
    
    public Integer getReviewCount() {
		return reviewCount;
	}

	public void setReviewCount(Integer reviewCount) {
		this.reviewCount = reviewCount;
	}

	public Integer getSaleCount() {
		return saleCount;
	}

	public void setSaleCount(Integer saleCount) {
		this.saleCount = saleCount;
	}

	private List<ProductImage> singleProductImage=new ArrayList<ProductImage>();
    private List<ProductImage> detailProductImage=new ArrayList<ProductImage>();
    
    public ProductImage getFirstProductImage() {
		return firstProductImage;
	}

	public void setFirstProductImage(ProductImage firstProductImage) {
		this.firstProductImage = firstProductImage;
	}

	public List<ProductImage> getSingleProductImage() {
		return singleProductImage;
	}

	public void setSingleProductImage(List<ProductImage> singleProductImage) {
		this.singleProductImage = singleProductImage;
	}

	public List<ProductImage> getDetailProductImage() {
		return detailProductImage;
	}

	public void setDetailProductImage(List<ProductImage> detailProductImage) {
		this.detailProductImage = detailProductImage;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle == null ? null : subTitle.trim();
    }

    public Float getOrignalPrice() {
        return orignalPrice;
    }

    public void setOrignalPrice(Float orignalPrice) {
        this.orignalPrice = orignalPrice;
    }

    public Float getPromotePrice() {
        return promotePrice;
    }

    public void setPromotePrice(Float promotePrice) {
        this.promotePrice = promotePrice;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}