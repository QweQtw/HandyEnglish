package com.tw.progs.HandyEnglish.views.gui;

import com.tw.progs.HandyEnglish.db.myBatis.dtos.Category;
import com.tw.progs.HandyEnglish.db.myBatis.dtos.Profile;
import com.tw.progs.HandyEnglish.db.myBatis.dtos.Word;
import com.tw.progs.HandyEnglish.db.myBatis.mappers.CategoriesMapper;
import com.tw.progs.HandyEnglish.db.myBatis.mappers.WordsMapper;
import com.tw.progs.HandyEnglish.models.daos.IDAO;
import com.tw.progs.HandyEnglish.tools.CaptionHolder;
import com.tw.progs.HandyEnglish.tools.LoginService;
import com.vaadin.event.ShortcutAction;
import com.vaadin.event.ShortcutListener;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TextField;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Set;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class showDictionaryHandler extends showDictionary {

    private WordsMapper wm;
    private CategoriesMapper cm;
    private CaptionHolder ch;
    private SqlSession sqlSession;
    private String uriLocation;
    private LoginService loginService;
    private Profile loggedUser;
    private Integer profileId;

    private List<Word> words;
    private String wordtxtId;
    private String eqivtxtId;

    @Autowired
    public showDictionaryHandler(WordsMapper wm, CategoriesMapper cm, CaptionHolder ch) {
        this.wm = wm;
        this.cm = cm;
        this.ch = ch;
    }

    @PostConstruct
    public void init(){

        Object obj = VaadinSession.getCurrent().getAttribute(LoginService.AuthSessionKey);
        String logedAs = "";
        if (obj instanceof Profile){
            loggedUser = (Profile)obj;
            logedAs = loggedUser.getName();
            this.profileId = loggedUser.getId();
            setDataProvider();
        }else{
//            tabDetails.addComponentsAndExpand(new LoginError(ch));
        }

        setListeners();
        //redirection("mainPage");

        setCaptions();

        wordtxtId = txtWord.getCaption();
        eqivtxtId = txtEquiv.getCaption();
    }

    private void setCaptions() {

        pnlDictionary.setCaption(ch.getCaption("Twój słownik")+":");
        cmbCategory.setCaption(ch.getCaption("kategoria")+":");
        txtWord.setCaption(ch.getCaption("słowo")+":");
        txtEquiv.setCaption(ch.getCaption("odpowiednik")+":");
    }

    private void setListeners() {
        cmbCategory.addValueChangeListener(e->{
            if(!e.isUserOriginated()) return;
            txtWord.clear();
            txtEquiv.clear();
            if (e.getValue()==null || e.getValue().getCategory().trim().isEmpty()){
                words = wm.getAllWordsForUser(profileId);
                grdData.setItems(words);
            }else {
                int cat = e.getValue().getId();
                words = wm.getWordsByCategory(cat, profileId);
                grdData.setItems(words);
            }
        });

        pnlDictionary.addShortcutListener(new ShortcutListener("Enter", ShortcutAction.KeyCode.ENTER, null) {
            @Override
            public void handleAction(Object sender, Object target) {
                if (target instanceof TextField) {
                    if (((TextField) target).getCaption().equalsIgnoreCase(wordtxtId)) {
                        cmbCategory.clear();
                        txtEquiv.clear();
                        if (txtWord.getValue() == null || txtWord.getValue().trim().isEmpty()) {
                            words = wm.getAllWordsForUser(profileId);
                            grdData.setItems(words);
                        } else {
                            words = wm.findWordLike(txtWord.getValue().trim() + "%", profileId);
                            grdData.setItems(words);
                        }
                    }
                    if (((TextField) target).getCaption().equalsIgnoreCase(eqivtxtId)) {
                        cmbCategory.clear();
                        txtWord.clear();
                        if (txtEquiv.getValue() == null || txtEquiv.getValue().trim().isEmpty()) {
                            words = wm.getAllWordsForUser(profileId);
                            grdData.setItems(words);
                        } else {
                            words = wm.findEquivLike(txtEquiv.getValue().trim() + "%", profileId);
                            grdData.setItems(words);
                        }
                    }
                }

            }
        });

        btnClear.addClickListener(e->{
           txtWord.setValue("");
           txtEquiv.setValue("");
           cmbCategory.clear();
        });

        grdData.addSelectionListener(event -> {
            btnDetails.setEnabled(false);
            Set<Word> selected = event.getAllSelectedItems();
            selected.stream().findFirst().ifPresent(item -> {
                btnDetails.setEnabled(true);
            });
        });

        btnDetails.addClickListener(e->{

        });

    }

    private void setDataProvider() {
        List<Category> cmbList = cm.getAllCategories(profileId);
        cmbCategory.setItems(cmbList);
        cmbCategory.setItemCaptionGenerator(Category::getCategory);
//        cmbCategory.setSelectedItem(cmbList.get(0));

        grdData.addColumn(w-> cm.findCategoryById(w.getCategory()).getCategory()).setCaption(ch.getCaption("kategoria"));
        grdData.addColumn(Word::getWord).setCaption(ch.getCaption("słowo"));
        grdData.addColumn(Word::getEqiv).setCaption(ch.getCaption("odpowiednik"));
        //grdActions.setItems(new ArrayList<ActionsDAO>());
        words = wm.getAllWordsForUser(profileId);
        grdData.setItems(words);

        //grdActions.setDataProvider((sortorder, offset, limit)-> am.findAllUsers(offset, limit).stream(), ()->am.countAllUsers());

        grdData.setSelectionMode(Grid.SelectionMode.SINGLE);
    }

    public void setSqlSession(SqlSession openSession) {
        this.sqlSession = openSession;
        this.uriLocation = Page.getCurrent().getLocation().toString();
    }

    public void setLoggedUser(LoginService ls, IDAO profile) {
        this.loginService = ls;
        if (profile!=null ) {
            this.loggedUser = (Profile) profile;
//            setDataProvider();
//            setListeners();
            afterFullInitialization();
        }else{
//            tabDetails.addComponent(new LoginError(ch));
        }
    }

    private void afterFullInitialization() {
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
