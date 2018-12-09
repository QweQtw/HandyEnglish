package com.tw.progs.HandyEnglish.views;

import com.tw.progs.HandyEnglish.models.daos.IDAO;
import com.tw.progs.HandyEnglish.views.gui.mainPageHandler;
import com.vaadin.spring.annotation.SpringUI;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

@SpringUI(path = "/HandyEnglih/mainPage")
public class main extends BaseView {

    @Autowired
    private mainPageHandler mph;
    @Autowired
    private SqlSessionFactory sqlSF;

    @Override
    protected void show(Map<String, String[]> params) {
        mph.setSqlSession(sqlSF.openSession(false));
        mph.setLoggedUser(ls, (IDAO)ls.getLoginUserDAO());
//        String[] histNo = (params!=null && params.containsKey("hist"))?params.get("hist"):new String[]{""};
//        selectProfile.setInitParam(histNo[0]);
        setContent(mph);

    }
}
