package com.example.demo.services;

import com.example.demo.api.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Arrays;

@Service
public class UserService {

    private final List<User> userList;

    public List<User> getAllUsers() {
        return new ArrayList<>(userList);  // Returning a copy of the list to avoid external modification
    }


    public UserService() {
        userList = new ArrayList<>();

        // Create some initial users
        User user1 = new User(1, "Ida", 32, "ida@mail.com");
        User user2 = new User(2, "Hans", 26, "hans@mail.com");
        User user3 = new User(3, "Lars", 45, "lars@mail.com");
        User user4 = new User(4, "Ben", 32, "ben@mail.com");
        User user5 = new User(5, "Eva", 59, "eva@mail.com");

        // Add them to the list
        userList.addAll(Arrays.asList(user1, user2, user3, user4, user5));
    }

    // Get user by id
    public Optional<User> getUser(Integer id) {
        return userList.stream()
                .filter(user -> user.getId() == id)
                .findFirst();
    }

    // Create a new user (simulating saving to a database)
    public User createUser(User user) {
        // Generate a new ID based on the size of the userList
        int newId = userList.size() + 1;
        User newUser = new User(newId, user.getName(), user.getAge(), user.getEmail());
        userList.add(newUser);
        return newUser;
    }

    // Update an existing user (by id)
    public User updateUser(User user) {
        for (int i = 0; i < userList.size(); i++) {
            User existingUser = userList.get(i);
            if (existingUser.getId() == user.getId()) {
                // Update the fields (except for id)
                existingUser.setName(user.getName());
                existingUser.setAge(user.getAge());
                existingUser.setEmail(user.getEmail());
                return existingUser;
            }
        }
        return null;  // User with the given id doesn't exist
    }
}
