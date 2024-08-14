package com.springboot.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.ecommerce.config.AppConstants;
import com.springboot.ecommerce.payload.CategoryDTO;
import com.springboot.ecommerce.payload.CategoryResponse;
import com.springboot.ecommerce.service.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class CategoryController {

	@Autowired
	CategoryService service;

	@GetMapping("/user/category")
	public ResponseEntity<CategoryResponse> getAllCategory(
			@RequestParam(defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize) {
		return ResponseEntity.ok(service.fetchAllCategories(pageNumber, pageSize));
	}
	
	@GetMapping("/user/category/{categoryId}")
	public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable int categoryId){
		CategoryDTO returnedCategoryDTO = service.fetchCategorybyId(categoryId);
		return new ResponseEntity<CategoryDTO>(returnedCategoryDTO, HttpStatus.OK);
	}

	@PostMapping("/admin/category")
	public ResponseEntity<CategoryDTO> addCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
		CategoryDTO categoryDtoResponse = service.postCategory(categoryDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(categoryDtoResponse);
	}

	@PutMapping("/admin/category/{categoryId}")
	public ResponseEntity<CategoryDTO> editCategory(@RequestBody CategoryDTO categoryDTO, @PathVariable int categoryId) {
		CategoryDTO editedCategoryDTO = service.editCategory(categoryDTO, categoryId);
		return new ResponseEntity<>(editedCategoryDTO, HttpStatus.OK);
	}

	@DeleteMapping("/admin/category/{categoryId}")
	public ResponseEntity<CategoryDTO> deleteCatgory(@PathVariable int categoryId) {
		CategoryDTO deletedCategoryDTO = service.removeCategory(categoryId);
		return new ResponseEntity<>(deletedCategoryDTO, HttpStatus.OK);
	}

}
