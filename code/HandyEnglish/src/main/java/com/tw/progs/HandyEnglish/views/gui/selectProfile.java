package com.tw.progs.HandyEnglish.views.gui;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.annotations.DesignRoot;
import com.vaadin.ui.declarative.Design;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.CheckBox;
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
public class selectProfile extends VerticalLayout {
    protected Panel pnlLogin;
    protected TextField txtLoginEmail;
    protected PasswordField pssLoginPass;
    protected CheckBox chbRemember;
    protected Button btnLogin;
    protected Button btnResetPass;
    protected Panel pnlRegister;
    protected TextField txtRegisterEmail;
    protected PasswordField pssRegisterPass;
    protected PasswordField pssRegisterRepeat;
    protected Button btnRegister;
    protected VerticalLayout tabDetails;

    public selectProfile() {
        Design.read(this);
    }
}
