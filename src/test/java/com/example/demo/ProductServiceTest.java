package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.example.demo.domain.Product;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.ProductServiceImpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock // Crea un ProductRepository falso para no usar la base de datos real.
    private ProductRepository productRepository;

    @InjectMocks // Crea ProductServiceImpl real e introduce dentro el ProductRepository falso.
    private ProductServiceImpl productServiceImpl;

    @Test
    void testGetProduct() {

        Product product = new Product(
                "Camiseta",
                "M",
                "20",
                1
        );

        product.setId(1L);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Product result = productServiceImpl.getProduct(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Camiseta", result.getName());
        assertEquals("M", result.getSize());
        assertEquals("20", result.getPrice());
        assertEquals(1, result.getStock());
    }

    @Test
    void testSaveProduct() {

        Product product = new Product(
                "Pantalon",
                "L",
                "35",
                2
        );

        Product savedProduct = new Product(
                "Pantalon",
                "L",
                "35",
                2
        );

        savedProduct.setId(2L);

        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);

        Product result = productServiceImpl.saveProduct(product);

        assertNotNull(result);
        assertEquals(2L, result.getId());
        assertEquals("Pantalon", result.getName());
        assertEquals("L", result.getSize());
        assertEquals("35", result.getPrice());
        assertEquals(2, result.getStock());
    }

    @Test
    void testDeleteProduct() {

        productServiceImpl.deleteProduct(1L);

        verify(productRepository).deleteById(1L);
    }

    @Test
    void testGetProductNotFound() {

        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            productServiceImpl.getProduct(99L);
        });

        assertEquals("Product not found with id: 99", exception.getMessage());
    }

    @Test
    void testGetAllProducts() {

        Product product1 = new Product(
                "Camiseta",
                "M",
                "20",
                1
        );

        Product product2 = new Product(
                "Pantalon",
                "L",
                "35",
                2
        );

        List<Product> products = Arrays.asList(product1, product2);

        when(productRepository.findAll()).thenReturn(products);

        List<Product> result = productServiceImpl.getAllProducts();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Camiseta", result.get(0).getName());
        assertEquals("Pantalon", result.get(1).getName());
    }
}