package com.kuzmenchuk.publications.controller;

import com.kuzmenchuk.publications.repository.model.User;
import com.kuzmenchuk.publications.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RegistrationController {
    @Autowired
    private UserService userService;

    @PostMapping("/registration")
    public String registration(@RequestParam("username") String username, @RequestParam("password") String password) {
        User user = new User();

        user.setUsername(username);
        user.setPassword(password);

        userService.save(user);

        return "Success!";
    }

    @GetMapping("/showAll")
    public List<User> show() {
        return userService.showAll();
    }
}