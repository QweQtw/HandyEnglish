package com.tw.progs.HandyEnglish.views.endpoints;

import com.tw.progs.HandyEnglish.models.daos.IDAO;
import com.tw.progs.HandyEnglish.views.BaseView;
import com.tw.progs.HandyEnglish.views.handlers.showRulesHandler;
import com.vaadin.spring.annotation.SpringUI;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

@SpringUI(path = "/HandyEnglish/showRules")
public class showRules extends BaseView {

    @Autowired
    showRulesHandler sr;

    @Override
    protected void show(Map<String, String[]> params) {
        sr.setSqlSession(sqlSF.openSession(false));
        sr.setLoggedUser(ls, (IDAO)ls.getLoginUserDAO());
        setContent(sr);

    }
}
