package com.tw.progs.HandyEnglish.db.myBatis.dtos;

import java.time.ZonedDateTime;

public class GramaRule {
	private Integer id;
	private Integer grama_categ;
	private String titl;
	private String defn;
	private String exmp;
	private Integer prof;
	private ZonedDateTime crtd;

	public GramaRule(){

	}

	public GramaRule(Integer id, Integer grama_category, String titl, String defn, String exmp, Integer profile, ZonedDateTime crtd) {
		super();
		this.id = id;
		this.grama_categ = grama_category;
		this.titl = titl;
		this.defn = defn;
		this.exmp = exmp;
		this.prof = profile;
		this.crtd = crtd;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getCategory() {
		return grama_categ;
	}
	public void setCategory(Integer grama_category) {
		this.grama_categ = grama_category;
	}
	public String getTitle() {
		return titl;
	}
	public void setTitle(String title) {
		this.titl = title;
	}
	public String getDefn() {
		return defn;
	}
	public void setDefn(String defn) {
		this.defn = defn;
	}
	public String getExmp() {
		return exmp;
	}
	public void setExmp(String exmp) {
		this.exmp = exmp;
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
