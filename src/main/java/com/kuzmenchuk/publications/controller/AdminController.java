package com.kuzmenchuk.publications.controller;

import com.kuzmenchuk.publications.repository.model.User;
import com.kuzmenchuk.publications.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:8080")
public class AdminController {
    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/users/all")
    public List<User> showUsers() {
        return userService.showAll();
    }

    @GetMapping("/users/edit/{id}")
    public User showUser(@PathVariable("id") Long id) {
        return userService.findById(id).orElse(null);
    }

    @PostMapping("/users/save")
    public void saveUser(@RequestBody User user) {
        if (user.getId() != null ) {
            User userFromDB = userService.findById(user.getId()).orElse(new User());

            String dbPassword = userFromDB.getPassword();
            String userPassword = user.getPassword();

            if (!dbPassword.equalsIgnoreCase(userPassword)) {
                user.setPassword(passwordEncoder.encode(userPassword));
            }
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        userService.save(user);
    }

    @PostMapping("/users/delete/{id}")
    public void deleteUserByAdmin(@PathVariable("id") Long id) {
        User userToDelete = userService.findById(id).orElse(null);
        userService.delete(userToDelete);
    }
}
