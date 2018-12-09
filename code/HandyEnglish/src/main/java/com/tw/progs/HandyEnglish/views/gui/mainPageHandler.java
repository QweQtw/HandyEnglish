package com.tw.progs.HandyEnglish.views.gui;

import com.tw.progs.HandyEnglish.models.daos.IDAO;
import com.tw.progs.HandyEnglish.models.daos.ProfilesDAO;
import com.tw.progs.HandyEnglish.tools.LoginService;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinSession;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class mainPageHandler extends mainPage {
    private SqlSession openSession;
    private LoginService ls;
    private ProfilesDAO loggedUser;
    private String uriLocation;

    @PostConstruct
    public void init(){

        Object obj = VaadinSession.getCurrent().getAttribute(LoginService.AuthSessionKey);
        String logedAs = "";
        if (obj instanceof ProfilesDAO){
            ProfilesDAO loggedUser = (ProfilesDAO) obj;
            logedAs = loggedUser.getName();
            setDataProvider();
//            lblLogedAs.setValue(ch.getCaption("zalogowany:")+logedAs);
        }else{
//            tabDocs.addComponentsAndExpand(new SimpleError(ch));
//            tabHist.addComponentsAndExpand(new LoginError(ch));
//            tabDetails.addComponentsAndExpand(new LoginError(ch));
        }

        setListeners();
        //redirection("mainPage");

    }

    private void setDataProvider() {
    }

    public void setSqlSession(SqlSession openSession) {
        this.openSession = openSession;
        this.uriLocation = Page.getCurrent().getLocation().toString();
    }

    public void setLoggedUser(LoginService ls, IDAO loggedUser) {
        this.ls = ls;
        if (loggedUser!=null && (loggedUser instanceof ProfilesDAO)) {
            this.loggedUser = (ProfilesDAO) loggedUser;
            //lblLogedAs.setValue(ch.getCaption("zalogowany:")+loggedUser.getName());
            setDataProvider();
            setListeners();
            //afterFullInitialization();
        }else{
//            tabDocs.addComponent(new SimpleError(ch));
//            tabHist.addComponent(new LoginError(ch));
//            tabDetails.addComponent(new LoginError(ch));
        }

    }

    private void setListeners() {
        btnLogout.addClickListener(event->{
            ls.LogOut();
            redirection("start");
        });
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
