package com.tw.progs.HandyEnglish.db.myBatis.mappers;

import java.util.List;

import com.tw.progs.HandyEnglish.db.myBatis.dtos.Category;
import org.apache.ibatis.annotations.*;

import com.tw.progs.HandyEnglish.db.myBatis.dtos.Word;

@Mapper
public interface WordsMapper {

	@Insert("INSERT INTO words(category, word, eqiv, defn, exmp, prof) VALUES(#{category}, #{word}, #{eqiv}, #{defn}, #{exmp}, #{prof})")
	@Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
	void insertWord(Word word);

	@Update("UPDATE words SET category=#{category}, defn=#{defn}, exmp=#{exmp} WHERE word=#{word} AND eqiv=#{eqiv} AND prof=#{prof}")
	void updateWord(Word word);

	@Select("SELECT * FROM words WHERE word=#{word} AND eqiv=#{eqiv} AND prof=#{prof}")
	Word findExactWord(String word, String eqiv, @Param("prof")Integer profile);

	@Select("SELECT * FROM words WHERE prof=#{prof} AND word like #{word}")
	List<Word> findWordLike(String word, @Param("prof")Integer profile);

	@Select("SELECT * FROM words WHERE prof=#{prof} AND eqiv like #{eqiv}")
	List<Word> findEquivLike(String eqiv, @Param("prof")Integer profile);

	@Select("SELECT * FROM words WHERE id=#{id}")
	Word findWordById(Integer id);

	@Select("SELECT * FROM words WHERE category=#{category} AND prof=#{prof}")
	List<Word> getWordsByCategory(Integer category, @Param("prof")Integer profile);

	@Select("SELECT * FROM words WHERE prof=#{prof}")
	List<Word> getAllWordsForUser(@Param("prof")Integer profile);
}
