package com.example.productcatalog.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import com.example.productcatalog.dto.DummyJsonProductDto;
import com.example.productcatalog.model.Category;
import com.example.productcatalog.model.Product;

import lombok.RequiredArgsConstructor;

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
