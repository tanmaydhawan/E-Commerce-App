package com.springboot.ecommerce.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.ecommerce.entity.Category;
import com.springboot.ecommerce.entity.Product;
import com.springboot.ecommerce.exceptions.ResourceNotFoundException;
import com.springboot.ecommerce.payload.ProductDTO;
import com.springboot.ecommerce.payload.ProductResponse;
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

	@Override
	public ProductResponse getAllProducts() {

		List<Product> productList = productRepository.findAll();
		
		List<ProductDTO> productDTOList = productList.stream()
													.map(product -> modelMapper.map(product, ProductDTO.class))
													.toList();
		
		ProductResponse response = new ProductResponse();
		response.setContent(productDTOList);
		return response;
	}

	@Override
	public ProductResponse searchByCategory(Integer categoryId) {
		
		Optional<Category> category = categoryRepository.findById(categoryId);
		
		if(category.isEmpty()) {
			throw new ResourceNotFoundException("No category found with id :"+categoryId);
			}
		List<Product> productList = productRepository.findByCategory(category);
		
		List<ProductDTO> productDTOList = productList.stream()
													.map(product -> modelMapper.map(product, ProductDTO.class))
													.toList();
		
		ProductResponse response = new ProductResponse();
		response.setContent(productDTOList);
		return response;
	}

	@Override
	public ProductResponse searchProductsByKeyword(String keyword) {
		
		List<Product> productList = productRepository.findByProductNameLikeIgnoreCase("%"+keyword+"%");
				
		List<ProductDTO> productDTOList = productList.stream()
													.map(product -> modelMapper.map(product, ProductDTO.class))
													.toList();
				
		ProductResponse response = new ProductResponse();
		response.setContent(productDTOList);
		return response;
	}

}
