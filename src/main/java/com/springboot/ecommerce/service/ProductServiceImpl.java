package com.springboot.ecommerce.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.ecommerce.entity.Category;
import com.springboot.ecommerce.entity.Product;
import com.springboot.ecommerce.exceptions.ApiException;
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
	
	@Value("${product.image}")
	private String path;

	@Override
	public ProductDTO addProduct(ProductDTO productDTO, Integer categoryId) {
		
		Product product = modelMapper.map(productDTO, Product.class);
		
		Category category = categoryRepository.findById(categoryId)
							.orElseThrow(()-> new ResourceNotFoundException("No Category Found!") );
		
		boolean isProductNotPresent = true;
		
		List<Product> products = category.getProducts();
		
		for(Product value : products) {
			if(value.getProductName().equals(productDTO.getProductName())) {
				isProductNotPresent = false;
				break;
			}
		}
		
		if (isProductNotPresent) {
			product.setCategory(category);
			product.setImage("default.png");

			double specialPrice = product.getPrice() - ((product.getDiscount() * 0.01) * product.getPrice());
			product.setSpecialPrice(specialPrice);

			Product savedProduct = productRepository.save(product);

			ProductDTO savedProductDTO = modelMapper.map(savedProduct, ProductDTO.class);

			return savedProductDTO;
		} else {
			throw new ApiException("Product name already exists!");
		}
	}

	@Override
	public ProductResponse getAllProducts(int pageNumber, int pageSize) {
		
		Pageable pageRequest = PageRequest.of(pageNumber, pageSize);
		
		Page<Product> productPage = productRepository.findAll(pageRequest);
		
		List<ProductDTO> productDTOList = productPage.stream()
													.map(product -> modelMapper.map(product, ProductDTO.class))
													.toList();
		
		ProductResponse response = new ProductResponse();
		response.setContent(productDTOList);
		response.setPageNumber(productPage.getNumber());
		response.setPageSize(productPage.getSize());
		response.setTotalElements(productPage.getTotalElements());
		response.setTotalPages(productPage.getTotalPages());
		response.setLastPage(productPage.isLast());
		
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

	@Override
	public ProductDTO editProductById(Long productId, ProductDTO productDTO) {
				
		Product existingProduct = productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException("No Product found with id: "+productId));
		
		existingProduct.setProductName(productDTO.getProductName());
		existingProduct.setDescription(productDTO.getDescription());
		existingProduct.setQuantity(productDTO.getQuantity());
		existingProduct.setPrice(productDTO.getPrice());
		existingProduct.setDiscount(productDTO.getDiscount());
		
		double specialPrice = productDTO.getPrice() - ((productDTO.getDiscount()*0.01) * productDTO.getPrice());
		existingProduct.setSpecialPrice(specialPrice);
		
		Product updatedProduct = productRepository.save(existingProduct);
		
		ProductDTO updatedProductDTO = modelMapper.map(updatedProduct, ProductDTO.class);
		
		return updatedProductDTO;
	}

	@Override
	public ProductDTO deleteProductById(Long productId) {

		Product existingProduct = productRepository.findById(productId).
				orElseThrow(()-> new ResourceNotFoundException("No Product found with id: "+productId));
		
		productRepository.deleteById(productId);
		
		return modelMapper.map(existingProduct, ProductDTO.class);

	}

	@Override
	public ProductDTO updateProductImage(Long productId, MultipartFile imageFile) throws IOException {

		//Get the product from db
		Product productFromDB = productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException("No product available!"));
				
		//Upload the image to server (/image)
		//Get the file name of the uploaded image
		String fileName = uploadImage(path, imageFile);
		
		//Updating the new file name to the product
		productFromDB.setImage(fileName);
		
		//Save the updated product
		Product updatedProduct = productRepository.save(productFromDB);
		
		//Return the updated product
		return modelMapper.map(updatedProduct, ProductDTO.class);
		
	}

	private String uploadImage(String path, MultipartFile imageFile) throws IOException {

		//Get the file name of the OG file
		String originalFileName = imageFile.getOriginalFilename(); 
		
		//generating a unique file name
		//tanmay.jpg -> 12345 -> 12345.jpg
		String randomId = UUID.randomUUID().toString();
		String fileName = randomId.concat(originalFileName.substring(originalFileName.lastIndexOf('.')));
		
		String filePath = path + File.separator + fileName;
		
		//Check if path exists else create
		File folder = new File(path);
		if(!folder.exists()) {
			folder.mkdir();
		}
		
		//Upload the file to server
		Files.copy(imageFile.getInputStream(), Paths.get(filePath));
		
		
		return fileName;
	}

}
