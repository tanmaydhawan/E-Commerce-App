package com.springboot.ecommerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.springboot.ecommerce.model.Category;
import com.springboot.ecommerce.repository.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository repository;

	@Override
	public List<Category> fetchAllCategories() {
		return repository.findAll();
	}

	@Override
	public String postCategory(Category category) {
		repository.save(category);
		return "Category Added successfully";
	}

	@Override
	public String removeCategory(int categoryId) {

		Optional<Category> category = repository.findById(categoryId);

		if (category.isPresent()) {
			repository.deleteById(categoryId);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found!");
		}

		return "Category deleted successfully: " + categoryId;
	}

	@Override
	public String editCategory(Category category, int categoryId) {

		Optional<Category> existingCategory = repository.findById(categoryId);

		if (existingCategory.isPresent()) {
			Category updatedCategory = existingCategory.get();
			updatedCategory.setCategoryName(category.getCategoryName());
			repository.save(updatedCategory);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not Found!");
		}
		return "Category with id " + categoryId + " has been updated!";
	}

}
