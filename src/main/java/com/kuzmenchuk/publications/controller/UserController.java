package com.kuzmenchuk.publications.controller;

import com.kuzmenchuk.publications.exception.UserAlreadyExistException;
import com.kuzmenchuk.publications.repository.model.User;
import com.kuzmenchuk.publications.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/registration")
    public ModelAndView registration(@ModelAttribute("newUser") @Valid User newUser,
                                     BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView();
        } else {
            try {
                userService.registerNewUser(newUser);
            } catch (UserAlreadyExistException e) {
                return new ModelAndView().addObject("message",
                        "An account for that username already exists");
            }


            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(newUser.getUsername(),
                    newUser.getPassword());

            // generate session if one doesn't exist
            request.getSession();

            token.setDetails(new WebAuthenticationDetails(request));
            Authentication authenticatedUser = authenticationManager.authenticate(token);

            SecurityContextHolder.getContext().setAuthentication(authenticatedUser);



            return new ModelAndView("redirect:/");
        }
    }

    @RequestMapping("/login")
    public Principal user(Principal user) {
        return user;
    }

    @GetMapping("profile/{id}")
    public User profile(@PathVariable("id") Integer id) {
        return userService.findById(id);
    }

    @GetMapping("profile/{id}/edit")
    public User editProfile(@PathVariable("id") Integer id) {
        return userService.findById(id);
    }

    @PostMapping("profile/edit/{id}")
    public void editProfilePost(@PathVariable("id") Integer id,
                                        @RequestParam("newPassword") String newPassword) {
      User userToUpd = userService.findById(id);

      userToUpd.setPassword(passwordEncoder.encode(newPassword));
      userService.update(userToUpd);
    }

}
