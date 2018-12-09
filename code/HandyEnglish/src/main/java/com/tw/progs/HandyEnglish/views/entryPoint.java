package com.tw.progs.HandyEnglish.views;

import com.tw.progs.HandyEnglish.models.daos.IDAO;
import com.tw.progs.HandyEnglish.views.gui.selectProfileHandler;
import com.vaadin.spring.annotation.SpringUI;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

@SpringUI(path = "/HandyEnglih/start")
public class entryPoint extends BaseView{

    @Autowired
    private selectProfileHandler selectProfile;
    @Autowired
    private SqlSessionFactory sqlSF;


    @Override
    protected void show(Map<String, String[]> params) {
        selectProfile.setSqlSession(sqlSF.openSession(false));
        selectProfile.setLoggedUser(ls, (IDAO)ls.getLoginUserDAO());
//        String[] histNo = (params!=null && params.containsKey("hist"))?params.get("hist"):new String[]{""};
//        selectProfile.setInitParam(histNo[0]);
        setContent(selectProfile);
    }

}
