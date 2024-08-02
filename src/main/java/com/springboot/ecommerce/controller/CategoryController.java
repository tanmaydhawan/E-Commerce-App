package com.springboot.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.ecommerce.model.Category;
import com.springboot.ecommerce.service.CategoryService;

@RestController
@RequestMapping("/api")
public class CategoryController {

	@Autowired
	CategoryService service;
	
	@GetMapping("/user/category")
	public List<Category> getAllCategory() {
		return service.fetchAllCategories();
	}

	@PostMapping("/admin/category")
	public String addCategory(@RequestBody Category category) {
			return service.postCategory(category);
	}
	
	@DeleteMapping("/admin/category/{categoryId}")
	public String deleteCatgory(@PathVariable int categoryId) {
		String status = service.removeCategory(categoryId);
		return status;
	}

}
