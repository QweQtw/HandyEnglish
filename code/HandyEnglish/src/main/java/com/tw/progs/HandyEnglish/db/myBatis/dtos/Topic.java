package com.tw.progs.HandyEnglish.db.myBatis.dtos;

import java.time.ZonedDateTime;

public class Topic {
	private Integer id;
	private String topic;
	private Integer prof;
	private ZonedDateTime crtd;

	public Topic(){

	}

	public Topic(Integer id, String topic, Integer profile, ZonedDateTime crtd) {
		super();
		this.id = id;
		this.topic = topic;
		this.prof = profile;
		this.crtd = crtd;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
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
