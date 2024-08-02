package com.springboot.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.springboot.ecommerce.model.Category;
import com.springboot.ecommerce.service.CategoryService;

@RestController
@RequestMapping("/api")
public class CategoryController {

	@Autowired
	CategoryService service;
	
	@GetMapping("/user/category")
	public ResponseEntity<List<Category>> getAllCategory() {
		return ResponseEntity.ok(service.fetchAllCategories()); 
	}

	@PostMapping("/admin/category")
	public ResponseEntity<String> addCategory(@RequestBody Category category) {
			String status =  service.postCategory(category);
			return ResponseEntity.status(HttpStatus.CREATED).body(status);
	}
	
	@PutMapping("/admin/category/{categoryId}")
	public ResponseEntity<String> editCategory(@RequestBody Category category,
			@PathVariable int categoryId){
		try {
		String status = service.editCategory(category,categoryId);
		return new ResponseEntity<>(status,HttpStatus.OK);
		}
		catch (ResponseStatusException e) {
			return new ResponseEntity<>(e.getReason(), e.getStatusCode());
		}
	}
	
	@DeleteMapping("/admin/category/{categoryId}")
	public ResponseEntity<String> deleteCatgory(@PathVariable int categoryId) {
		try {
		String status = service.removeCategory(categoryId);
		return new ResponseEntity<>(status,HttpStatus.OK);
		
	} catch (ResponseStatusException e) {
		return new ResponseEntity<>(e.getReason(),e.getStatusCode());
		}
	}

}
