package com.tw.progs.HandyEnglish.views.endpoints;

import com.tw.progs.HandyEnglish.views.BaseView;
import com.tw.progs.HandyEnglish.views.gui.SimpleError;
import com.vaadin.spring.annotation.SpringUI;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

@SpringUI(path = "/error")
public class error  extends BaseView {

    @Autowired
    private SimpleError simpleError;


    @Override
    protected void show(Map<String, String[]> params) {
        ls.LogOut();
        setContent(simpleError);
    }}
