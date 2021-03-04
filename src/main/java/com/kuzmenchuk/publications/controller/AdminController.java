package com.kuzmenchuk.publications.controller;

import com.kuzmenchuk.publications.repository.model.Publication;
import com.kuzmenchuk.publications.repository.model.User;
import com.kuzmenchuk.publications.service.PublicationService;
import com.kuzmenchuk.publications.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:8080")
public class AdminController {
    @Autowired
    private UserService userService;

    @Autowired
    private PublicationService publicationService;

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
    public String saveUser(@RequestBody User user) {
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

        return "Saved!";
    }

    @PostMapping("/users/delete/{id}")
    public void deleteUserByAdmin(@PathVariable("id") Long id) {
        User userToDelete = userService.findById(id).orElse(null);
        userService.delete(userToDelete);
    }

    @PostMapping("/publication/save")
    public String savePublication(@ModelAttribute Publication publication, @RequestParam("image") MultipartFile cover) {
        publicationService.savePublication(publication, cover);

        return "Saved!";
    }

    @PostMapping("/publication/delete/{id}")
    public String deletePublication(@PathVariable("id") Long id) {
        publicationService.delete(id);

        return "Deleted!";
    }
}
