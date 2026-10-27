package com.example.productcatalog.model;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import lombok.Data;

@Entity 
@Data 
public class Product extends BaseEntity {
    private String name;
    private String description;
    private Category category;
    private BigDecimal price;
    private String imageUrl;

}
