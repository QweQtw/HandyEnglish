package com.tw.progs.HandyEnglish.views.endpoints;

import com.tw.progs.HandyEnglish.models.daos.IDAO;
import com.tw.progs.HandyEnglish.views.BaseView;
import com.tw.progs.HandyEnglish.views.gui.addWordHandler;
import com.vaadin.spring.annotation.SpringUI;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

@SpringUI(path = "/HandyEnglih/addWord")
public class addWord extends BaseView {

    @Autowired
    private addWordHandler addWord;


    @Override
    protected void show(Map<String, String[]> params) {
        addWord.setSqlSession(sqlSF.openSession(false));
        addWord.setLoggedUser(ls, (IDAO)ls.getLoginUserDAO());
        setContent(addWord);
    }

}
