package com.productmgmt.service;

import com.productmgmt.model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private List<Product> productsList = new ArrayList<>();

    public List<Product> getAllProducts() {
        return productsList;
    }

    public Optional<Product> getProductById(long id) {
        return productsList.stream().filter(product -> product.getId() == id).findFirst();
    }

    public void addProduct(Product product) {
        productsList.add(product);
    }

    public boolean updateProduct(Long id, Product updatedProduct) {
        Optional<Product> existingProduct = getProductById(id);
        if(existingProduct.isPresent()) {
            existingProduct.get().setName(updatedProduct.getName());
            existingProduct.get().setPrice(updatedProduct.getPrice());
            return true;
        }
        return false;
    }

    public boolean deleteProduct(Long id) {
        Optional<Product> existingProduct = getProductById(id);
        if(existingProduct.isPresent()) {
            productsList.remove(existingProduct.get());
        return true;
        }
        return false;
    }
}
