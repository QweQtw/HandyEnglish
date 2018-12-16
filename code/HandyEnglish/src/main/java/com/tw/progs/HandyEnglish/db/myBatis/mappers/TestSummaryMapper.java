package com.tw.progs.HandyEnglish.db.myBatis.mappers;

import org.apache.ibatis.annotations.*;

@Mapper
public interface TestSummaryMapper {

    @Insert("INSERT INTO testSummary(testType, category, topic, overall, goodAns, duration, prof) VALUES(#{testType}, #{category}, #{topic}, #{overall}, #{goodAns}, #{duration}, #{prof})")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    void insertTestSummary(String testType, Integer category, Integer topic, Integer overall, Integer goodAns, Integer duration, Integer prof);

}
