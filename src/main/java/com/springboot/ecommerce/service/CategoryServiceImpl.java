package com.springboot.ecommerce.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.springboot.ecommerce.entity.Category;
import com.springboot.ecommerce.exceptions.ApiException;
import com.springboot.ecommerce.exceptions.ResourceNotFoundException;
import com.springboot.ecommerce.payload.CategoryDTO;
import com.springboot.ecommerce.payload.CategoryResponse;
import com.springboot.ecommerce.repository.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository repository;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CategoryResponse fetchAllCategories(Integer pageNumber, Integer pageSize) {

		Pageable pageDetails = PageRequest.of(pageNumber, pageSize);
		Page<Category> categoryPage = repository.findAll(pageDetails);
		
		List<Category> categories = categoryPage.getContent();

		if (categories.isEmpty()) {
			throw new ApiException("Please create a category before fetching!");
		}
		
		List<CategoryDTO> list = categories.stream()
									.map(category -> modelMapper.map(category, CategoryDTO.class))
									.toList();
		
		CategoryResponse response = new CategoryResponse();
		response.setContent(list);
		response.setPageNumber(categoryPage.getNumber());
		response.setPageSize(categoryPage.getSize());
		response.setTotalElements(categoryPage.getTotalElements());
		response.setTotalPages(categoryPage.getTotalPages());
		response.setLastPage(categoryPage.isLast());
		
		return response;
	}
	
	@Override
	public CategoryDTO fetchCategorybyId(int categoryId) {
		Optional<Category> returnedCategory = repository.findById(categoryId);
		
		if (!returnedCategory.isPresent()) {
			throw new ResourceNotFoundException("No Category found with id: "+categoryId);
		}
		
		return modelMapper.map(returnedCategory, CategoryDTO.class);
	}

	@Override
	public CategoryDTO postCategory(CategoryDTO categoryDTO) {

		Category category = modelMapper.map(categoryDTO, Category.class);
		Optional<Category> categoryFromDB = repository.findByCategoryName(category.getCategoryName());
		
		if (categoryFromDB.isPresent()) {
			throw new ApiException("Category with this NAME already exists!");
		}
		
		Category savedCategory = repository.save(category);
		return  modelMapper.map(savedCategory, CategoryDTO.class);
	}

	@Override
	public CategoryDTO removeCategory(int categoryId) {

		Optional<Category> category = repository.findById(categoryId);

		if (!category.isPresent()) {
			throw new ResourceNotFoundException("Category not found with id: " + categoryId);
		}

		repository.deleteById(categoryId);
		return modelMapper.map(category, CategoryDTO.class);
	}

	@Override
	public CategoryDTO editCategory(CategoryDTO categoryDTO, int categoryId) {

		Category category = modelMapper.map(categoryDTO, Category.class);
		Optional<Category> existingCategory = repository.findById(categoryId);

		if (!existingCategory.isPresent()) {
			throw new ResourceNotFoundException("Category not found with id: " + categoryId);
		}

		Category updatedCategory = existingCategory.get();
		updatedCategory.setCategoryName(category.getCategoryName());
		repository.save(updatedCategory);

		return modelMapper.map(updatedCategory, CategoryDTO.class);
	}

}
