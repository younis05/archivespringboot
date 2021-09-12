package com.younes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.younes.entity.Category;
import com.younes.service.CategoryService;

@Controller
@RequestMapping("/category")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;
	
	@GetMapping("/list-categories")
	public String getAllCategories(Model model) {
		List<Category> listCategories=categoryService.findAllCategories();
		model.addAttribute("listCategories", listCategories);
		return"category/list_categories";
	}
	
	@GetMapping("/register")
	public String register(Model model) {
		model.addAttribute("title", "Registration");
		model.addAttribute("category", new Category());
		
		return "category/category_form";
	}
	
	@PostMapping("/register-category")
	public String registerCategory(@ModelAttribute Category category) {
		categoryService.saveCategory(category);
		
		return "redirect:/category/list-categories";
	}
	@GetMapping("/update/{id}")
	public String editCategory(@PathVariable("id")Long id,Model model) {
		model.addAttribute("title", "Edit-category");
		Category category=categoryService.findCategoryById(id);
        
		model.addAttribute("category", category);
		return "category/edit_form";
	}
	@PostMapping("/edit")
	public String editCategory(@ModelAttribute Category category) {
		
		categoryService.saveCategory(category);
		
		return "redirect:/category/list-categories";
	}
	@GetMapping("/delete/{id}")
	public String deleteCategory(@PathVariable("id")Long id) {

		categoryService.deleteCategory(id);
        
		return "redirect:/category/list-categories";
	}
}
