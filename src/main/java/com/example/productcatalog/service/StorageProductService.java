package com.example.productcatalog.service;

import com.example.productcatalog.dto.ProductDto;
import com.example.productcatalog.model.Product;
import com.example.productcatalog.repository.ProductRepository;
import com.example.productcatalog.utility.Utilities;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
@RequiredArgsConstructor
public class StorageProductService implements IProductService{

    private final ProductRepository productRepository;

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product createProduct(ProductDto productDto) {
        Product product = Utilities.productDtoToProduct(productDto);
        return null;
    }

    @Override
    public boolean deleteProductById(Long id) {
        return false;
    }
}
