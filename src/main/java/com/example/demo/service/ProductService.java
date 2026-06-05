package com.example.demo.service;

import com.example.demo.domain.Product;

public interface ProductService {

    Product getProduct(long id);

    Product saveProduct(Product product);

    void deleteProduct(long id);
}