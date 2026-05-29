package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.User;

/**
 * 
 * 
 *@author david
 *@author Rubénñ
 *27 may 2026
 */
@RestController //  his uses is to manage the user petition
@RequestMapping// his uses is to define the resource route
public class Usercontroller {
@GetMapping // is to recover a  server source
	public String test() {
		
		
		
		return "hello";
	}
@GetMapping("/{id}")
public User GetUser(/** variable in the route*/@PathVariable long id) {
	return new User(id);
}

@GetMapping("/all")
public List<User> GetAllUsers() {
	List<User> users = new ArrayList<User>();

	users.add(new User(1));
	users.add(new User(2));
	return users;	
}
}
