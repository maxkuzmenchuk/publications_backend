package com.kuzmenchuk.publications.controller;

import com.kuzmenchuk.publications.exception.UserAlreadyExistException;
import com.kuzmenchuk.publications.repository.model.User;
import com.kuzmenchuk.publications.service.UserService;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/")
    public String index() {
        return "/";
    }

    @GetMapping("/registration")
    public ModelAndView displayRegistration(Model model) {
        model.addAttribute("newUser", new User());
        return new ModelAndView("registration");
    }

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

    @GetMapping("user/profile/{id}/edit")
    public ModelAndView editProfile(@PathVariable("id") Integer id, Model model) {
        User user = userService.findById(id);

        model.addAttribute("user", user);

        return new ModelAndView("user/edit");
    }

    @PostMapping("user/edit/{id}")
    public ModelAndView editProfilePost(@PathVariable("id") Integer id,
                                        @RequestParam("newPassword") String newPassword, Model model) {
      User userToUpd = userService.findById(id);

      userToUpd.setPassword(passwordEncoder.encode(newPassword));
      userService.update(userToUpd);

      model.addAttribute("user", userToUpd);
      return new ModelAndView("redirect:/user/profile/" + userToUpd.getUsername());
    }

}