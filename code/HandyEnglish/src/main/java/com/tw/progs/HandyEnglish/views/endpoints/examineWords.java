package com.tw.progs.HandyEnglish.views.endpoints;

import com.tw.progs.HandyEnglish.global.HE_Constants;
import com.tw.progs.HandyEnglish.models.daos.IDAO;
import com.tw.progs.HandyEnglish.views.BaseView;
import com.tw.progs.HandyEnglish.views.handlers.examineWordsHandler;
import com.vaadin.spring.annotation.SpringUI;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;


@SpringUI(path = "/HandyEnglish/"+ HE_Constants.WordsExamEndpoint)
public class examineWords extends BaseView {

    @Autowired
    private examineWordsHandler examineWords;

    @Override
    protected void show(Map<String, String[]> params) {

        String[] categories = params.get(HE_Constants.ExamCategoryParam);
        String[] topics = params.get(HE_Constants.ExamTopicParam);
        boolean revertedExam = params.containsKey(HE_Constants.ExamWordsRevert);

        examineWords.init(categories, topics, revertedExam);
        examineWords.setSqlSession(sqlSF.openSession(false));
        examineWords.setLoggedUser(ls, (IDAO)ls.getLoginUserDAO());
        setContent(examineWords);

    }
}
