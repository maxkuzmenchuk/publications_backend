package com.kuzmenchuk.publications.service;

import com.kuzmenchuk.publications.exception.UserAlreadyExistException;
import com.kuzmenchuk.publications.repository.UserRepository;
import com.kuzmenchuk.publications.repository.model.Role;
import com.kuzmenchuk.publications.repository.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username);

        if (user == null) throw new UsernameNotFoundException("User " + username + " not found!");

        List<GrantedAuthority> grantedAuthorities = buildUserAuthority(user.getRole());


        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
    }

    private List<GrantedAuthority> buildUserAuthority(Set<Role> userRoles) {

        Set<GrantedAuthority> setAuths = new HashSet<>();

        // Build user's authorities
        for (Role userRole : userRoles) {
            setAuths.add(new SimpleGrantedAuthority(String.valueOf(userRole)));
        }

        return new ArrayList<>(setAuths);
    }

    @Transactional
    public User registerNewUser(User user)  {

        if (usernameExists(user.getUsername())) {
            throw new UserAlreadyExistException("User " + user.getUsername() + " is exists!");
        }

        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setRole(new HashSet<>(Collections.singleton(Role.ROLE_USER)));

        return userRepository.saveAndFlush(newUser);
    }

    public User addNewUser(User user) {
        if (usernameExists(user.getUsername())) {
            throw new UserAlreadyExistException("User " + user.getUsername() + " is exists!");
        }

        Set<Role> role = new HashSet<>();

        switch (user.getRole().toString()) {
            case "ROLE_ADMIN":
                role.add(Role.ROLE_ADMIN);
                break;
            case "ROLE_USER":
                role.add(Role.ROLE_USER);
                break;
        }

        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setRole(role);

        return userRepository.saveAndFlush(newUser);
    }

    private boolean usernameExists(String username) {
        return userRepository.findByUsername(username) != null;
    }

    public List<User> showAll() {
        return userRepository.findAll();
    }

    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void update(User user) {
        userRepository.save(user);
    }

    public void delete(User user) {
        userRepository.delete(user);
    }

    public User findById(Integer id) {
        return userRepository.getOne(id);
    }
}
