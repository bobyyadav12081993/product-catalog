package com.example.productcatalog.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity 
@Data 
public class Category extends BaseEntity {
    private String name;
    private String description;
    @OneToMany(mappedBy = "category")
    private List<Product> products;
}