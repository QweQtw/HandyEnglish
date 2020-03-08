package com.tw.progs.HandyEnglish.views.handlers;

import com.tw.progs.HandyEnglish.db.myBatis.dtos.GramaCategory;
import com.tw.progs.HandyEnglish.db.myBatis.dtos.GramaRule;
import com.tw.progs.HandyEnglish.db.myBatis.dtos.Profile;
import com.tw.progs.HandyEnglish.db.myBatis.mappers.GramaCategoriesMapper;
import com.tw.progs.HandyEnglish.db.myBatis.mappers.GramaRulesMapper;
import com.tw.progs.HandyEnglish.models.daos.IDAO;
import com.tw.progs.HandyEnglish.tools.CaptionHolder;
import com.tw.progs.HandyEnglish.tools.LoginService;
import com.tw.progs.HandyEnglish.views.gui.showRules;
import com.vaadin.event.ShortcutAction;
import com.vaadin.event.ShortcutListener;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinSession;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class showRulesHandler extends showRules {

    private GramaRulesMapper grm;
    private GramaCategoriesMapper gcm;
    private CaptionHolder ch;
    private SqlSession sqlSession;
    private String uriLocation;
    private LoginService loginService;
    private Profile loggedUser;
    private Integer profileId;

    private List<GramaRule> rules;
    private HashMap<Integer, String> gramaRuleCategoriesCache;
//    private String wordtxtId;
//    private String eqivtxtId;

    @Autowired
    public showRulesHandler(GramaRulesMapper grm, GramaCategoriesMapper gcm, CaptionHolder ch) {
        this.grm = grm;
        this.gcm = gcm;
        this.ch = ch;
    }

    @PostConstruct
    public void init(){

        Object obj = VaadinSession.getCurrent().getAttribute(LoginService.AuthSessionKey);
        String logedAs = "";
        if (obj instanceof Profile){
            loggedUser = (Profile)obj;
            logedAs = loggedUser.getName();
            this.profileId = loggedUser.getId();
            setDataProvider();
        }else{
//            tabDetails.addComponentsAndExpand(new LoginError(ch));
        }

        setListeners();
        //redirection("mainPage");

        setCaptions();
    }

    private void setCaptions() {

        pnlRules.setCaption(ch.getCaption("Twoje zasady gramatyczne")+":");
        txtCategory.setCaption(ch.getCaption("kategoria")+":");
        txtTitle.setCaption(ch.getCaption("tytuł")+":");
        txtaDefinition.setCaption(ch.getCaption("definicja")+":");
        txtaExample.setCaption(ch.getCaption("przykład")+":");
    }

    private void setListeners() {
        lstRules.addValueChangeListener(e->{
//            if (e.isUserOriginated()) {
                populateData(e.getValue().stream().findFirst().get());
//            }
        });


        pnlRules.addShortcutListener(new ShortcutListener("Enter", ShortcutAction.KeyCode.ENTER, null) {
            @Override
            public void handleAction(Object sender, Object target) {
                populateData(lstRules.getSelectedItems().stream().findFirst().get());
            }
        });

    }

    private void populateData(GramaRule rule) {
        txtTitle.setValue(rule.getTitle());
        String gramaRuleCategory = getgCatName(rule.getCategory());
        txtCategory.setValue(gramaRuleCategory);
        txtaDefinition.setValue(rule.getDefn());
        txtaExample.setValue(rule.getExmp());
    }

    private String getgCatName(Integer category) {
        if (gramaRuleCategoriesCache.containsKey(category)){
            return gramaRuleCategoriesCache.get(category);
        }else{
            GramaCategory gc = gcm.findCategoryById(category);
            gramaRuleCategoriesCache.put(category, gc.getCategory());
            return gc.getCategory();
        }
    }

    private void setDataProvider() {

        gramaRuleCategoriesCache = new HashMap<Integer, String>();

        List<GramaRule> rules = grm.getAllGramaRulesForUser(profileId);
        lstRules.setItems(rules);
        lstRules.setItemCaptionGenerator(GramaRule::getTitle);

        if (rules.size()>0){
            lstRules.select(rules.get(0));
            populateData(rules.get(0));
        }
    }

    public void setSqlSession(SqlSession openSession) {
        this.sqlSession = openSession;
        this.uriLocation = Page.getCurrent().getLocation().toString();
    }

    public void setLoggedUser(LoginService ls, IDAO profile) {
        this.loginService = ls;
        if (profile!=null ) {
            this.loggedUser = (Profile) profile;
//            setDataProvider();
//            setListeners();
            afterFullInitialization();
        }else{
//            tabDetails.addComponent(new LoginError(ch));
        }
    }

    private void afterFullInitialization() {
    }

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
