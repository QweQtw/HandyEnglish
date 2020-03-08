package com.tw.progs.HandyEnglish.views;

import com.tw.progs.HandyEnglish.tools.LoginService;
import com.vaadin.server.DefaultErrorHandler;
import com.vaadin.server.ErrorHandler;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;

import java.util.Map;

@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public abstract class BaseView  extends UI{

    @Autowired
    protected LoginService ls;
    @Autowired
    protected SqlSessionFactory sqlSF;
    @Value("${app.start.path}")
    private String mainPath;

    protected String[] themes = {"valo", "reindeer", "runo", "chameleon", "dark"};

    @Override
    protected void init(VaadinRequest request){

        UI.getCurrent().setErrorHandler((ErrorHandler) event -> {
            Page.getCurrent().setLocation("error");
            // Do the default error handling (optional)
            DefaultErrorHandler.doDefault(event);
        });

        if (ls.isSessionAuthorized()){
            show(request.getParameterMap());
        }else{
            show(null);
            if (!Page.getCurrent().getLocation().getPath().contains(mainPath))
                ls.showLoginWindow(Page.getCurrent().getLocation().toString(), true, this);
        }
    }

    protected abstract void show(Map<String, String[]> params);
    public void refresh(){show(null);}
}
