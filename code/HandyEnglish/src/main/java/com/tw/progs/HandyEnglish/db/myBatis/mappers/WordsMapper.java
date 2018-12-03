package com.tw.progs.HandyEnglish.db.myBatis.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.tw.progs.HandyEnglish.db.myBatis.dtos.Word;

@Mapper
public interface WordsMapper {
	
	@Select("SELECT * FROM words WHERE word=#{word}")
	List<Word> findWord(String word);

	@Select("SELECT * FROM words WHERE id=#{id}")
	Word findWordById(Integer id);

	@Select("SELECT * FROM words WHERE category=#{category}")
	List<Word> getWordsByCategory(Integer category);
}
