package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.Product;
import com.example.demo.service.ProductService;
import com.example.demo.service.productService;

/**
 * Controller used to manage product requests.
 * 
 * @author David
 * @author Rubén
 * 27 May 2026
 */
@RestController
@RequestMapping("/products")
@CrossOrigin // when the server recognize the client or user
public class ProductController {

    // dependency injection
    @Autowired
    private ProductService productService;

    @GetMapping
    public String test() {
        return "Hello";
    }

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable long id) {
        return productService.getProduct(id);
    }

    /**
     * @PostMapping Is used to map a handler to a unique post request
     * 
     * @param product
     * @return
     */
    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productService.saveProduct(product);
    }

    @GetMapping("/all")
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();

        products.add(new Product("Camiseta", "M", "20", 1));
        products.add(new Product("Pantalon", "L", "35", 2));

        return products;
    }

    /**
     * Modifies an existing product.
     * 
     * @param id Product id
     * @param product Updated product data
     * @return modified product
     */
    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable long id, @RequestBody Product product) {
        product.setId(id);
        return productService.saveProduct(product);
    }

    /**
     * Deletes a product by id.
     * 
     * @param id Product id
     */
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable long id) {
        productService.deleteProduct(id);
    }
}