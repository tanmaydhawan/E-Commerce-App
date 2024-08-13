package com.springboot.ecommerce.service;

import com.springboot.ecommerce.payload.CategoryDTO;
import com.springboot.ecommerce.payload.CategoryResponse;

public interface CategoryService {
	
	public CategoryResponse fetchAllCategories(Integer pageNumber, Integer pageSize);
	public CategoryDTO postCategory(CategoryDTO categoryDto);
	public CategoryDTO removeCategory(int categoryId);
	public CategoryDTO editCategory(CategoryDTO categoryDTO, int categoryId);

}
