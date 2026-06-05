package com.example.demo.service;

import com.example.demo.domain.User;

/**
 * Service layer: it is in charge of business logic, the controller
 * layer uses this to manage user data
 *@author david
 *@author Rubén
 *2 jun 2026
 */

public interface UserService {

	User getUser(long id);

	User saveUser(User user);

	void deleteUser(long id);

	
}
