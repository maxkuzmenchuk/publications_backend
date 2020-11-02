package com.kuzmenchuk.publications.controller;

import com.kuzmenchuk.publications.repository.model.User;
import com.kuzmenchuk.publications.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminController {
    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/show-all")
    public List<User> showUsers() {
        return userService.showAll();
    }

    @GetMapping("/show-user")
    public User showUser(@RequestParam("username") String username) {
        return userService.findUserByUsername(username);
    }

    @PostMapping("/update/{id}")
    public ModelAndView updateUserByAdmin(@PathVariable("id") Integer id,
                                          @ModelAttribute("updUser") User updUser) {
        User userToUpd = userService.findById(id);

        userToUpd.setUsername(updUser.getUsername());
        if (!updUser.getPassword().equalsIgnoreCase(userToUpd.getPassword())) {
            userToUpd.setPassword(passwordEncoder.encode(updUser.getPassword()));
        }
        userToUpd.setRole((updUser.getRole()));

        userService.update(userToUpd);

        return new ModelAndView("redirect:/admin/show-all");
    }

    @PostMapping("/delete/{id}")
    public ModelAndView deleteUserByAdmin(@PathVariable("id") Integer id) {
        User userToDelete = userService.findById(id);

        userService.delete(userToDelete);

        return new ModelAndView("redirect:/admin/show-all");
    }
}
