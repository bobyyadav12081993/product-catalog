package com.example.productcatalog.repository;

import com.example.productcatalog.enums.State;
import com.example.productcatalog.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByIdAndState(Long id, State state);

    @EntityGraph(attributePaths = {"category"})
    @Query("SELECT p FROM Product p WHERE " +
            "(:minPrice IS NULL OR p.price >= :minPrice) AND " +
            "(:maxPrice IS NULL OR p.price <= :maxPrice)" +
            " AND p.state=:state")
    Page<Product> findByPriceRange(BigDecimal minPrice,
                                             BigDecimal maxPrice,
                                             State state,
                                             Pageable pageable);

    @Query("SELECT p from Product p LEFT JOIN FETCH p.category c")
    List<Product> findAllProducts();
}
