package com.example.productcatalog.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.productcatalog.dto.CategoryDto;
import com.example.productcatalog.dto.ProductDto;
import com.example.productcatalog.model.Product;
import com.example.productcatalog.service.IProductService;

import lombok.RequiredArgsConstructor;

@RestController 
@RequestMapping("/products")
@RequiredArgsConstructor 
public class ProductController {

    private final IProductService productService;

    @GetMapping 
    public List<Product> getProducts() {
        return null;
    }

    @GetMapping("/{id}")
    ResponseEntity<ProductDto> getProductById(@PathVariable("id") Long id) {
        Product product = productService.getProductById(id);
        return new ResponseEntity<>(from(product),HttpStatus.OK);
    }

    private ProductDto from(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setDescription(product.getCategory().getDescription());
        categoryDto.setId(product.getCategory().getId());
        categoryDto.setName(product.getCategory().getName());

        productDto.setCategory(categoryDto);
        return productDto;
    }
}