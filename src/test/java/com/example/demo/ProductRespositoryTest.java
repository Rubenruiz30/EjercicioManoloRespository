package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.example.demo.domain.Product;
import com.example.demo.repository.ProductRepository;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void cleanDatabase() {
        productRepository.deleteAll();
    }

    @Test
    void testSaveProductWithImageUrl() {

        Product product = new Product(
                "Camiseta",
                "M",
                "20",
                10,
                "https://images.unsplash.com/photo-1521572163474-6864f9cf17ab"
        );

        Product savedProduct = productRepository.save(product);

        assertNotNull(savedProduct);
        assertNotNull(savedProduct.getId());

        assertEquals("Camiseta", savedProduct.getName());
        assertEquals("M", savedProduct.getSize());
        assertEquals("20", savedProduct.getPrice());
        assertEquals(10, savedProduct.getStock());
        assertEquals("https://images.unsplash.com/photo-1521572163474-6864f9cf17ab", savedProduct.getImageUrl());
    }

    @Test
    void testSaveProductWithoutImageUrl() {

        Product product = new Product(
                "Producto sin foto",
                "L",
                "15",
                4
        );

        Product savedProduct = productRepository.save(product);

        assertNotNull(savedProduct);
        assertNotNull(savedProduct.getId());

        assertEquals("Producto sin foto", savedProduct.getName());
        assertEquals("L", savedProduct.getSize());
        assertEquals("15", savedProduct.getPrice());
        assertEquals(4, savedProduct.getStock());
        assertNull(savedProduct.getImageUrl());
    }

    @Test
    void testFindProductById() {

        Product product = new Product(
                "Pantalon",
                "L",
                "35",
                5,
                "https://images.unsplash.com/photo-1542272604-787c3835535d"
        );

        Product savedProduct = productRepository.save(product);

        Optional<Product> foundProduct = productRepository.findById(savedProduct.getId());

        assertTrue(foundProduct.isPresent());

        Product result = foundProduct.get();

        assertEquals(savedProduct.getId(), result.getId());
        assertEquals("Pantalon", result.getName());
        assertEquals("L", result.getSize());
        assertEquals("35", result.getPrice());
        assertEquals(5, result.getStock());
        assertEquals("https://images.unsplash.com/photo-1542272604-787c3835535d", result.getImageUrl());
    }

    @Test
    void testFindAllProductsWithPagination() {

        Product product1 = new Product(
                "Camiseta",
                "M",
                "20",
                10,
                "https://images.unsplash.com/photo-1521572163474-6864f9cf17ab"
        );

        Product product2 = new Product(
                "Zapatillas",
                "42",
                "60",
                8,
                "https://images.unsplash.com/photo-1542291026-7eec264c27ff"
        );

        productRepository.save(product1);
        productRepository.save(product2);

        Pageable pageable = PageRequest.of(0, 20);

        Page<Product> products = productRepository.findAll(pageable);

        assertNotNull(products);
        assertEquals(2, products.getContent().size());
        assertEquals(2, products.getTotalElements());
    }

    @Test
    void testUpdateProduct() {

        Product product = new Product(
                "Sudadera",
                "M",
                "30",
                12,
                "https://images.unsplash.com/photo-1556821840-3a63f95609a7"
        );

        Product savedProduct = productRepository.save(product);

        savedProduct.setName("Sudadera Nike");
        savedProduct.setSize("L");
        savedProduct.setPrice("45");
        savedProduct.setStock(20);
        savedProduct.setImageUrl("https://images.unsplash.com/photo-1556821840-3a63f95609a7");

        Product updatedProduct = productRepository.save(savedProduct);

        Optional<Product> foundProduct = productRepository.findById(updatedProduct.getId());

        assertTrue(foundProduct.isPresent());

        Product result = foundProduct.get();

        assertEquals(updatedProduct.getId(), result.getId());
        assertEquals("Sudadera Nike", result.getName());
        assertEquals("L", result.getSize());
        assertEquals("45", result.getPrice());
        assertEquals(20, result.getStock());
        assertEquals("https://images.unsplash.com/photo-1556821840-3a63f95609a7", result.getImageUrl());
    }

    @Test
    void testDeleteProduct() {

        Product product = new Product(
                "Gorra",
                "S",
                "15",
                6,
                "https://images.unsplash.com/photo-1521369909029-2afed882baee"
        );

        Product savedProduct = productRepository.save(product);

        Long id = savedProduct.getId();

        assertTrue(productRepository.existsById(id));

        productRepository.deleteById(id);

        assertFalse(productRepository.existsById(id));
    }

    @Test
    void testCountProducts() {

        Product product1 = new Product(
                "Abrigo",
                "XL",
                "80",
                3,
                "https://images.unsplash.com/photo-1539533018447-63fcce2678e3"
        );

        Product product2 = new Product(
                "Zapatos",
                "43",
                "70",
                4,
                "https://images.unsplash.com/photo-1549298916-b41d501d3772"
        );

        productRepository.save(product1);
        productRepository.save(product2);

        long totalProducts = productRepository.count();

        assertEquals(2, totalProducts);
    }

    @Test
    void testFindByNameContainingIgnoreCase() {

        Product product1 = new Product(
                "Camiseta Azul",
                "M",
                "20",
                10,
                "https://images.unsplash.com/photo-1521572163474-6864f9cf17ab"
        );

        Product product2 = new Product(
                "Pantalon Negro",
                "L",
                "35",
                5,
                "https://images.unsplash.com/photo-1542272604-787c3835535d"
        );

        productRepository.save(product1);
        productRepository.save(product2);

        Pageable pageable = PageRequest.of(0, 20);

        Page<Product> result = productRepository.findByNameContainingIgnoreCase("camiseta", pageable);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals("Camiseta Azul", result.getContent().get(0).getName());
        assertEquals("https://images.unsplash.com/photo-1521572163474-6864f9cf17ab", result.getContent().get(0).getImageUrl());
    }

    @Test
    void testFindBySizeContainingIgnoreCase() {

        Product product1 = new Product(
                "Camiseta",
                "M",
                "20",
                10,
                "https://images.unsplash.com/photo-1521572163474-6864f9cf17ab"
        );

        Product product2 = new Product(
                "Pantalon",
                "L",
                "35",
                5,
                "https://images.unsplash.com/photo-1542272604-787c3835535d"
        );

        productRepository.save(product1);
        productRepository.save(product2);

        Pageable pageable = PageRequest.of(0, 20);

        Page<Product> result = productRepository.findBySizeContainingIgnoreCase("L", pageable);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals("Pantalon", result.getContent().get(0).getName());
    }

    @Test
    void testFindByPriceContainingIgnoreCase() {

        Product product1 = new Product(
                "Camiseta",
                "M",
                "20",
                10,
                "https://images.unsplash.com/photo-1521572163474-6864f9cf17ab"
        );

        Product product2 = new Product(
                "Sudadera",
                "L",
                "35",
                5,
                "https://images.unsplash.com/photo-1556821840-3a63f95609a7"
        );

        productRepository.save(product1);
        productRepository.save(product2);

        Pageable pageable = PageRequest.of(0, 20);

        Page<Product> result = productRepository.findByPriceContainingIgnoreCase("35", pageable);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals("Sudadera", result.getContent().get(0).getName());
    }

    @Test
    void testFindByStock() {

        Product product1 = new Product(
                "Camiseta",
                "M",
                "20",
                10,
                "https://images.unsplash.com/photo-1521572163474-6864f9cf17ab"
        );

        Product product2 = new Product(
                "Gorra",
                "S",
                "15",
                6,
                "https://images.unsplash.com/photo-1521369909029-2afed882baee"
        );

        productRepository.save(product1);
        productRepository.save(product2);

        Pageable pageable = PageRequest.of(0, 20);

        Page<Product> result = productRepository.findByStock(6, pageable);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals("Gorra", result.getContent().get(0).getName());
    }
}