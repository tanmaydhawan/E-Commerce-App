package com.springboot.ecommerce.service;

import java.util.List;

import com.springboot.ecommerce.model.Category;

public interface CategoryService {
	
	public List<Category> fetchAllCategories();
	public String postCategory(Category category);
	public String removeCategory(int categoryId);

}
