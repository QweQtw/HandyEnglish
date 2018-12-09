package com.tw.progs.HandyEnglish.views.gui;

import com.tw.progs.HandyEnglish.tools.CaptionHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class LoginError extends LoginErrorForm {

    public LoginError(@Autowired CaptionHolder ch, String err_msg) {
        pnlLoginError.setCaption(err_msg);
        lblMessage.setValue(ch.getCaption("sorry, dalej nie można"));
    }

    public LoginError(@Autowired CaptionHolder ch) {
        this(ch, ch.getCaption("brak dostępu"));
    }
}
