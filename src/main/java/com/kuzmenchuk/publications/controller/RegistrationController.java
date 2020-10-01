package com.kuzmenchuk.publications.controller;

import com.kuzmenchuk.publications.repository.model.User;
import com.kuzmenchuk.publications.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class RegistrationController {
    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public ModelAndView regModel() {
        ModelAndView model = new ModelAndView();

        model.setViewName("registration");

        return model;
    }

    @PostMapping("/registration")
    public String registration(@RequestParam("username") String username, @RequestParam("password") String password) {
        User user = new User();

        user.setUsername(username);
        user.setPassword(password);

        userService.save(user);

        return "Success!";
    }
}