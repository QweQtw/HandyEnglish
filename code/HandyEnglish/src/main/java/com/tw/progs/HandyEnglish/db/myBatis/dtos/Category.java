package com.tw.progs.HandyEnglish.db.myBatis.dtos;

import java.util.Date;

public class Category {
	Integer id;
	String category;
	Date crtd;
	public Category(Integer id, String category, Date crtd) {
		super();
		this.id = id;
		this.category = category;
		this.crtd = crtd;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public Date getCrtd() {
		return crtd;
	}
	public void setCrtd(Date crtd) {
		this.crtd = crtd;
	}
}
