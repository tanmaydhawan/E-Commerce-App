package com.springboot.ecommerce.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
					.findFirst().orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Category not found!"));
		
		categoryList.remove(category);
		return "Category deleted successfully: "+ categoryId;
	}

	@Override
	public String editCategory(Category category, int categoryId) {

		Optional<Category> ogCategory = categoryList.stream()
				.filter(c-> c.getCategoryId()==categoryId)
				.findFirst();
		
		if(ogCategory.isPresent()) {
			ogCategory.get().setCategoryName(category.getCategoryName());
		}
		else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Category not Found!");
		}
		return "Category with id "+categoryId+" has been updated!";
	}

}
