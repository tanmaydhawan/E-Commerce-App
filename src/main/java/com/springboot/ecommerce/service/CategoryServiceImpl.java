package com.springboot.ecommerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.ecommerce.exceptions.ApiException;
import com.springboot.ecommerce.exceptions.ResourceNotFoundException;
import com.springboot.ecommerce.model.Category;
import com.springboot.ecommerce.repository.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository repository;

	@Override
	public List<Category> fetchAllCategories() {

		List<Category> categories = repository.findAll();

		if (categories.isEmpty()) {
			throw new ApiException("Please create a category before fetching!");
		}
		return categories;
	}

	@Override
	public String postCategory(Category category) {

		Optional<Category> savedCategory = repository.findByCategoryName(category.getCategoryName());

		if (savedCategory.isPresent()) {
			throw new ApiException("Category with this NAME already exists!");
		}

		repository.save(category);
		return "Category Added successfully";
	}

	@Override
	public String removeCategory(int categoryId) {

		Optional<Category> category = repository.findById(categoryId);

		if (!category.isPresent()) {
			throw new ResourceNotFoundException("Category not found with id: " + categoryId);
		}

		repository.deleteById(categoryId);
		return "Category deleted successfully: " + categoryId;
	}

	@Override
	public String editCategory(Category category, int categoryId) {

		Optional<Category> existingCategory = repository.findById(categoryId);

		if (!existingCategory.isPresent()) {
			throw new ResourceNotFoundException("Category not found with id: " + categoryId);
		}

		Category updatedCategory = existingCategory.get();
		updatedCategory.setCategoryName(category.getCategoryName());
		repository.save(updatedCategory);

		return "Category with id " + categoryId + " has been updated!";
	}

}
