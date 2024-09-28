package com.springboot.ecommerce.service;

import com.springboot.ecommerce.payload.ProductDTO;
import com.springboot.ecommerce.payload.ProductResponse;

public interface ProductService {

	ProductDTO addProduct(ProductDTO productDTO, Integer categoryId);

	ProductResponse getAllProducts();

	ProductResponse searchByCategory(Integer categoryId);

	ProductResponse searchProductsByKeyword(String keyword);

	ProductDTO editProductById(Long productId, ProductDTO productDTO);

	ProductDTO deleteProductById(Long productId);

}
