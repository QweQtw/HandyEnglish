package com.tw.progs.HandyEnglish.views.gui;

import com.tw.progs.HandyEnglish.db.myBatis.dtos.Profile;
import com.tw.progs.HandyEnglish.db.myBatis.dtos.Word;
import com.tw.progs.HandyEnglish.db.myBatis.mappers.CategoriesMapper;
import com.tw.progs.HandyEnglish.db.myBatis.mappers.WordsMapper;
import com.tw.progs.HandyEnglish.models.daos.CategoryDAO;
import com.tw.progs.HandyEnglish.models.daos.IDAO;
import com.tw.progs.HandyEnglish.tools.CaptionHolder;
import com.tw.progs.HandyEnglish.tools.LoginService;
import com.vaadin.server.Page;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class addWordHandler extends addWord {


    private SqlSession sqlSession;
    private String uriLocation;
    private LoginService loginService;
    private Profile loggedUser;
    private WordsMapper wm;
    private CategoriesMapper cm;
    private CategoryDAO cd;
    private CaptionHolder ch;
    private Integer profileId;

    @Autowired
    public addWordHandler(WordsMapper wm, CategoriesMapper cm, CategoryDAO cd, CaptionHolder ch){
        this.wm = wm;
        this.cm = cm;
        this.cd = cd;
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
        cmbCategory.setItems(cm.getAllCategories(profileId).stream().map(x->x.getCategory()));
        //cmbCategory.setSelectedItem();
    }

    private void setListeners() {
        cmbCategory.addValueChangeListener(e->{
            e.
                    dopisać dodawanie
                    albo sprawdzić jak edytować cmb i dodawać z ręki itemy
        })

        btnReset.addClickListener(event->{
            clearFileds();
        });

        btnSend.addClickListener(event->{
            Word word = assignCompValues();

            if (word.getId()<0){
                tabDetails.removeAllComponents();
                tabDetails.addComponent(new LoginError(ch, "Wypełnij poprawnie wszytkie pola"));
            }else{
                Word tmp = wm.findWord(word.getWord(), word.getEqiv(), profileId);
                if (tmp==null)
                    wm.insertWord(word);
                else
                    wm.updateWord(word);
                tabDetails.removeAllComponents();
                clearFileds();
            }
        });
    }

    private void clearFileds() {
        txtWord.setValue("");
        txtEquiv.setValue("");
        txtDefn.setValue("");
        txtExmpl.setValue("");
    }

    private Word assignCompValues() {
        String cat = (cmbCategory.getValue()!=null&&!cmbCategory.getValue().trim().isEmpty())?cmbCategory.getValue().trim():"";
        String wrd = (txtWord.getValue()!=null&&!txtWord.getValue().trim().isEmpty())?txtWord.getValue().trim():"";
        String eqv = (txtEquiv.getValue()!=null&&!txtEquiv.getValue().trim().isEmpty())?txtEquiv.getValue().trim():"";
        String dfn = (txtDefn.getValue()!=null&&!txtDefn.getValue().trim().isEmpty())?txtDefn.getValue().trim():"";
        String exm = (txtExmpl.getValue()!=null&&!txtExmpl.getValue().trim().isEmpty())?txtExmpl.getValue().trim():"";

        Integer catId = cd.resolveID(cat, profileId);
        Integer id = (catId<1||wrd.isEmpty()||eqv.isEmpty())?-1:0;

        return new Word(id,catId,wrd,eqv,dfn,exm,profileId, null);
    }

}
