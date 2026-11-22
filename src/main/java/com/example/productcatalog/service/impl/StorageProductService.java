package com.example.productcatalog.service.impl;

import com.example.productcatalog.dto.ProductDto;
import com.example.productcatalog.enums.State;
import com.example.productcatalog.model.Category;
import com.example.productcatalog.model.Product;
import com.example.productcatalog.repository.CategoryRepository;
import com.example.productcatalog.repository.ProductRepository;
import com.example.productcatalog.service.IProductService;
import com.example.productcatalog.utility.Utilities;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Primary
@RequiredArgsConstructor
public class StorageProductService implements IProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAllProducts();
    }

    @Override
    public Page<Product> getProductsInPriceRange(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable) {
        return productRepository.findByPriceRange(minPrice, maxPrice, State.ACTIVE, pageable);
    }

    @Override
    public Product createProduct(ProductDto productDto) {
        Product product = Utilities.productDtoToProduct(productDto);
        product.setCategory(findOrCreateCategory(product.getCategory()));
        product.setCreatedAt(new Date());
        product.setLastUpdatedAt(new Date());
        product.setState(State.ACTIVE);
        return productRepository.save(product);
    }

    @Override
    public boolean deleteProductById(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        if(product==null) return false;
        product.setState(State.INACTIVE);
        product.setLastUpdatedAt(new Date());
        productRepository.save(product);
        return true;
    }

    private Category findOrCreateCategory(Category category) {
        if (category == null) {
            return null;
        }

        Optional<Category> existing = categoryRepository.findByName(category.getName());
        if (existing.isPresent()) {
            return existing.get();
        }

        Date now = new Date();
        category.setId(null);
        category.setCreatedAt(now);
        category.setLastUpdatedAt(now);
        return category;
    }

}
