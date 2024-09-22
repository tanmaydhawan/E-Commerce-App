package com.springboot.ecommerce.service;

import com.springboot.ecommerce.entity.Product;
import com.springboot.ecommerce.payload.ProductDTO;

public interface ProductService {

	ProductDTO addProduct(Product product, Integer categoryId);

}
