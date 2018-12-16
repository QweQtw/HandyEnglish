package com.tw.progs.HandyEnglish.db.myBatis.mappers;

import com.tw.progs.HandyEnglish.db.myBatis.dtos.Topic;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TopicsMapper {

	@Insert("insert into topics(topic, prof) values(#{topic}, #{prof})")
	@Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
	void insertTopic(Topic topic);

	@Select("SELECT * FROM topics WHERE topic=#{topic} and prof=#{prof}")
	Topic findTopic(String topic, @Param("prof") Integer profile);

	@Select("SELECT * FROM topics WHERE id=#{id}")
	Topic findTopicById(Integer id);

	@Select("SELECT * FROM topics WHERE prof=#{prof}")
	List<Topic> getAllTopics(@Param("prof") Integer profile);
}
