package com.younes.service.impl;

import java.util.List;

import com.younes.entity.Category;


public interface CategoryServiceImpl {

	public List<Category> findAllCategories();
	public Category getCategoryByName(String name);
	public Category saveCategory(Category category);
	public Category findCategoryById(Long id);
	public Category updateCategory(Category category,Long id);
	public void deleteCategory(Long id);
}
