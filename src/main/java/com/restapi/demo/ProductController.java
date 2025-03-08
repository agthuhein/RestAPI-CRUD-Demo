package com.restapi.demo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@RestController
public class ProductController {

    private static final String FILE_PATH = "src/main/resources/products.json";
    private ObjectMapper objectMapper = new ObjectMapper();


    @PostMapping("/products")
    public ResponseEntity<List<Product>> createProduct(@RequestBody Product product) throws IOException {
        //Logic to store the product permanently (db, cloud, file)
        List<Product> products = readProductsFromFile();
        products.add(product);
        writeProductToFile(products);
        return new ResponseEntity<>(products, HttpStatus.CREATED);
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts() throws IOException {
        List<Product> products = readProductsFromFile();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable long id) throws IOException {
        List<Product> products = readProductsFromFile();
        Optional<Product> product = products.stream().filter(p -> p.getId() == id).findFirst();
        if (product.isPresent()) {
            return new ResponseEntity<>(product.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        /*
        for(Product p : products) {
            if(p.getId() == id) {
                return new ResponseEntity<>(p, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        */
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") long id, @RequestBody Product updatedProduct)
            throws IOException {
        List<Product> products = readProductsFromFile();
        for(Product product : products) {
            if(product.getId() == id) {
                //this is where the updating
                product.setName(updatedProduct.getName());
                product.setPrice(updatedProduct.getPrice());
                writeProductToFile(products);
                return new ResponseEntity<>(product, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") long id) throws IOException {
        List<Product> products = readProductsFromFile();
        Iterator<Product> iterator = products.iterator();

        while (iterator.hasNext()) {
            Product product = iterator.next();
            if(product.getId() == id) {
                iterator.remove();
                writeProductToFile(products);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    private void writeProductToFile(List<Product> products) throws IOException {
        objectMapper.writeValue(new File(FILE_PATH), products);
    }
    private List<Product> readProductsFromFile() throws IOException {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        return objectMapper.readValue(file,
                new TypeReference<List<Product>>(){
                });
    }
}
