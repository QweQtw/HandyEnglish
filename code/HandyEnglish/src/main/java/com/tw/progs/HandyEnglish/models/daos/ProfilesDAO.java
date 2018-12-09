package com.tw.progs.HandyEnglish.models.daos;

import java.time.ZonedDateTime;

/**
 * Created by VCLERK on 22.03.2017.
 */
public class ProfilesDAO implements IDAO{

    public ProfilesDAO() {

    }
    public ProfilesDAO(String name, String pass_hash) {
        this.name = name;
        this.pass_hash = pass_hash;
    }

    private Integer id;
    private String name;
    private String pass_hash;
    private ZonedDateTime uptd;
    private ZonedDateTime crtd;

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
        this.name = name;
    }

    public String getPass_hash() {
        return pass_hash;
    }

    public void setPass_hash(String pass_hash) {
        this.pass_hash = pass_hash;
    }

    public ZonedDateTime getUptd() {
        return uptd;
    }

    public void setUptd(ZonedDateTime uptd) {
        this.uptd = uptd;
    }

    public ZonedDateTime geCrtd() {
        return crtd;
    }

    public void setCrtd(ZonedDateTime crtd) {
        this.crtd = crtd;
    }

}
