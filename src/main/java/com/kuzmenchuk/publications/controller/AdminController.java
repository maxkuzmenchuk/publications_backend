package com.kuzmenchuk.publications.controller;

import com.kuzmenchuk.publications.repository.model.User;
import com.kuzmenchuk.publications.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AdminController {
    @Autowired
    UserService userService;

    @GetMapping("/showAll")
    public List<User> show() {
        return userService.showAll();
    }

    @GetMapping("/show-user")
    public User showUser(@RequestParam("username") String username) {
        return userService.showUser(username);
    }
}
