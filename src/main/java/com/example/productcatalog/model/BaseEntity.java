package com.example.productcatalog.model;

import com.example.productcatalog.enums.State;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@MappedSuperclass 
@Data 
public abstract class BaseEntity {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date createdAt;
    private Date lastUpdatedAt;

    @Enumerated(value = EnumType.STRING)
    private State state;

}
