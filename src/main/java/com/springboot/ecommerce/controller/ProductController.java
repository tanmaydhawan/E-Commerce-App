package com.springboot.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.ecommerce.entity.Product;
import com.springboot.ecommerce.payload.ProductDTO;
import com.springboot.ecommerce.service.ProductService;

@RestController
@RequestMapping("/api")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@PostMapping("/admin/category/{categoryId}/product")
	public ResponseEntity<ProductDTO> createProduct(@RequestBody Product product,
													@PathVariable Integer categoryId){
		
		ProductDTO createdProduct = productService.addProduct(product,categoryId);
		
		return new ResponseEntity<ProductDTO>(createdProduct,HttpStatus.OK);
		
	}

}
