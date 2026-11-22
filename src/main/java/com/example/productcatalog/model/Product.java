package com.example.productcatalog.model;

import java.math.BigDecimal;

import jakarta.persistence.*;
import lombok.Data;

@Entity 
@Data
@Table(indexes = @Index(name = "idx_product_name", columnList = "name"))
public class Product extends BaseEntity {
    private String name;
    private String description;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(precision = 19,scale = 3)
    private BigDecimal price;

    private String imageUrl;

}
