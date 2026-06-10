package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import com.example.demo.repository.UserRepository;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testUserRepositoryIsNotNull() {

        assertNotNull(userRepository);
    }

    @Test
    void testCountUsers() {

        long totalUsers = userRepository.count();

        assertNotNull(totalUsers);
    }
}