package com.tw.progs.HandyEnglish.views.endpoints;

import com.tw.progs.HandyEnglish.models.daos.IDAO;
import com.tw.progs.HandyEnglish.views.BaseView;
import com.tw.progs.HandyEnglish.views.handlers.showDictionaryHandler;
import com.vaadin.spring.annotation.SpringUI;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

@SpringUI(path = "/HandyEnglish/showDictionary")
public class showDictionary extends BaseView {

    @Autowired
    showDictionaryHandler sd;

    @Override
    protected void show(Map<String, String[]> params) {
        sd.setSqlSession(sqlSF.openSession(false));
        sd.setLoggedUser(ls, (IDAO)ls.getLoginUserDAO());
        setContent(sd);

    }
}
