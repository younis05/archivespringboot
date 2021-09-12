package com.younes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.younes.entity.Category;
import com.younes.repository.CategoryRepository;
import com.younes.service.impl.CategoryServiceImpl;

@Service
public class CategoryService implements CategoryServiceImpl{

	@Autowired
	private CategoryRepository categoryRepository;
	@Override
	public List<Category> findAllCategories() {
		
		return categoryRepository.findAll();
	}

	@Override
	public Category getCategoryByName(String name) {
		
		return categoryRepository.getCategoryByName(name);
	}

	@Override
	public Category saveCategory(Category category) {
		
		return categoryRepository.save(category);
	}

	@Override
	public Category findCategoryById(Long id) {
		
		return categoryRepository.findById(id).get();
	}

	@Override
	public Category updateCategory(Category category, Long id) {
		category.setId(id);
		return categoryRepository.save(category);
	}

	@Override
	public void deleteCategory(Long id) {
		
		categoryRepository.deleteById(id);
	}

}
