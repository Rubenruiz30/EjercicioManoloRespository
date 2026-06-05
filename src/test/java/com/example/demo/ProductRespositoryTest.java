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
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import com.example.demo.domain.Product;
import com.example.demo.repository.ProductRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
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
                1001L
        );

        Product savedProduct = productRepository.save(product);

        assertNotNull(savedProduct);
        assertNotNull(savedProduct.getId());

        assertEquals(1001L, savedProduct.getId());
        assertEquals("Camiseta", savedProduct.getName());
        assertEquals("M", savedProduct.getSize());
        assertEquals("20", savedProduct.getPrice());
    }

    @Test
    void testFindProductById() {

        Product product = new Product(
                "Pantalon",
                "L",
                "35",
                1002L
        );

        productRepository.save(product);

        Optional<Product> foundProduct = productRepository.findById(1002L);

        assertTrue(foundProduct.isPresent());

        Product result = foundProduct.get();

        assertEquals(1002L, result.getId());
        assertEquals("Pantalon", result.getName());
        assertEquals("L", result.getSize());
        assertEquals("35", result.getPrice());
    }

    @Test
    void testFindAllProducts() {

        Product product1 = new Product(
                "Camiseta",
                "M",
                "20",
                1003L
        );

        Product product2 = new Product(
                "Zapatillas",
                "42",
                "60",
                1004L
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
                1005L
        );

        Product savedProduct = productRepository.save(product);

        savedProduct.setName("Sudadera Nike");
        savedProduct.setSize("L");
        savedProduct.setPrice("45");

        productRepository.save(savedProduct);

        Optional<Product> foundProduct = productRepository.findById(1005L);

        assertTrue(foundProduct.isPresent());

        Product updatedProduct = foundProduct.get();

        assertEquals(1005L, updatedProduct.getId());
        assertEquals("Sudadera Nike", updatedProduct.getName());
        assertEquals("L", updatedProduct.getSize());
        assertEquals("45", updatedProduct.getPrice());
    }

    @Test
    void testDeleteProduct() {

        Product product = new Product(
                "Gorra",
                "S",
                "15",
                1006L
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
                1007L
        );

        Product product2 = new Product(
                "Zapatos",
                "43",
                "70",
                1008L
        );

        productRepository.save(product1);
        productRepository.save(product2);

        long totalProducts = productRepository.count();

        assertEquals(2, totalProducts);
    }
}
