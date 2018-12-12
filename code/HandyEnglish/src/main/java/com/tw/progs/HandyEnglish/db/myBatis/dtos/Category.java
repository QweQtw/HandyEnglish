package com.tw.progs.HandyEnglish.db.myBatis.dtos;

import java.time.ZonedDateTime;

public class Category {
	private Integer id;
	private String category;
	private Integer prof;
	private ZonedDateTime crtd;

	public Category(){

	}

	public Category(Integer id, String category, Integer profile, ZonedDateTime crtd) {
		super();
		this.id = id;
		this.category = category;
		this.prof = profile;
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
	public Integer getProf() {
		return prof;
	}

	public void setProf(Integer prof) {
		this.prof = prof;
	}
	public ZonedDateTime getCrtd() {
		return crtd;
	}
	public void setCrtd(ZonedDateTime crtd) {
		this.crtd = crtd;
	}

}
