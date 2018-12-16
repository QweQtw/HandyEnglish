package com.tw.progs.HandyEnglish.views.endpoints;

import com.tw.progs.HandyEnglish.db.myBatis.dtos.Profile;
import com.tw.progs.HandyEnglish.views.BaseView;
import com.tw.progs.HandyEnglish.views.handlers.selectProfileHandler;
import com.vaadin.spring.annotation.SpringUI;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

@SpringUI(path = "/HandyEnglish/start")
public class entryPoint extends BaseView {

    @Autowired
    private selectProfileHandler selectProfile;


    @Override
    protected void show(Map<String, String[]> params) {
        selectProfile.setSqlSession(sqlSF.openSession(false));
        selectProfile.setLoggedUser(ls, (Profile)ls.getLoginUserDAO());
        setContent(selectProfile);
    }

}
