package com.tw.progs.HandyEnglish.db.myBatis.mappers;

import com.tw.progs.HandyEnglish.db.myBatis.dtos.Category;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CategoriesMapper {

	@Insert("insert into categories(category, prof) values(#{category}, #{prof})")
	@Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
	void insertCategory(Category category);

	@Select("SELECT * FROM categories WHERE category=#{category} and prof=#{prof}")
	Category findCategory(String category, @Param("prof")Integer profile);

	@Select("SELECT * FROM categories WHERE id=#{id}")
	Category findCategoryById(Integer id);

	@Select("SELECT * FROM categories WHERE prof=#{prof}")
	List<Category> getAllCategories(@Param("prof")Integer profile);
}
