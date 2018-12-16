package com.tw.progs.HandyEnglish.views.handlers;

import com.tw.progs.HandyEnglish.db.myBatis.dtos.Profile;
import com.tw.progs.HandyEnglish.db.myBatis.mappers.CategoriesMapper;
import com.tw.progs.HandyEnglish.db.myBatis.mappers.TopicsMapper;
import com.tw.progs.HandyEnglish.db.myBatis.mappers.WordsMapper;
import com.tw.progs.HandyEnglish.models.daos.IDAO;
import com.tw.progs.HandyEnglish.tools.CaptionHolder;
import com.tw.progs.HandyEnglish.tools.LoginService;
import com.tw.progs.HandyEnglish.views.gui.selectExamine;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinSession;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class selectExamineHandler extends selectExamine {

    private SqlSession openSession;
    private LoginService ls;
    private Profile loggedUser;
    private String uriLocation;
    private CategoriesMapper cm;
    private TopicsMapper tm;
    private WordsMapper wm;
    private CaptionHolder ch;
    private tabWordExamHandler tabWordExam;

    @Autowired
    public selectExamineHandler(CategoriesMapper cm, TopicsMapper tm, WordsMapper wm, CaptionHolder ch){

        this.cm = cm;
        this.tm = tm;
        this.wm = wm;
        this.ch = ch;
    }

    public void setSqlSession(SqlSession openSession) {
        this.openSession = openSession;
        this.uriLocation = Page.getCurrent().getLocation().toString();
    }

    public void setLoggedUser(LoginService ls, IDAO loggedUser) {
        this.ls = ls;
        if (loggedUser!=null && (loggedUser instanceof Profile)) {
            this.loggedUser = (Profile) loggedUser;
            tabWordExam = new tabWordExamHandler(cm, tm, wm, ch, this.loggedUser, uriLocation);
            setDataProvider();
//            setListeners();
//            afterFullInitialization();
        }
    }

    private void setDataProvider() {
        tabExams.addTab(tabWordExam);
    }

//    private void setListeners() {
//        btn.addClickListener(event->{
//            redirection("addWord");
//        });
//
//        btnExam.addClickListener(event->{
//            redirection("Exam");
//        });
//
//        btnShowDict.addClickListener(e->{
//            redirection("showDictionary");
//        });
//        btnLogout.addClickListener(event->{
//            ls.LogOut();
//            redirection("start");
//        });
//    }

    private void redirection(String linkName) {
        if (linkName==null || linkName.isEmpty()){
            Page.getCurrent().open(uriLocation, "_self");
        }else {
            String tmp = uriLocation.substring(0, uriLocation.lastIndexOf("/") + 1);
            final ExternalResource redirection = new ExternalResource(tmp + linkName);
            Page.getCurrent().open(redirection.getURL(), "_self");
        }
    }

}
