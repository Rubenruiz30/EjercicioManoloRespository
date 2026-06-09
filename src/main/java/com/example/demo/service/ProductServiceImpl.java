package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Product;
import com.example.demo.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product getProduct(long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }

    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Page<Product> getAllProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findAll(pageable);
    }

    @Override
    public Page<Product> searchProducts(String type, String value, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        if (type == null || value == null || value.trim().isEmpty()) {
            return productRepository.findAll(pageable);
        }

        String cleanValue = value.trim();

        switch (type.toLowerCase()) {

            case "id":
                try {
                    Long id = Long.parseLong(cleanValue);

                    return productRepository.findById(id)
                            .map(product -> new PageImpl<>(List.of(product), pageable, 1))
                            .orElse(new PageImpl<>(List.of(), pageable, 0));

                } catch (NumberFormatException e) {
                    return new PageImpl<>(List.of(), pageable, 0);
                }

            case "name":
                return productRepository.findByNameContainingIgnoreCase(cleanValue, pageable);

            case "size":
                return productRepository.findBySizeContainingIgnoreCase(cleanValue, pageable);

            case "price":
                return productRepository.findByPriceContainingIgnoreCase(cleanValue, pageable);

            case "stock":
                try {
                    int stock = Integer.parseInt(cleanValue);
                    return productRepository.findByStock(stock, pageable);
                } catch (NumberFormatException e) {
                    return new PageImpl<>(List.of(), pageable, 0);
                }

            default:
                return productRepository.findAll(pageable);
        }
    }
}