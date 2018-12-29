package com.tw.progs.HandyEnglish.db.myBatis.mappers;

import com.tw.progs.HandyEnglish.db.myBatis.dtos.GramaCategory;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface GramaCategoriesMapper {

	@Insert("insert into grama_categories(grama_categ, prof) values(#{grama_categ}, #{prof})")
	@Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
	void insertCategory(GramaCategory category);

	@Select("SELECT * FROM grama_categories WHERE grama_categ=#{category} and prof=#{prof}")
	GramaCategory findCategory(@Param("category")String category, @Param("prof") Integer profile);

	@Select("SELECT * FROM grama_categories WHERE id=#{id}")
	GramaCategory findCategoryById(Integer id);

	@Select("SELECT * FROM grama_categories WHERE prof=#{prof}")
	List<GramaCategory> getAllCategories(@Param("prof") Integer profile);
}
