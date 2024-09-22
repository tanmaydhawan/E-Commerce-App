package com.springboot.ecommerce.service;

import com.springboot.ecommerce.entity.Product;
import com.springboot.ecommerce.payload.ProductDTO;
import com.springboot.ecommerce.payload.ProductResponse;

public interface ProductService {

	ProductDTO addProduct(Product product, Integer categoryId);

	ProductResponse getAllProducts();

	ProductResponse searchByCategory(Integer categoryId);

}
