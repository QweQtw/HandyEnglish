package com.tw.progs.HandyEnglish.views.handlers;

import com.tw.progs.HandyEnglish.db.myBatis.dtos.GramaCategory;
import com.tw.progs.HandyEnglish.db.myBatis.dtos.GramaRule;
import com.tw.progs.HandyEnglish.db.myBatis.dtos.Profile;
import com.tw.progs.HandyEnglish.db.myBatis.mappers.GramaCategoriesMapper;
import com.tw.progs.HandyEnglish.db.myBatis.mappers.GramaRulesMapper;
import com.tw.progs.HandyEnglish.models.daos.GramaCategoryDAO;
import com.tw.progs.HandyEnglish.models.daos.IDAO;
import com.tw.progs.HandyEnglish.tools.CaptionHolder;
import com.tw.progs.HandyEnglish.tools.LoginService;
import com.tw.progs.HandyEnglish.views.gui.LoginError;
import com.tw.progs.HandyEnglish.views.gui.addRule;
import com.vaadin.event.ShortcutAction;
import com.vaadin.event.ShortcutListener;
import com.vaadin.server.Page;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class addRuleHandler extends addRule {


    private SqlSession sqlSession;
    private String uriLocation;
    private LoginService loginService;
    private Profile loggedUser;
    private GramaCategoriesMapper gcm;
    private GramaRulesMapper grm;
    private GramaCategoryDAO gcd;
    private CaptionHolder ch;
    private Integer profileId;

    private List<GramaCategory> cmbCatList;

    @Autowired
    public addRuleHandler(GramaCategoriesMapper gcm, GramaRulesMapper grm, GramaCategoryDAO gcd, CaptionHolder ch){
        this.gcm = gcm;
        this.grm = grm;
        this.gcd = gcd;
        this.ch = ch;
    }

    public void setSqlSession(SqlSession openSession) {
        this.sqlSession = sqlSession;
        this.uriLocation = Page.getCurrent().getLocation().toString();
    }

    public void setLoggedUser(LoginService ls, IDAO loggedUser) {
        this.loginService = ls;
        if (loggedUser!=null && (loggedUser instanceof Profile)) {
            this.loggedUser = (Profile) loggedUser;
            this.profileId = loggedUser.getId();
            setListeners();
            afterFullInitialization();
        }
    }

    private void afterFullInitialization() {
        cmbCatList = gcm.getAllCategories(profileId);
        cmbCategory.setItems(cmbCatList);
        cmbCategory.setItemCaptionGenerator(GramaCategory::getCategory);

    }

    private void setListeners() {
        cmbCategory.setNewItemProvider(inputString->{
            GramaCategory newCategory = new GramaCategory(0, inputString, profileId, null);
            cmbCatList.add(newCategory);
            cmbCategory.setItems(cmbCatList);
            cmbCategory.setSelectedItem(newCategory);
            return Optional.of(newCategory);
        });

        btnReset.addClickListener(event->{
            clearFileds();
        });

        txtTitle.addShortcutListener(new ShortcutListener("Enter", ShortcutAction.KeyCode.ENTER, null) {

            @Override
            public void handleAction(Object sender, Object target) {
                GramaRule tmp = grm.findExactGramaRule(txtTitle.getValue().trim(), profileId);
                if (tmp!=null){
                    txtaDefn.setValue(tmp.getDefn());
                    txtaExample.setValue(tmp.getExmp());
                }
            }
        });

        btnSend.addClickListener(event->{
             GramaRule rule = assignCompValues();

            if (rule.getId()<0){
                tabDetails.removeAllComponents();
                tabDetails.addComponent(new LoginError(ch, "Wypełnij poprawnie wszytkie pola"));
            }else{
                GramaRule tmp = grm.findExactGramaRule(rule.getTitle(), profileId);
                if (tmp==null)
                    grm.insertGramaRule(rule);
                else
                    grm.updateGramaRule(rule);
                tabDetails.removeAllComponents();
                clearFileds();
            }
        });
    }

    private void clearFileds() {
        txtTitle.setValue("");
        txtaDefn.setValue("");
        txtaExample.setValue("");
    }

    private GramaRule assignCompValues() {
        String tit = (txtTitle.getValue()!=null&&!txtTitle.getValue().trim().isEmpty())?txtTitle.getValue().trim():"";
        String dfn = (txtaDefn.getValue()!=null&&!txtaDefn.getValue().trim().isEmpty())?txtaDefn.getValue().trim():"";
        String exm = (txtaExample.getValue()!=null&&!txtaExample.getValue().trim().isEmpty())?txtaExample.getValue().trim():"";
        String cat = (cmbCategory.getValue()!=null&&!cmbCategory.getValue().getCategory().trim().isEmpty())?cmbCategory.getValue().getCategory().trim():"";

        Integer catId = gcd.resolveID(cat, profileId);
        Integer id = (catId<1||tit.isEmpty()||dfn.isEmpty())?-1:0;

        return new GramaRule(id,catId, tit,dfn,exm,profileId, null);
    }

}
