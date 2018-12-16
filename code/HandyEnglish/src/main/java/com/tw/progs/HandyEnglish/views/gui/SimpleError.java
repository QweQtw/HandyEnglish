package com.tw.progs.HandyEnglish.views.gui;

import com.tw.progs.HandyEnglish.tools.CaptionHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by VCLERK on 25.04.2017.
 */
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class SimpleError extends SimpleErrorForm {

//    @Autowired
//    private CaptionHolder ch;

    @Autowired
    public SimpleError(CaptionHolder ch) {
        pnlError.setCaption(ch.getCaption("coś poszło nie tak!"));
        lblMessage.setCaption(ch.getCaption("wystąpił błąd:")+" "+ch.getCaption("nieznany błąd"));
        lblMessage.setValue(ch.getCaption("nieoczekiwana operacja"));
    }


//    public SimpleError() {
//        pnlError.setCaption("coś poszło nie tak!");
//        lblMessage.setCaption("wystąpił błąd: nieznany błąd");
//        lblMessage.setValue("nieoczekiwana operacja");
//    }
}
