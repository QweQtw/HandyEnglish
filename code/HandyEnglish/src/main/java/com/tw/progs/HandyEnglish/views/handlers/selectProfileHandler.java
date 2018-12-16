package com.tw.progs.HandyEnglish.views.handlers;

import com.tw.progs.HandyEnglish.HandyEnglishApplication;
import com.tw.progs.HandyEnglish.db.myBatis.dtos.Profile;
import com.tw.progs.HandyEnglish.db.myBatis.mappers.ProfilesMapper;
import com.tw.progs.HandyEnglish.models.daos.IDAO;
import com.tw.progs.HandyEnglish.models.daos.ProfilesDAO;
import com.tw.progs.HandyEnglish.tools.*;
import com.tw.progs.HandyEnglish.views.gui.LoginError;
import com.tw.progs.HandyEnglish.views.gui.selectProfile;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinSession;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class selectProfileHandler extends selectProfile {
    private final ProfilesMapper pm;
    private final Logger logger;
    private ProfilesDAO pd;
    private eMailAddressValidator ev;
    private passwordDataValidator pv;
    private SqlSession sqlSession = null;
    private String uriLocation;
    private LoginService loginService;
    private Profile loggedUser;
    private final CaptionHolder ch;


    @Autowired
    public selectProfileHandler(CaptionHolder ch, ProfilesMapper pm, ProfilesDAO pd, eMailAddressValidator eV, passwordDataValidator pV) {
        this.ch = ch;
        this.pm = pm;
        this.pd = pd;
        this.ev = eV;
        this.pv = pV;
        this.logger = HandyEnglishApplication.log;
    }

    @PostConstruct
    public void init(){

        Object obj = VaadinSession.getCurrent().getAttribute(LoginService.AuthSessionKey);
        String logedAs = "";
        if (obj instanceof Profile){
            loggedUser = (Profile)obj;
            logedAs = loggedUser.getName();
            setDataProvider();
        }else{
//            tabDetails.addComponentsAndExpand(new LoginError(ch));
        }

        setListeners();
        //redirection("mainPage");

    }

    public void setSqlSession(SqlSession openSession) {
        this.sqlSession = sqlSession;
        this.uriLocation = Page.getCurrent().getLocation().toString();
    }

    public void setLoggedUser(LoginService ls, Profile loggedUser) {
        this.loginService = ls;
        if (loggedUser!=null ) {
            this.loggedUser = (Profile) loggedUser;
            setDataProvider();
            setListeners();
            afterFullInitialization();
        }else{
//            tabDetails.addComponent(new LoginError(ch));
        }
    }

    private void afterFullInitialization() {

    }

    private void setListeners() {

        btnLogin.addClickListener(event->{ String email = txtLoginEmail.getValue().trim();
            String pass = pssLoginPass.getValue().trim();

            Profile profile = pm.findUserByeMail(email);
            if (profile!=null && profile.getPass_hash().equalsIgnoreCase(CipherOps.MD5(pass))) {
                loginService.LogIn(email,pass);
                checkRemeberMe();
                redirection("mainPage");
            }else{
                reset(true, "nieprawidłowe dane logowania");
            }
        });

        btnRegister.addClickListener(event->{
            String email = txtRegisterEmail.getValue().trim();
            String pass = pssRegisterPass.getValue().trim();
            String rept = pssRegisterRepeat.getValue().trim();


            if (pm.findUserByeMail(email)!=null){
                reset(true, "Taki Użytkownik już istnieje!");
                return;
            }
            if (ev.isValidEmailAddress(email)){
                if(pv.isValidPassword(pass) && pass.equalsIgnoreCase(rept)) {
                    Profile profile = new Profile(email, CipherOps.MD5(pass));
                    try {
                        pd.saveProfile(profile);
                        //pm.insertUser(profile);
                    } catch (Exception ex) {
                        reset(true, ch.getCaption("Nie można zarejestrować użytkownika! (Spróbuj później)"));
                        logger.error("Registartion user problem: "+ex.getLocalizedMessage());
                    }
                    loginService.LogIn(email,pass);
                    redirection("mainPage");
                }else{
                    reset(true, ch.getCaption("Upewnij się że hasło składa się ..."));
                }
            }else{
                reset(true, ch.getCaption("Niepoprawne dane rejestracyjne!"));
            }
        });

        btnResetPass.addClickListener(event->{
            String email = txtLoginEmail.getValue().trim();

            if (checkEmailExists(email))
                sendResetLink();
        });

    }

    private void reset(boolean login_error, String register_err_mesg) {
        txtLoginEmail.setValue("");
        pssLoginPass.setValue("");

        txtRegisterEmail.setValue("");
        pssRegisterPass.setValue("");
        pssRegisterRepeat.setValue("");

        if(login_error) {
            tabDetails.removeAllComponents();
            tabDetails.addComponent(new LoginError(ch, register_err_mesg));
        }
    }

    private void checkRemeberMe() {

    }

    private void sendResetLink() {

    }

    private boolean checkEmailExists(String email) {
        Profile profile = pm.findUserByeMail(email.trim());
        return profile!=null && profile.getId()>0;
    }

    private void setDataProvider() {

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
