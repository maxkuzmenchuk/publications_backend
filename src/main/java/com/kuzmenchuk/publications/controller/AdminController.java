package com.kuzmenchuk.publications.controller;

import com.kuzmenchuk.publications.repository.model.User;
import com.kuzmenchuk.publications.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class AdminController {
    @Autowired
    UserService userService;

    @GetMapping("/show-all")
    public ModelAndView show(Model model) {
        model.addAttribute("users", userService.showAll());

        return new ModelAndView("admin");
    }

    @GetMapping("/show-user")
    public User showUser(@RequestParam("username") String username) {
        return userService.findUserByUsername(username);
    }
}
