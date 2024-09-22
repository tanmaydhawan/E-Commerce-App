package com.springboot.ecommerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.ecommerce.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

	public Optional<Category> findByCategoryName(String categoryName);

}
