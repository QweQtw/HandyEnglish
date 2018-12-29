package com.tw.progs.HandyEnglish.db.myBatis.mappers;

import com.tw.progs.HandyEnglish.db.myBatis.dtos.GramaRule;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface GramaRulesMapper {

	@Insert("INSERT INTO grama_rules(grama_categ, titl, defn, exmp, prof) VALUES(#{grama_categ}, #{titl}, #{defn}, #{exmp}, #{prof})")
	@Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
	void insertGramaRule(GramaRule grama_rule);

	@Update("UPDATE grama_rules SET grama_categ=#{grama_categ}, defn=#{defn}, exmp=#{exmp} WHERE titl=#{titl} AND prof=#{prof}")
	void updateGramaRule(GramaRule grama_rule);

	@Select("SELECT * FROM grama_rules WHERE titl=#{title} AND prof=#{prof}")
	GramaRule findExactGramaRule(@Param("title") String title, @Param("prof") Integer profile);

	@Select("SELECT * FROM grama_rules WHERE prof=#{prof} AND defn like #{defn}")
	List<GramaRule> findDefinitionLike(@Param("defn") String defnition, @Param("prof") Integer profile);

	@Select("SELECT * FROM grama_rules WHERE prof=#{prof} AND titl like #{title}")
	List<GramaRule> findTitleLike(@Param("title") String title, @Param("prof") Integer profile);

	@Select("SELECT * FROM grama_rules WHERE id=#{id}")
	GramaRule findGramaRuleById(Integer id);

	@Select("SELECT * FROM grama_rules WHERE grama_categ=#{category} AND prof=#{prof}")
	List<GramaRule> getGramaRulesByCategory(@Param("category") Integer grama_category, @Param("prof") Integer profile);

	@Select("SELECT * FROM grama_rules WHERE prof=#{prof}")
	List<GramaRule> getAllGramaRulesForUser(@Param("prof") Integer profile);

}
