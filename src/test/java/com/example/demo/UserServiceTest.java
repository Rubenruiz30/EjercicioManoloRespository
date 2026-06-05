package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.domain.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Test
    void testGetUser() {

        User user = new User(
                1L,
                "David",
                "Garcia",
                1500.50,
                "1234",
                "Madrid",
                true,
                "david123",
                true
        );

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User result = userServiceImpl.getUser(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("David", result.getName());
        assertEquals("Garcia", result.getSurname());
        assertEquals(1500.50, result.getBalance());
        assertEquals("1234", result.getPassword());
        assertEquals("Madrid", result.getResidence());
        assertTrue(result.isLogin());
        assertEquals("david123", result.getNicknameString());
        assertTrue(result.isSingup());
    }

    @Test
    void testGetUserNotFound() {

        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userServiceImpl.getUser(99L);
        });

        assertEquals("User not found with id: 99", exception.getMessage());
    }
}
