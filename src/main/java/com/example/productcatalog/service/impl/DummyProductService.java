package com.example.productcatalog.service.impl;

import com.example.productcatalog.dto.DummyJsonProductListDto;
import com.example.productcatalog.dto.ProductDto;
import com.example.productcatalog.service.IProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import com.example.productcatalog.dto.DummyJsonProductDto;
import com.example.productcatalog.model.Category;
import com.example.productcatalog.model.Product;

import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service 
@RequiredArgsConstructor 
public class DummyProductService implements IProductService {

    private final RestClient restClient;

    @Override
    public Product getProductById(Long id) {
        try {

            DummyJsonProductDto response = restClient.get().uri("https://dummyjson.com/products/{id}", id)
                    .retrieve()
                    .body(DummyJsonProductDto.class);

            return from(response);
        } catch (HttpClientErrorException.NotFound e) {
            return null;
        }
    }

    @Override
    public Product createProduct(ProductDto productDto) {
        return null;
    }

    @Override
    public boolean deleteProductById(Long id) {
        return false;
    }

    @Override
    public List<Product> getAllProducts() {
        DummyJsonProductListDto response = restClient.get().uri(
                "https://dummyjson.com/products?limit=0")
                .retrieve()
                .body(DummyJsonProductListDto.class);

        List<Product> products = new ArrayList<>();
        if(response != null) {
            for (DummyJsonProductDto dto : response.getProducts()) {
                products.add(from(dto));
            }
        }

        return products;
    }

    @Override
    public Page<Product> getProductsInPriceRange(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable) {
        return null;
    }

    private Product from(DummyJsonProductDto dto) {
        Product product = new Product();
        product.setId(dto.getId());
        product.setName(dto.getTitle());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setImageUrl(dto.getThumbnail());

        Category category = new Category();
        category.setName(dto.getCategory());
        product.setCategory(category);
        return product;
    }
    
}
