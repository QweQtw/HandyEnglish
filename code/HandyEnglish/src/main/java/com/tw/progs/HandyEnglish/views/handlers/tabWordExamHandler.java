package com.tw.progs.HandyEnglish.views.handlers;

import com.tw.progs.HandyEnglish.db.myBatis.dtos.Category;
import com.tw.progs.HandyEnglish.db.myBatis.dtos.Profile;
import com.tw.progs.HandyEnglish.db.myBatis.dtos.Topic;
import com.tw.progs.HandyEnglish.db.myBatis.mappers.CategoriesMapper;
import com.tw.progs.HandyEnglish.db.myBatis.mappers.TopicsMapper;
import com.tw.progs.HandyEnglish.db.myBatis.mappers.WordsMapper;
import com.tw.progs.HandyEnglish.global.EntitiesConfiguration;
import com.tw.progs.HandyEnglish.global.HE_Constants;
import com.tw.progs.HandyEnglish.tools.CaptionHolder;
import com.tw.progs.HandyEnglish.views.gui.tabWordExam;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.Page;
import com.vaadin.ui.Notification;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class tabWordExamHandler extends tabWordExam {

    private final CategoriesMapper cm;
    private final TopicsMapper tm;
    private WordsMapper wm;
    private final CaptionHolder ch;
    private Integer profileId;
    private String uriLocation;
    private List<Category> cmbCatList;
    private List<Topic> cmbTopicList;

    private Optional<Category> catFilter = Optional.empty();
    private Optional<Topic> topicFilter = Optional.empty();
    private int examSize = 0;
    private String examParm = "";

    @Autowired
    public tabWordExamHandler(CategoriesMapper cm, TopicsMapper tm, WordsMapper wm, CaptionHolder ch, Profile profile, String uriLocation){
        this.cm = cm;
        this.tm = tm;
        this.wm = wm;
        this.ch = ch;
//        this.profile = profile;
        this.profileId = profile.getId();
        this.uriLocation = uriLocation;
        afterFullInitialization();
    }

    private void afterFullInitialization() {
        cmbCatList = cm.getAllCategories(profileId);
        cmbCategory.setItems(cmbCatList);
        cmbCategory.setItemCaptionGenerator(Category::getCategory);

        cmbTopicList = tm.getAllTopics(profileId);
        cmbTopic.setItems(cmbTopicList);
        cmbTopic.setItemCaptionGenerator(Topic::getTopic);
        //cmbCategory.setSelectedItem();
        setListeners();
    }

    private void setListeners() {
        cmbCategory.addValueChangeListener(inputString->{
            catFilter = cmbCategory.getSelectedItem();
            calAmount();
        });

        cmbTopic.addValueChangeListener(inputString->{
            topicFilter = cmbTopic.getSelectedItem();
            calAmount();
        });

        btnBack.addClickListener(event->{
            redirection("mainPage");
        });

        btnLaunch.addClickListener(event->{
            if (examSize<=0||examParm.isEmpty()) {
                Notification.show(ch.getCaption("Błędne parametry examinu")+"!", Notification.Type.ERROR_MESSAGE);
                redirection("mainPage");
            }
            redirection(HE_Constants.WordsExamEndpoint+examParm);
        });
    }

    private void calAmount() {
        //examSize = 0;
        String revertParam = (chbRevert.getValue())?"&"+HE_Constants.ExamWordsRevert+"=1":"";
        if (catFilter.isPresent()&&topicFilter.isPresent()){
            examSize = wm.getWordsByTopicAndCat(catFilter.get().getId(), topicFilter.get().getId(), profileId).size();
            examParm = "?"+HE_Constants.ExamCategoryParam+"="+catFilter.get().getId()
                    +"&"+HE_Constants.ExamTopicParam+"="+topicFilter.get().getId()
                    +revertParam;
            lblAmount.setValue(""+examSize);
        }else{
            if (catFilter.isPresent()){
                examSize = wm.getWordsByCategory(catFilter.get().getId(), profileId).size();
                examParm = "?"+HE_Constants.ExamCategoryParam+"="+catFilter.get().getId()+revertParam;
                lblAmount.setValue(""+examSize);
            }else
            if (topicFilter.isPresent()){
                examSize = wm.getWordsByTopic(topicFilter.get().getId(), profileId).size();
                examParm = "?"+HE_Constants.ExamTopicParam+"="+topicFilter.get().getId()+revertParam;
                lblAmount.setValue(""+examSize);
            }else{
                examSize = 0;
                examParm = "";
            }
        }
        btnLaunch.setEnabled(examSize > 0);
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
