package com.tw.progs.HandyEnglish.views.handlers;

import com.tw.progs.HandyEnglish.db.myBatis.dtos.Category;
import com.tw.progs.HandyEnglish.db.myBatis.dtos.Profile;
import com.tw.progs.HandyEnglish.db.myBatis.dtos.Topic;
import com.tw.progs.HandyEnglish.db.myBatis.dtos.Word;
import com.tw.progs.HandyEnglish.db.myBatis.mappers.CategoriesMapper;
import com.tw.progs.HandyEnglish.db.myBatis.mappers.TopicsMapper;
import com.tw.progs.HandyEnglish.db.myBatis.mappers.WordsMapper;
import com.tw.progs.HandyEnglish.models.daos.CategoryDAO;
import com.tw.progs.HandyEnglish.models.daos.IDAO;
import com.tw.progs.HandyEnglish.models.daos.TopicDAO;
import com.tw.progs.HandyEnglish.tools.CaptionHolder;
import com.tw.progs.HandyEnglish.tools.LoginService;
import com.tw.progs.HandyEnglish.views.gui.LoginError;
import com.tw.progs.HandyEnglish.views.gui.addWord;
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
public class addWordHandler extends addWord {


    private SqlSession sqlSession;
    private String uriLocation;
    private LoginService loginService;
    private Profile loggedUser;
    private WordsMapper wm;
    private CategoriesMapper cm;
    private TopicsMapper tm;
    private CategoryDAO cd;
    private TopicDAO td;
    private CaptionHolder ch;
    private Integer profileId;

    private List<Category> cmbCatList;
    private List<Topic> cmbTopicList;

    @Autowired
    public addWordHandler(WordsMapper wm, CategoriesMapper cm, TopicsMapper tm, CategoryDAO cd, TopicDAO td, CaptionHolder ch){
        this.wm = wm;
        this.cm = cm;
        this.tm = tm;
        this.cd = cd;
        this.td = td;
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
        cmbCatList = cm.getAllCategories(profileId);
        cmbCategory.setItems(cmbCatList);
        cmbCategory.setItemCaptionGenerator(Category::getCategory);

        cmbTopicList = tm.getAllTopics(profileId);
        cmbTopic.setItems(cmbTopicList);
        cmbTopic.setItemCaptionGenerator(Topic::getTopic);
        //cmbCategory.setSelectedItem();
    }

    private void setListeners() {
        cmbCategory.setNewItemProvider(inputString->{
            Category newCategory = new Category(0, inputString, profileId, null);
            cmbCatList.add(newCategory);
            cmbCategory.setItems(cmbCatList);
            cmbCategory.setSelectedItem(newCategory);
            return Optional.of(newCategory);
        });

        cmbTopic.setNewItemProvider(inputString->{
            Topic newTopic = new Topic(0, inputString, profileId, null);
            cmbTopicList.add(newTopic);
            cmbTopic.setItems(cmbTopicList);
            cmbTopic.setSelectedItem(newTopic);
            return Optional.of(newTopic);
        });

        btnReset.addClickListener(event->{
            clearFileds();
        });

        btnSend.addClickListener(event->{
            Word word = assignCompValues();

            if (word.getId()<0){
                tabDetails.removeAllComponents();
                tabDetails.addComponent(new LoginError(ch, "Wypełnij poprawnie wszytkie pola"));
            }else{
                Word tmp = wm.findExactWord(word.getWord(), word.getEqiv(), profileId);
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
        String wrd = (txtWord.getValue()!=null&&!txtWord.getValue().trim().isEmpty())?txtWord.getValue().trim():"";
        String eqv = (txtEquiv.getValue()!=null&&!txtEquiv.getValue().trim().isEmpty())?txtEquiv.getValue().trim():"";
        String dfn = (txtDefn.getValue()!=null&&!txtDefn.getValue().trim().isEmpty())?txtDefn.getValue().trim():"";
        String exm = (txtExmpl.getValue()!=null&&!txtExmpl.getValue().trim().isEmpty())?txtExmpl.getValue().trim():"";
        String cat = (cmbCategory.getValue()!=null&&!cmbCategory.getValue().getCategory().trim().isEmpty())?cmbCategory.getValue().getCategory().trim():"";
        String tpc = (cmbTopic.getValue()!=null&&!cmbTopic.getValue().getTopic().trim().isEmpty())?cmbTopic.getValue().getTopic().trim():"";

        Integer catId = cd.resolveID(cat, profileId);
        Integer tpcId = td.resolveID(tpc, profileId);
        Integer id = (catId<1||wrd.isEmpty()||eqv.isEmpty())?-1:0;

        return new Word(id,catId, tpcId,wrd,eqv,dfn,exm,profileId, null);
    }

}
