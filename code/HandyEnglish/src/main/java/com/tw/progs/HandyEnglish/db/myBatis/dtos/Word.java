package com.tw.progs.HandyEnglish.db.myBatis.dtos;

import java.time.ZonedDateTime;

public class Word {
	private Integer id;
	private Integer category;
	private String word;
	private String eqiv;
	private String defn;
	private String exmp;
	private Integer prof;
	private ZonedDateTime crtd;

	public Word(){

	}

	public Word(Integer id, Integer category, String word, String eqiv, String defn, String exmp, Integer profile, ZonedDateTime crtd) {
		super();
		this.id = id;
		this.category = category;
		this.word = word;
		this.eqiv = eqiv;
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
		return category;
	}
	public void setCategory(Integer category) {
		this.category = category;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public String getEqiv() {
		return eqiv;
	}
	public void setEqiv(String eqiv) {
		this.eqiv = eqiv;
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
