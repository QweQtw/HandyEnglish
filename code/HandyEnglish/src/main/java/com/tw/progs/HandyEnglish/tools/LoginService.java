package com.tw.progs.HandyEnglish.tools;

import com.tw.progs.HandyEnglish.HandyEnglishApplication;
import com.tw.progs.HandyEnglish.db.myBatis.mappers.ProfilesMapper;
import com.tw.progs.HandyEnglish.models.daos.NullDAO;
import com.tw.progs.HandyEnglish.models.daos.ProfilesDAO;
import com.tw.progs.HandyEnglish.views.BaseView;
import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.*;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by VCLERK on 20.04.2017.
 */
@Component
public class LoginService extends UI{

    public static final String AuthSessionKey = "LogIn";
    private final Logger logger;
    private ProfilesMapper pm;
    private CaptionHolder ch;
    private List<VaadinSession> globalList;
    @Value("${db.super.admin.email}")
    private String superAdminEmail;
    @Value("${app.check.test.location}")
    private String testLocation;

    @Autowired
    private SqlSessionFactory sqlSF;


    @Autowired
    public LoginService(CaptionHolder ch, ProfilesMapper pm, List<VaadinSession> globalList) {
        this.ch = ch;
        this.pm = pm;
        this.globalList = globalList;
        this.logger = HandyEnglishApplication.log;
    }


    @Override
    protected void init(VaadinRequest request) {
    }

    private boolean Authenticated(ProfilesDAO pD, String pass){

        Integer user_id = pD.getId();
        if (pD==null) return false;
        String hash = pD.getPass_hash();
        return hash.equalsIgnoreCase(CipherOps.MD5(pass.trim()));
    }

    public boolean isDebugLocation(Page location){
//        return false;
        if (testLocation.equalsIgnoreCase("true")) {
            if (location == null) return false;
            String tmp = location.getLocation().toString();
            return (tmp.contains("127.0.0.1") || tmp.contains("192.168.29.12") || tmp.contains("localhost"));
        }else{
            return false;
        }
    }

    public void LogIn(String user, String pass){

        VaadinSession vs = VaadinSession.getCurrent();
        vs.getSession().setMaxInactiveInterval(600);
        if (!isDebugLocation(Page.getCurrent())) {
            final ProfilesDAO pD = pm.findUserByeMail(user);
            if (pD!=null && Authenticated(pD, pass)) {
                vs.setAttribute(AuthSessionKey, pD);
                globalList.add(vs);
                logger.info(this.getClass().getName()+"::LogIn -> just logged in, user:"+pD.getId());
            }else {
                vs.setAttribute(AuthSessionKey, new NullDAO());
                logger.info(this.getClass().getName()+"::LogIn -> unsuccessful login attempt for "+ ((pD!=null)?pD.getName():" unknown user!") );
            }
        } else {
            final ProfilesDAO pD = pm.findUserByeMail(superAdminEmail);
            vs.setAttribute(AuthSessionKey, pD);
            globalList.add(vs);
            logger.info(this.getClass().getName()+"::LogIn -> just logged in (Debug Mode), user:"+pD.getId());
        }
    }

    public void LogOut(){
        VaadinSession vs = VaadinSession.getCurrent();
        vs.setAttribute(AuthSessionKey, new NullDAO());
        globalList.remove(vs);
        final Object obj = VaadinSession.getCurrent().getAttribute(AuthSessionKey);
        if ((obj!=null) && (obj instanceof ProfilesDAO)){
            logger.info(this.getClass().getName()+"::LogOut -> just logged out, user:"+((ProfilesDAO)obj).getId());
        }else {
            logger.info(this.getClass().getName() + "::LogOut -> just logged out for unknown user");
        }
        vs.close();
    }

    public boolean isSessionAuthorized(){
        Object obj = VaadinSession.getCurrent().getAttribute(AuthSessionKey);
        return  (obj!=null) && (obj instanceof ProfilesDAO);
    }

    public Object getLoginUserDAO(){
        return VaadinSession.getCurrent().getAttribute(AuthSessionKey);
    }

    public void showLoginWindow(String uriLocation, boolean RedirectOnFailure, BaseView parent){
        Window wnd = new Window(ch.getCaption("zaloguj się"));
        wnd.setWidth("320px");
        wnd.setHeight("320px");
        wnd.setModal(true);
        wnd.setClosable(false);
        wnd.setResizable(false);
        Panel pnlLogin = new Panel(ch.getCaption("logowanie"));
        pnlLogin.setIcon(VaadinIcons.DIPLOMA);
        VerticalLayout content = new VerticalLayout();
        TextField user = new TextField(ch.getCaption("e-mail"));
        PasswordField pass = new PasswordField(ch.getCaption("hasło"));
        user.setWidth("100%");
        user.setRequiredIndicatorVisible(true);
        user.focus();
        pass.setWidth("100%");
        pass.setRequiredIndicatorVisible(true);
        content.addComponents(user, pass);
        Button saveButton = new Button("OK", (click) -> {
            LogIn(user.getValue(), pass.getValue());
            wnd.close();
            if (!isSessionAuthorized() && RedirectOnFailure ){
                String tmp = uriLocation.substring(0, uriLocation.lastIndexOf("/") + 1);
                Page.getCurrent().open(tmp+"loginError", "_self");
            }
            if (isSessionAuthorized()){
                parent.refresh();
            }
        });
        saveButton.setStyleName("primary");
        saveButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        saveButton.setWidth("100%");
//        pass.addShortcutListener(new ShortcutListener("OK", ShortcutAction.KeyCode.ENTER, null) {
//            @Override
//            public void handleAction(Object sender, Object target) {
//                saveButton.click();
//            }
//        });
//        wnd.addCloseListener(e->{
//            if ()
//        })
        content.addComponentsAndExpand(user, pass, saveButton);
        pnlLogin.setContent(content);
//        content.addComponentsAndExpand(saveButton);
        wnd.setContent(pnlLogin);
        UI.getCurrent().addWindow(wnd);
    }

}
