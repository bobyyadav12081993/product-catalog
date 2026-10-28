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
       if (id < 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Product product = productService.getProductById(id);
        if (product == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }
        return new ResponseEntity<>(from(product), HttpStatus.OK);
    }

    private ProductDto from(Product product) {
        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setImageUrl(product.getImageUrl());

        if (product.getCategory() != null) {
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setId(product.getCategory().getId());
            categoryDto.setName(product.getCategory().getName());
            categoryDto.setDescription(product.getCategory().getDescription());
            dto.setCategory(categoryDto);
        }
        return dto;
    }

    @GetMapping("/{id}/pitch")
    public ResponseEntity<ProductPitchDto> generatePitch(@PathVariable Long id) {
        if(id<0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Product product = productService.getProductById(id);
        if(product==null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ProductPitchDto productPitchDto = new ProductPitchDto();
        productPitchDto.setId(product.getId());
        productPitchDto.setPitch(pitchService.generatePitch(product));
        return new ResponseEntity<>(productPitchDto,HttpStatus.OK);

    }
}