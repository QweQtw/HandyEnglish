package com.tw.progs.HandyEnglish.views.handlers;

import com.tw.progs.HandyEnglish.db.myBatis.dtos.Profile;
import com.tw.progs.HandyEnglish.db.myBatis.dtos.Word;
import com.tw.progs.HandyEnglish.db.myBatis.mappers.CategoriesMapper;
import com.tw.progs.HandyEnglish.db.myBatis.mappers.TestSummaryMapper;
import com.tw.progs.HandyEnglish.db.myBatis.mappers.TopicsMapper;
import com.tw.progs.HandyEnglish.db.myBatis.mappers.WordsMapper;
import com.tw.progs.HandyEnglish.global.HE_Constants;
import com.tw.progs.HandyEnglish.models.daos.IDAO;
import com.tw.progs.HandyEnglish.tools.CaptionHolder;
import com.tw.progs.HandyEnglish.tools.LoginService;
import com.tw.progs.HandyEnglish.views.gui.ExamineWords;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.Page;
import com.vaadin.ui.Notification;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Random;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class examineWordsHandler extends ExamineWords {

    private String[] categories;
    private String[] topics;
    private SqlSession sqlSession;
    private String uriLocation;
    private LoginService loginService;
    private Profile loggedUser;
    private Integer profileId;

    private List<Word> examWordList;
    private WordsMapper wm;
    private CategoriesMapper cm;
    private TopicsMapper tm;
    private TestSummaryMapper tsm;
    private CaptionHolder ch;
    private Word currentWord;
    private Random rnd;

    private int goodAns = 0;
    private int badAns = 0;
    private int overall = 0;
    private int pktDelta = 4;
    private int pkt = 0;
    private Instant begin;

    @Autowired
    public examineWordsHandler(WordsMapper wm, CategoriesMapper cm, TopicsMapper tm, TestSummaryMapper tsm, CaptionHolder ch){

        this.wm = wm;
        this.cm = cm;
        this.tm = tm;
        this.tsm = tsm;
        this.ch = ch;
        rnd = new Random();
    }

    public void init(String[] categories, String[] topics) {

        this.categories = categories;
        this.topics = topics;
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
        if (categories!=null && categories.length>0 && topics!=null && topics.length>0){
            examWordList = wm.getWordsByTopicAndCat(new Integer(categories[0]), new Integer(topics[0]), profileId);
        }else{
            if (categories!=null  && categories.length>0){
                examWordList = wm.getWordsByCategory(new Integer(categories[0]), profileId);
            }else
            if (topics!=null && topics.length>0){
                examWordList = wm.getWordsByTopic(new Integer(topics[0]), profileId);
            }else{
                examWordList = null;
            }
        }

        if (examWordList==null || examWordList.size()<1) {
            Notification.show(ch.getCaption("Nie można pobrać danych do egzaminu")+"!", Notification.Type.ERROR_MESSAGE);
            redirection("mainPage");
        }

        overall = examWordList.size();
        prgProgress.setValue((goodAns+badAns)/overall);
        Notification.show("Let's start.", Notification.Type.WARNING_MESSAGE);
        begin = Instant.now();

        processList();
    }

    private void setListeners() {
        btnCheck.addClickListener(e->{
            checkValidity();
            processList();
        });

        btnHint.addClickListener(e->{
           txaDefn.setValue(currentWord.getDefn());
            pktDelta = 2;
        });

        btnExmpl.addClickListener(e->{
            String tmp = currentWord.getExmp().trim();
           txaExmpl.setValue(tmp);
           if(!tmp.isEmpty()) {
               pktDelta = 0;
               btnCheck.setEnabled(false);
           }
        });
        btnNext.addClickListener(e->{
            badAns++;
            finishStep();
            processList();
        });
        btnBreak.addClickListener(e->{
            redirection("mainPage");
        });
    }

    private void checkValidity() {
        if (txtAnswer.getValue().trim().equalsIgnoreCase(currentWord.getWord().trim())){
            pkt = pkt + pktDelta;
            goodAns++;
            Notification.show("OK.", Notification.Type.TRAY_NOTIFICATION);
        }else{
            badAns++;
            Notification.show("WRONG!\n\nYour answer:\n"+txtAnswer.getValue()+"\nGood answer:\n"+currentWord.getWord(), Notification.Type.ERROR_MESSAGE);
        }
        finishStep();
    }

    private void finishStep() {

        btnOverall.setCaption(""+(goodAns+badAns)+"/"+overall);
        btnGood.setCaption(""+goodAns);
        btnBad.setCaption(""+badAns);
        txtAnswer.clear();
        txaDefn.clear();
        txaExmpl.clear();
        btnCheck.setEnabled(true);
        examWordList.remove(currentWord);
    }

    private void processList() {
        int tmp = examWordList.size();
        prgProgress.setValue(((float)(goodAns+badAns))/(float)overall);
        if (tmp>0) {
            int idx = (tmp > 1) ? rnd.nextInt(tmp) : 0;
            currentWord = examWordList.get(idx);
            pktDelta = 4;
            txtEquiv.setValue(currentWord.getEqiv());
        }else{
            Instant end = Instant.now();
            Integer durSec = (new Long(Duration.between(begin, end).toMillis() / 1000)).intValue();
            int topicId = (topics!=null)?new Integer(topics[0]):-1;
            int categId = (categories!=null)?new Integer(categories[0]):-1;
            String  topic = (topics!=null)?tm.findTopicById(new Integer(topics[0])).getTopic():"brak";
            String  categ = (categories!=null)?cm.findCategoryById(new Integer(categories[0])).getCategory():"brak";
            tsm.insertTestSummary(HE_Constants.ExamTypes_Words,categId,topicId, overall,goodAns, durSec, profileId);
            StringBuilder sb = new StringBuilder();
            sb.append(ch.getCaption("PODSUMOWANIE")).append(":").append("\n\n")
                    .append("test z kategorii").append(": ").append(categ).append("\n")
                    .append("test z tematu").append(": ").append(topic).append("\n\n")
                    .append("WYNIKI").append("\n")
                    .append("Wszytkich pytań").append(": "+overall).append("\n")
                    .append("dobrych odpowiedzi").append(": "+goodAns).append("\n").append("\n")
                    .append("Czas trawania testu").append(": "+durSec).append(" sec.")
                    .append("\n").append("\n").append("KONIEC.");
            Notification.show(sb.toString(), Notification.Type.ERROR_MESSAGE);
            //redirection("mainPage");
        }
    }

    private void redirection(String linkName) {
        if (linkName==null || linkName.isEmpty()){
            Page.getCurrent().open(uriLocation, "_self");
        }else {
            String tmp = uriLocation.substring(0, uriLocation.lastIndexOf(HE_Constants.WordsExamEndpoint) );
            final ExternalResource redirection = new ExternalResource(tmp + linkName);
            Page.getCurrent().open(redirection.getURL(), "_self");
        }
    }


}
