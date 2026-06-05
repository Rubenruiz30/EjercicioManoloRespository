
package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.User;
import com.example.demo.service.UserService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

/**
 * Controller used to manage user requests.
 * 
 * @author David
 * @author Rubén
 * 27 May 2026
 */
@RestController
@RequestMapping("/users")
@CrossOrigin // when the server recognize the client or user
public class UserController {
	// dependency injection
	@Autowired
	private UserService userService;

    @GetMapping
    public String test() {
        return "Hello";
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable long id) {
        return userService.getUser(id);
        
    }
    /**
     * @PostMappi Is used to map a handler to a unique post request
     * @param user
     * @return
     */
    @PostMapping
    public User createUser(@RequestBody User user) {
    	return userService.saveUser(user);
    }

    @GetMapping("/all")
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        users.add(new User(1));
        users.add(new User(2));

        return users;
    }
    /**
     * Modifies an existing user.
     * 
     * @param id User id
     * @param user Updated user data
     * @return modified user
     */
    @PutMapping("/{id}")
    public User updateUser(@PathVariable long id, @RequestBody User user) {
        user.setId(id);
        return userService.saveUser(user);
    }

    /**
     * Deletes a user by id.
     * 
     * @param id User id
     */
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
    }


}