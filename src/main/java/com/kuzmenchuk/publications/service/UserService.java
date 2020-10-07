package com.kuzmenchuk.publications.service;

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

    public boolean save(User user) {
        User existingUser = userRepository.findByUsername(user.getUsername());

        if (existingUser != null) return false;

        user.setUsername(user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(new HashSet<>(Collections.singleton(Role.ROLE_USER)));

        userRepository.saveAndFlush(user);

        return true;
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
