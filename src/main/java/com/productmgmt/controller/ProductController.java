package com.productmgmt.controller;

import com.productmgmt.model.Product;
import com.productmgmt.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productmgmt")
public class ProductController {
    private final ProductService productService;

    //Constructor based dependency injection of ProductService
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @PostMapping
    public ResponseEntity<String> addProduct(@RequestBody Product product) {
        productService.addProduct(product);
        return new ResponseEntity<>("Product added successfully", HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable long id, @RequestBody Product product) {
        if(productService.updateProduct(id, product)) {
            return new ResponseEntity<>("Product updated successfully", HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable long id) {
        if(productService.deleteProduct(id)) {
            return new ResponseEntity<>("Product deleted successfully", HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        }
    }
}
