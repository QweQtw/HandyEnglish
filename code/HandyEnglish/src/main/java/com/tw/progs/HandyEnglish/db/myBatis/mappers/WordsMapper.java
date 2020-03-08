package com.tw.progs.HandyEnglish.db.myBatis.mappers;

import com.tw.progs.HandyEnglish.db.myBatis.dtos.Word;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface WordsMapper {

	@Insert("INSERT INTO words(category, topic, word, eqiv, defn, exmp, prof) VALUES(#{category}, #{topic}, #{word}, #{eqiv}, #{defn}, #{exmp}, #{prof})")
	@Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
	void insertWord(Word word);

	@Update("UPDATE words SET category=#{category}, topic=#{topic}, defn=#{defn}, exmp=#{exmp} WHERE word=#{word} AND eqiv=#{eqiv} AND prof=#{prof}")
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
	List<Word> getWordsByCategory(@Param("category")Integer category, @Param("prof")Integer profile);

	@Select("SELECT * FROM words WHERE topic=#{topic} AND prof=#{prof}")
	List<Word> getWordsByTopic(@Param("topic")Integer topic, @Param("prof")Integer profileId);

	@Select("SELECT * FROM words WHERE category=#{category} AND topic=#{topic} AND prof=#{prof}")
	List<Word> getWordsByTopicAndCat(@Param("category")Integer category, @Param("topic")Integer topic, @Param("prof")Integer profileId);

	@Select("SELECT * FROM words WHERE prof=#{prof}")
	List<Word> getAllWordsForUser(@Param("prof")Integer profile);

}
