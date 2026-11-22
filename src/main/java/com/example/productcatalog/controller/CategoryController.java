package com.example.productcatalog.controller;

import com.example.productcatalog.service.ICategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final ICategoryService categoryService;

    public CategoryController(ICategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/avgPrice/{name}")
    public ResponseEntity<Double> getAvgPriceOfCategory(@PathVariable String name) {
        Double price = categoryService.getAvgPriceForCategory(name);

        return new ResponseEntity<>(price, HttpStatus.OK);
    }
}
