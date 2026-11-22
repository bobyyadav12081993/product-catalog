package com.example.productcatalog.controller;

import java.util.List;

import com.example.productcatalog.dto.ProductPitchDto;
import com.example.productcatalog.service.PitchService;
import com.example.productcatalog.utility.Utilities;
import org.springframework.beans.factory.annotation.Qualifier;
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
    public List<Product> getProducts() {
        return null;
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
    public ProductDto createProduct(@RequestBody ProductDto productDto) {
        Product product = storageService.createProduct(productDto);
        return null;
    }
}