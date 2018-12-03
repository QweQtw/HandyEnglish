package com.tw.progs.HandyEnglish.db.myBatis.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.tw.progs.HandyEnglish.db.myBatis.dtos.Category;

@Mapper
public interface CategoriesMapper {
	
	@Select("SELECT * FROM categories WHERE category=#{category}")
	Category findCategory(String category);

	@Select("SELECT * FROM categories WHERE id=#{id}")
	Category findCategoryById(Integer id);

	@Select("SELECT * FROM categories")
	List<Category> getAllCategories();
}
