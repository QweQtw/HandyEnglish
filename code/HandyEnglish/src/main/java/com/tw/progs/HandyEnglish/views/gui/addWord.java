package com.tw.progs.HandyEnglish.views.gui;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.annotations.DesignRoot;
import com.vaadin.ui.declarative.Design;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Button;

/**
 * !! DO NOT EDIT THIS FILE !!
 * <p>
 * This class is generated by Vaadin Designer and will be overwritten.
 * <p>
 * Please make a subclass with logic and additional interfaces as needed,
 * e.g class LoginView extends LoginDesign implements View { }
 */
@DesignRoot
@AutoGenerated
@SuppressWarnings("serial")
public class addWord extends VerticalLayout {
    protected Panel pnlAddWord;
    protected TextField txtWord;
    protected TextField txtEquiv;
    protected TextField txtDefn;
    protected TextField txtExmpl;
    protected ComboBox<com.tw.progs.HandyEnglish.db.myBatis.dtos.Category> cmbCategory;
    protected ComboBox<com.tw.progs.HandyEnglish.db.myBatis.dtos.Topic> cmbTopic;
    protected Button btnSend;
    protected Button btnReset;
    protected VerticalLayout tabDetails;

    public addWord() {
        Design.read(this);
    }
}
