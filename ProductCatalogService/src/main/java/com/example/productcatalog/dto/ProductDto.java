package com.example.productcatalog.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter 
public class ProductDto {
    private long id;
    private String name;
    private String description;
    private BigDecimal price;
    private CategoryDto category;
}
