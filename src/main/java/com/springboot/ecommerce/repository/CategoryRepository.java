package com.springboot.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.ecommerce.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
