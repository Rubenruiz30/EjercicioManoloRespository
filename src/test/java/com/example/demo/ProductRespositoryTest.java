package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

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
    void testSaveProduct() {

        Product product = new Product(
                "Camiseta",
                "M",
                "20",
                10
        );

        Product savedProduct = productRepository.save(product);

        assertNotNull(savedProduct);
        assertNotNull(savedProduct.getId());

        assertEquals("Camiseta", savedProduct.getName());
        assertEquals("M", savedProduct.getSize());
        assertEquals("20", savedProduct.getPrice());
        assertEquals(10, savedProduct.getStock());
    }

    @Test
    void testFindProductById() {

        Product product = new Product(
                "Pantalon",
                "L",
                "35",
                5
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
    }

    @Test
    void testFindAllProducts() {

        Product product1 = new Product(
                "Camiseta",
                "M",
                "20",
                10
        );

        Product product2 = new Product(
                "Zapatillas",
                "42",
                "60",
                8
        );

        productRepository.save(product1);
        productRepository.save(product2);

        List<Product> products = productRepository.findAll();

        assertNotNull(products);
        assertEquals(2, products.size());
    }

    @Test
    void testUpdateProduct() {

        Product product = new Product(
                "Sudadera",
                "M",
                "30",
                12
        );

        Product savedProduct = productRepository.save(product);

        savedProduct.setName("Sudadera Nike");
        savedProduct.setSize("L");
        savedProduct.setPrice("45");
        savedProduct.setStock(20);

        Product updatedProduct = productRepository.save(savedProduct);

        Optional<Product> foundProduct = productRepository.findById(updatedProduct.getId());

        assertTrue(foundProduct.isPresent());

        Product result = foundProduct.get();

        assertEquals(updatedProduct.getId(), result.getId());
        assertEquals("Sudadera Nike", result.getName());
        assertEquals("L", result.getSize());
        assertEquals("45", result.getPrice());
        assertEquals(20, result.getStock());
    }

    @Test
    void testDeleteProduct() {

        Product product = new Product(
                "Gorra",
                "S",
                "15",
                6
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
                3
        );

        Product product2 = new Product(
                "Zapatos",
                "43",
                "70",
                4
        );

        productRepository.save(product1);
        productRepository.save(product2);

        long totalProducts = productRepository.count();

        assertEquals(2, totalProducts);
    }
}