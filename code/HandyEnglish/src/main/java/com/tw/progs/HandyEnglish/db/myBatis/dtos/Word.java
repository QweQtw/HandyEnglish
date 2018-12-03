package com.tw.progs.HandyEnglish.db.myBatis.dtos;

import java.util.Date;

public class Word {
	Integer id;
	Integer category;
	String word;
	String eqiv;
	String defn;
	String exmp;
	Date crtd;
	public Word(Integer id, Integer category, String word, String eqiv, String defn, String exmp, Date crtd) {
		super();
		this.id = id;
		this.category = category;
		this.word = word;
		this.eqiv = eqiv;
		this.defn = defn;
		this.exmp = exmp;
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
	public Date getCrtd() {
		return crtd;
	}
	public void setCrtd(Date crtd) {
		this.crtd = crtd;
	}
}
