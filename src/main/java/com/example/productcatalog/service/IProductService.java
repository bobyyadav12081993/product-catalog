package com.example.productcatalog.service;

import com.example.productcatalog.dto.ProductDto;
import com.example.productcatalog.model.Product;

public interface IProductService {
    Product getProductById(Long id);
    Product createProduct(ProductDto productDto);
    boolean deleteProductById(Long id);
}
