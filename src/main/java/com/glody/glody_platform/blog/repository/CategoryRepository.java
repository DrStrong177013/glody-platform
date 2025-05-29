package com.glody.glody_platform.blog.repository;

import com.glody.glody_platform.blog.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByNameIgnoreCaseAndIsDeletedFalse(String name);
}
