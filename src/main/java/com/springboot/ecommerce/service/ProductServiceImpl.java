package com.springboot.ecommerce.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.ecommerce.entity.Category;
import com.springboot.ecommerce.entity.Product;
import com.springboot.ecommerce.exceptions.ResourceNotFoundException;
import com.springboot.ecommerce.payload.ProductDTO;
import com.springboot.ecommerce.repository.CategoryRepository;
import com.springboot.ecommerce.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public ProductDTO addProduct(Product product, Integer categoryId) {
		
		Category category = categoryRepository.findById(categoryId)
							.orElseThrow(()-> new ResourceNotFoundException("No Category Found!") );
		
		product.setCategory(category);
		product.setImage("default.png");
		
		double specialPrice = product.getPrice() - ((product.getDiscount()*0.01) * product.getPrice());
		product.setSpecialPrice(specialPrice);
		
		Product savedProduct = productRepository.save(product);
		
		ProductDTO productDTO = modelMapper.map(savedProduct, ProductDTO.class);
		
		return productDTO;
	}

}
