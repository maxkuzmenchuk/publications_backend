package com.kuzmenchuk.publications.controller;

import com.kuzmenchuk.publications.exception.UserAlreadyExistException;
import com.kuzmenchuk.publications.repository.model.User;
import com.kuzmenchuk.publications.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:8080")
public class AdminController {
    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/show-all")
    public List<User> showUsers() {
        return userService.showAll();
    }

    @GetMapping("/show-user/{id}")
    public User showUser(@PathVariable("id") Integer id) {
        return userService.findById(id);
    }

//    @PostMapping("/update/{id}")
//    public void updateUserByAdmin(@PathVariable("id") Integer id,
//                                          @RequestBody User updUser) {
//        System.err.println(updUser);
//
//        User userToUpd = userService.findById(id);
//
//        userToUpd.setUsername(updUser.getUsername());
//        if (!updUser.getPassword().equalsIgnoreCase(userToUpd.getPassword())) {
//            userToUpd.setPassword(passwordEncoder.encode(updUser.getPassword()));
//        }
//        userToUpd.setRole((updUser.getRole()));
//
//        userService.update(userToUpd);
//    }

    @PostMapping("/delete/{id}")
    public void deleteUserByAdmin(@PathVariable("id") Integer id) {
        User userToDelete = userService.findById(id);

        userService.delete(userToDelete);
    }

//    TODO: fix JSON parse of string

    @PostMapping("/add")
    public void addUserByAdmin(@RequestBody User newUser,
                               BindingResult bindingResult, HttpServletRequest request) {
        System.err.println("user = " + newUser);
        try {
//            userService.addNewUser(newUser);
        } catch (UserAlreadyExistException e) {
            throw new UserAlreadyExistException("An account for that username already exists");
        }
    }
}
