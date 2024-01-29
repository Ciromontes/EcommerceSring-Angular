package com.codewithProjects.ecom.services.admin.category;

import com.codewithProjects.ecom.dto.CategoryDto;
import com.codewithProjects.ecom.entity.Category;

import java.util.List;

public interface CategoryService {
    Category createcategory(CategoryDto categoryDto);

    List<Category> getAllCategories();
}