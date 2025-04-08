package com.example.javaspringbootnavy1.repository;

import com.example.javaspringbootnavy1.modal.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByCategoryId(String categoryId);
}
