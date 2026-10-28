package com.example.productcatalog.service;

import com.example.productcatalog.model.Product;

public interface IProductService {
    Product getProductById(Long id);
}
