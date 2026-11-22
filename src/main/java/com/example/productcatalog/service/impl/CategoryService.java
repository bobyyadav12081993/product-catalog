package com.example.productcatalog.service.impl;

import com.example.productcatalog.repository.CategoryRepository;
import com.example.productcatalog.service.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public Double getAvgPriceForCategory(String category) {
        return categoryRepository.findAvgPriceForCategory(category);
    }
}
