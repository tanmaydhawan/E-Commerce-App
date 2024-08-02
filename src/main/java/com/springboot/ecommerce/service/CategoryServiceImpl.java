package com.springboot.ecommerce.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.springboot.ecommerce.model.Category;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	List<Category> categoryList = new ArrayList<>();
	private int id = 1;

	@Override
	public List<Category> fetchAllCategories() {
		return categoryList;
	}

	@Override
	public String postCategory(Category category) {
		category.setCategoryId(id++);
		categoryList.add(category);
		return "Category Added successfully";
	}

	@Override
	public String removeCategory(int categoryId) {
		
		Category category = categoryList.stream()
					.filter(c-> c.getCategoryId()==categoryId)
					.findFirst().get();
		
		categoryList.remove(category);
		return "Category deleted successfully"+ categoryId;
	}

}
