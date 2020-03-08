package com.tw.progs.HandyEnglish.models.daos;

import com.tw.progs.HandyEnglish.db.myBatis.dtos.Profile;
import com.tw.progs.HandyEnglish.db.myBatis.mappers.ProfilesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by VCLERK on 22.03.2017.
 */
@Component
public class ProfilesDAO {


    private ProfilesMapper pm;
    @Value("${db.super.admin.email}")
    private String defUserName;

    @Autowired
    public ProfilesDAO(ProfilesMapper pm){
        this.pm = pm;
    }

    public void saveProfile(Profile prof){
        pm.insertUser(prof);
    }

    public Integer getDefUserAccountId(){
        return pm.findUserByeMail(defUserName).getId();
    }

}
