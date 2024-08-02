package com.springboot.ecommerce.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.springboot.ecommerce.model.Category;

public interface CategoryService {
	
	public List<Category> fetchAllCategories();
	public String postCategory(Category category);
	public String removeCategory(int categoryId);

}
