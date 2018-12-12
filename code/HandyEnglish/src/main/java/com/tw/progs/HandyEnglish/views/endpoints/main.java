package com.tw.progs.HandyEnglish.views.endpoints;

import com.tw.progs.HandyEnglish.models.daos.IDAO;
import com.tw.progs.HandyEnglish.views.BaseView;
import com.tw.progs.HandyEnglish.views.gui.mainPageHandler;
import com.vaadin.spring.annotation.SpringUI;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

@SpringUI(path = "/HandyEnglih/mainPage")
public class main extends BaseView {

    @Autowired
    private mainPageHandler mph;

    @Override
    protected void show(Map<String, String[]> params) {
        mph.setSqlSession(sqlSF.openSession(false));
        mph.setLoggedUser(ls, (IDAO)ls.getLoginUserDAO());
        setContent(mph);

    }
}
