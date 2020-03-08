package com.tw.progs.HandyEnglish.views.endpoints;

import com.tw.progs.HandyEnglish.models.daos.IDAO;
import com.tw.progs.HandyEnglish.views.BaseView;
import com.tw.progs.HandyEnglish.views.handlers.addRuleHandler;
import com.vaadin.spring.annotation.SpringUI;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

@SpringUI(path = "/HandyEnglish/addRule")
public class addRule extends BaseView {

    @Autowired
    private addRuleHandler addRule;


    @Override
    protected void show(Map<String, String[]> params) {
        addRule.setSqlSession(sqlSF.openSession(false));
        addRule.setLoggedUser(ls, (IDAO)ls.getLoginUserDAO());
        setContent(addRule);
    }

}
