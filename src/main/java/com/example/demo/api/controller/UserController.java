package com.example.demo.api.controller;

import com.example.demo.api.model.User;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // GET: Fetch a user by id
    @GetMapping("/user")
    public User getUser(@RequestParam Integer id) {
        Optional<User> user = userService.getUser(id);
        return user.orElse(null);  // Return the user if found, otherwise null (you can change this to a 404 response)
    }

    // POST: Create a new user
    @PostMapping("/user")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);  // Assuming createUser is a method in your service
        return ResponseEntity.status(201).body(createdUser);  // Return status 201 for created
    }

    // PUT: Update an existing user
    @PutMapping("/user/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Integer id, @RequestBody User user) {
        Optional<User> existingUser = userService.getUser(id);
        if (existingUser.isPresent()) {
            // The id in the path should match the id of the user you're trying to update, so we don't need to set it.
            user = new User(id, user.getName(), user.getAge(), user.getEmail());  // Ensure the id stays unchanged

            User updatedUser = userService.updateUser(user);
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.status(404).body(null);  // Return 404 if the user is not found
        }
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();  // Fetch all users
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();  // Return 204 No Content if no users exist
        }
        return ResponseEntity.ok(users);  // Return 200 OK with the list of users
    }

}
