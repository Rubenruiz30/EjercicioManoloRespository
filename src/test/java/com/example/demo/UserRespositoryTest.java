package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import com.example.demo.domain.User;
import com.example.demo.repository.UserRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testSaveUser() {

        User user = new User(
                null,
                "David",
                "Garcia",
                1500.50,
                "1234",
                "Madrid",
                true,
                "david123",
                true
        );

        User savedUser = userRepository.save(user);

        assertNotNull(savedUser);
        assertNotNull(savedUser.getId());

        assertEquals("David", savedUser.getName());
        assertEquals("Garcia", savedUser.getSurname());
        assertEquals(1500.50, savedUser.getBalance());
        assertEquals("1234", savedUser.getPassword());
        assertEquals("Madrid", savedUser.getResidence());
        assertTrue(savedUser.isLogin());
        assertEquals("david123", savedUser.getNicknameString());
        assertTrue(savedUser.isSingup());
    }

    @Test
    void testFindUserById() {

        User user = new User(
                null,
                "Ruben",
                "Lopez",
                800.00,
                "abcd",
                "Barcelona",
                false,
                "ruben99",
                true
        );

        User savedUser = userRepository.save(user);

        Optional<User> foundUser = userRepository.findById(savedUser.getId());

        assertTrue(foundUser.isPresent());

        User result = foundUser.get();

        assertEquals("Ruben", result.getName());
        assertEquals("Lopez", result.getSurname());
        assertEquals(800.00, result.getBalance());
        assertEquals("abcd", result.getPassword());
        assertEquals("Barcelona", result.getResidence());
        assertFalse(result.isLogin());
        assertEquals("ruben99", result.getNicknameString());
        assertTrue(result.isSingup());
    }

    @Test
    void testFindAllUsers() {

        userRepository.deleteAll();

        User user1 = new User(
                null,
                "David",
                "Garcia",
                1000.00,
                "pass1",
                "Madrid",
                true,
                "david123",
                true
        );

        User user2 = new User(
                null,
                "Ana",
                "Perez",
                2000.00,
                "pass2",
                "Valencia",
                false,
                "ana22",
                false
        );

        userRepository.save(user1);
        userRepository.save(user2);

        List<User> users = userRepository.findAll();

        assertNotNull(users);
        assertEquals(2, users.size());
    }

    @Test
    void testUpdateUser() {

        User user = new User(
                null,
                "Carlos",
                "Sanchez",
                500.00,
                "1111",
                "Sevilla",
                false,
                "carlos01",
                true
        );

        User savedUser = userRepository.save(user);

        savedUser.setBalance(900.00);
        savedUser.setResidence("Malaga");
        savedUser.setLogin(true);

        User updatedUser = userRepository.save(savedUser);

        assertEquals(savedUser.getId(), updatedUser.getId());
        assertEquals(900.00, updatedUser.getBalance());
        assertEquals("Malaga", updatedUser.getResidence());
        assertTrue(updatedUser.isLogin());
    }

    @Test
    void testDeleteUser() {

        User user = new User(
                null,
                "Laura",
                "Martinez",
                300.00,
                "2222",
                "Bilbao",
                true,
                "laura88",
                true
        );

        User savedUser = userRepository.save(user);

        Long id = savedUser.getId();

        userRepository.deleteById(id);

        Optional<User> deletedUser = userRepository.findById(id);

        assertFalse(deletedUser.isPresent());
    }

    @Test
    void testCountUsers() {

        userRepository.deleteAll();

        User user1 = new User(
                null,
                "Mario",
                "Ruiz",
                1200.00,
                "pass123",
                "Madrid",
                true,
                "mario10",
                true
        );

        User user2 = new User(
                null,
                "Lucia",
                "Fernandez",
                1800.00,
                "pass456",
                "Granada",
                false,
                "lucia20",
                false
        );

        userRepository.save(user1);
        userRepository.save(user2);

        long totalUsers = userRepository.count();

        assertEquals(2, totalUsers);
    }
}