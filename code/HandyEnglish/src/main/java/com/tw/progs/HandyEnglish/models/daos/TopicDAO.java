package com.tw.progs.HandyEnglish.models.daos;

import com.tw.progs.HandyEnglish.db.myBatis.dtos.Topic;
import com.tw.progs.HandyEnglish.db.myBatis.mappers.TopicsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TopicDAO {

    private TopicsMapper tm;
    private ProfilesDAO pd;

    @Autowired
    public TopicDAO(TopicsMapper tm, ProfilesDAO pd){
        this.tm = tm;
        this.pd = pd;
    }

    public Integer resolveID(String tpc, Integer profileId) {

        tpc = (tpc==null||tpc.trim().isEmpty())?"":tpc;

        if (tpc.isEmpty()){
            Topic topic = tm.getAllTopics(pd.getDefUserAccountId()).get(0);
            topic.setProf(profileId);
            topic.setId(-1);
            Topic tmp = tm.findTopic(topic.getTopic(), profileId);
            if (tmp==null)
                tm.insertTopic(topic);
            return tmp.getId();
        }else{
            Topic topic = tm.findTopic(tpc, profileId);
            if (topic!=null&&topic.getId()>0) {
                return topic.getId();
            }else {
                topic = new Topic();
                topic.setProf(profileId);
                topic.setTopic(tpc);
                tm.insertTopic(topic);
                return topic.getId();
            }
        }
    }
}
