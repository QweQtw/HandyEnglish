package com.tw.progs.HandyEnglish.db.myBatis.dtos;

import java.time.ZonedDateTime;

public class GramaCategory {
	private Integer id;
	private String grama_categ;
	private Integer prof;
	private ZonedDateTime crtd;

	public GramaCategory(){

	}

	public GramaCategory(Integer id, String grama_category, Integer profile, ZonedDateTime crtd) {
		super();
		this.id = id;
		this.grama_categ = grama_category;
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
		return grama_categ;
	}
	public void setCategory(String grama_category) {
		this.grama_categ = grama_category;
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
