package com.springboot.ecommerce.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.springboot.ecommerce.payload.ProductDTO;
import com.springboot.ecommerce.payload.ProductResponse;

public interface ProductService {

	ProductDTO addProduct(ProductDTO productDTO, Integer categoryId);

	ProductResponse getAllProducts(int pageNumber, int pageSize);

	ProductResponse searchByCategory(Integer categoryId);

	ProductResponse searchProductsByKeyword(String keyword);

	ProductDTO editProductById(Long productId, ProductDTO productDTO);

	ProductDTO deleteProductById(Long productId);

	ProductDTO updateProductImage(Long productId, MultipartFile imageFile) throws IOException;

}
