package com.example.demo.service;

import org.springframework.data.domain.Page;

import com.example.demo.domain.Product;

public interface ProductService {

    Product getProduct(long id);

    Product saveProduct(Product product);

    void deleteProduct(long id);

    Page<Product> getAllProducts(int page, int size);

    Page<Product> searchProducts(String type, String value, int page, int size);
}