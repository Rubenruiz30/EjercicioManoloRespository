package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.User;

/**
 * Controller used to manage user requests.
 * 
 * @author David
 * @author Rubén
 * 27 May 2026
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping
    public String test() {
        return "Hello";
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable long id) {
        return new User(id);
    }

    @GetMapping("/all")
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        users.add(new User(1));
        users.add(new User(2));

        return users;
    }
}