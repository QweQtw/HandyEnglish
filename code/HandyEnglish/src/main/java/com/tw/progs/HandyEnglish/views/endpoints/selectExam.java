package com.tw.progs.HandyEnglish.views.endpoints;

import com.tw.progs.HandyEnglish.models.daos.IDAO;
import com.tw.progs.HandyEnglish.views.BaseView;
import com.tw.progs.HandyEnglish.views.handlers.selectExamineHandler;
import com.vaadin.spring.annotation.SpringUI;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

@SpringUI(path = "/HandyEnglish/Exam")
public class selectExam extends BaseView {
    @Autowired
    selectExamineHandler se;

    @Override
    protected void show(Map<String, String[]> params) {
        se.setSqlSession(sqlSF.openSession(false));
        se.setLoggedUser(ls, (IDAO)ls.getLoginUserDAO());
        setContent(se);

    }
}
