package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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

        Pageable pageable = PageRequest.of(0, 20);
        Page<Product> productPage = new PageImpl<>(List.of(product1, product2), pageable, 2);

        when(productRepository.findAll(any(Pageable.class))).thenReturn(productPage);

        Page<Product> result = productServiceImpl.getAllProducts(0, 20);

        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        assertEquals(2, result.getTotalElements());
        assertEquals("Camiseta", result.getContent().get(0).getName());
        assertEquals("Pantalon", result.getContent().get(1).getName());
    }

    @Test
    void testSearchProductsById() {

        Product product = new Product(
                "Camiseta",
                "M",
                "20",
                1
        );

        product.setId(1L);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Page<Product> result = productServiceImpl.searchProducts("id", "1", 0, 20);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals("Camiseta", result.getContent().get(0).getName());
    }

    @Test
    void testSearchProductsByName() {

        Product product = new Product(
                "Camiseta",
                "M",
                "20",
                1
        );

        Pageable pageable = PageRequest.of(0, 20);
        Page<Product> productPage = new PageImpl<>(List.of(product), pageable, 1);

        when(productRepository.findByNameContainingIgnoreCase(any(String.class), any(Pageable.class)))
                .thenReturn(productPage);

        Page<Product> result = productServiceImpl.searchProducts("name", "camiseta", 0, 20);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals("Camiseta", result.getContent().get(0).getName());
    }

    @Test
    void testSearchProductsBySize() {

        Product product = new Product(
                "Pantalon",
                "L",
                "35",
                2
        );

        Pageable pageable = PageRequest.of(0, 20);
        Page<Product> productPage = new PageImpl<>(List.of(product), pageable, 1);

        when(productRepository.findBySizeContainingIgnoreCase(any(String.class), any(Pageable.class)))
                .thenReturn(productPage);

        Page<Product> result = productServiceImpl.searchProducts("size", "L", 0, 20);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals("Pantalon", result.getContent().get(0).getName());
    }

    @Test
    void testSearchProductsByPrice() {

        Product product = new Product(
                "Sudadera",
                "M",
                "30",
                3
        );

        Pageable pageable = PageRequest.of(0, 20);
        Page<Product> productPage = new PageImpl<>(List.of(product), pageable, 1);

        when(productRepository.findByPriceContainingIgnoreCase(any(String.class), any(Pageable.class)))
                .thenReturn(productPage);

        Page<Product> result = productServiceImpl.searchProducts("price", "30", 0, 20);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals("Sudadera", result.getContent().get(0).getName());
    }

    @Test
    void testSearchProductsByStock() {

        Product product = new Product(
                "Gorra",
                "S",
                "15",
                6
        );

        Pageable pageable = PageRequest.of(0, 20);
        Page<Product> productPage = new PageImpl<>(List.of(product), pageable, 1);

        when(productRepository.findByStock(anyInt(), any(Pageable.class)))
                .thenReturn(productPage);

        Page<Product> result = productServiceImpl.searchProducts("stock", "6", 0, 20);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals("Gorra", result.getContent().get(0).getName());
    }

    @Test
    void testSearchProductsByInvalidStock() {

        Page<Product> result = productServiceImpl.searchProducts("stock", "abc", 0, 20);

        assertNotNull(result);
        assertEquals(0, result.getContent().size());
    }

    @Test
    void testSearchProductsByInvalidId() {

        Page<Product> result = productServiceImpl.searchProducts("id", "abc", 0, 20);

        assertNotNull(result);
        assertEquals(0, result.getContent().size());
    }
}