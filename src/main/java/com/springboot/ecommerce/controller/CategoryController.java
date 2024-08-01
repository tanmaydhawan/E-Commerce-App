package com.springboot.ecommerce.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.ecommerce.model.Category;

@RestController
@RequestMapping("/api")
public class CategoryController {

	List<Category> categoryList = new ArrayList<>();

	@GetMapping("/user/category")
	public List<Category> getAllCategory() {
		return categoryList;
	}

	@PostMapping("/admin/category")
	public String addCategory(@RequestBody Category category) {
	
		categoryList.add(category);	
		return "Category Added Successfully";

	}

}
