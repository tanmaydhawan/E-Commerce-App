package com.springboot.ecommerce.controller;

import java.io.IOException;

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
import org.springframework.web.multipart.MultipartFile;

import com.springboot.ecommerce.config.AppConstants;
import com.springboot.ecommerce.payload.ProductDTO;
import com.springboot.ecommerce.payload.ProductResponse;
import com.springboot.ecommerce.service.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@PostMapping("/admin/category/{categoryId}/product")
	public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductDTO productDTO,
													@PathVariable Integer categoryId){
		
		ProductDTO createdProduct = productService.addProduct(productDTO,categoryId);
		
		return new ResponseEntity<ProductDTO>(createdProduct,HttpStatus.OK);
		
	}
	
	@GetMapping("/user/product")
	public ResponseEntity<ProductResponse> fetchAllProducts(@RequestParam(defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize){
				
		return new ResponseEntity<ProductResponse>(productService.getAllProducts(pageNumber, pageSize), HttpStatus.OK);
		
	}
	
	@GetMapping("/user/category/{categoryId}/product")
	public ResponseEntity<ProductResponse> getAllProductsByCategory(@PathVariable Integer categoryId){
		
		ProductResponse productResponse = productService.searchByCategory(categoryId);
		return new ResponseEntity<ProductResponse>(productResponse, HttpStatus.OK);
		
	}
	
	@GetMapping("/user/product/keyword/{keyword}")
	public ResponseEntity<ProductResponse> searchAllProductsByKeyword(@PathVariable String keyword){
		
		ProductResponse productResponse = productService.searchProductsByKeyword(keyword);
		return new ResponseEntity<ProductResponse>(productResponse, HttpStatus.OK);
		
	}
	
	@PutMapping("/admin/product/{productId}")
	public ResponseEntity<ProductDTO> editProduct(@Valid @RequestBody ProductDTO productDTO, @PathVariable Long productId){
		
		ProductDTO editedProductDTO = productService.editProductById(productId, productDTO);
		return new ResponseEntity<ProductDTO>(editedProductDTO,HttpStatus.OK);
	}
	
	@DeleteMapping("/admin/product/{productId}")
	public ResponseEntity<ProductDTO>deleteProduct(@PathVariable Long productId){
		
		ProductDTO deletedProductDTO = productService.deleteProductById(productId);
		return new ResponseEntity<ProductDTO>(deletedProductDTO, HttpStatus.OK);	
	}
	
	@PutMapping("/admin/product/{productId}/image")
	public ResponseEntity<ProductDTO> uploadImage(@PathVariable Long productId,
													@RequestParam ("image") MultipartFile imageFile) throws IOException{
		ProductDTO updatedProductDTO = productService.updateProductImage(productId,imageFile);
		return new ResponseEntity<ProductDTO>(updatedProductDTO, HttpStatus.OK);
		
	}
	

}
