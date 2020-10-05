package com.kuzmenchuk.publications.controller;

import com.kuzmenchuk.publications.repository.model.User;
import com.kuzmenchuk.publications.service.UserService;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.WebAttributes;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

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

        return "/index";
    }

    @GetMapping("/login?error")
    public String login(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        String errorMessage = null;
        if (session != null) {
            AuthenticationException ex = (AuthenticationException) session
                    .getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
            if (ex != null) {
                errorMessage = ex.getMessage();
            }
        }
        model.addAttribute("errorMessage", errorMessage);
        return "login";
    }

    @GetMapping("user/profile/{username}")
    public ModelAndView profile(@PathVariable("username") String username, Model model, ModelAndView modelAndView) {
        User user = userService.findUserByUsername(username);

        model.addAttribute("user", user);
        modelAndView.setViewName("user/profile");

        return modelAndView;
    }

    @GetMapping("user/profile/{username}/edit")
    public ModelAndView editProfile(@PathVariable("username") String username, Model model, ModelAndView modelAndView) {
        User user = userService.findUserByUsername(username);

        model.addAttribute("user", user);
        modelAndView.setViewName("user/edit");

        return modelAndView;
    }

    @PostMapping("user/profile/{username}")
    public ModelAndView editProfilePost(@PathVariable("username") String username,
                                  @RequestParam("newPassword") String newPassword, Model model) {
      User userToUpd = userService.findUserByUsername(username);

      userToUpd.setPassword(passwordEncoder.encode(newPassword));
      userService.update(userToUpd);

      model.addAttribute("user", userToUpd);
      return new ModelAndView("user/profile");
    }

}