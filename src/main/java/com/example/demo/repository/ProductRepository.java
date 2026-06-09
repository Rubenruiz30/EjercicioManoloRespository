package com.example.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<Product> findBySizeContainingIgnoreCase(String size, Pageable pageable);

    Page<Product> findByPriceContainingIgnoreCase(String price, Pageable pageable);

    Page<Product> findByStock(int stock, Pageable pageable);
}