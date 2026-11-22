package com.example.productcatalog.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.example.productcatalog.dto.ProductPitchDto;
import com.example.productcatalog.service.PitchService;
import com.example.productcatalog.utility.Utilities;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.productcatalog.dto.CategoryDto;
import com.example.productcatalog.dto.ProductDto;
import com.example.productcatalog.model.Product;
import com.example.productcatalog.service.IProductService;

import lombok.RequiredArgsConstructor;

@RestController 
@RequestMapping("/products")
public class ProductController {

    private final IProductService storageService;
    private final IProductService aiAgentSupplierService;
    private final PitchService pitchService;

    public ProductController( @Qualifier("storageProductService") IProductService aiAgentSupplierService, IProductService storageService, PitchService pitchService) {
        this.aiAgentSupplierService = aiAgentSupplierService;
        this.pitchService = pitchService;
        this.storageService = storageService;
    }

    @GetMapping 
    public List<ProductDto> getProducts() {
        List<Product> products = storageService.getAllProducts();
        List<ProductDto> productDtos = new ArrayList<>();

        for(Product product: products) {
            productDtos.add(Utilities.productToProductDto(product));
        }

        return productDtos;
    }

    @GetMapping("/{id}")
    ResponseEntity<ProductDto> getProductById(@PathVariable("id") Long id) {
       if (id < 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Product product = storageService.getProductById(id);
        if (product == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }
        return new ResponseEntity<>(Utilities.productToProductDto(product), HttpStatus.OK);
    }

    @GetMapping("/{id}/pitch")
    public ResponseEntity<ProductPitchDto> generatePitch(@PathVariable Long id) {
        if(id<0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Product product = storageService.getProductById(id);
        if(product==null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ProductPitchDto productPitchDto = new ProductPitchDto();
        productPitchDto.setId(product.getId());
        productPitchDto.setPitch(pitchService.generatePitch(product));
        return new ResponseEntity<>(productPitchDto,HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        Product product = storageService.createProduct(productDto);
        return new ResponseEntity<>(Utilities.productToProductDto(product), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable Long id) {
        boolean isDeleted = storageService.deleteProductById(id);
        if (isDeleted) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/search")
    public PagedModel<ProductDto> getProductsInPriceRange(
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            Pageable pageable) {
        Page<ProductDto> products = storageService
                .getProductsInPriceRange(minPrice,maxPrice,pageable)
                .map(Utilities::productToProductDto);

        return new PagedModel<>(products);
    }
}