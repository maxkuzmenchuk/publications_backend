package com.kuzmenchuk.publications.service;

import com.kuzmenchuk.publications.exception.UserAlreadyExistException;
import com.kuzmenchuk.publications.repository.UserRepository;
import com.kuzmenchuk.publications.repository.model.Authority;
import com.kuzmenchuk.publications.repository.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) throw new UsernameNotFoundException("User " + username + " not found!");

        return user;
    }

    @Transactional
    public void registerNewUser(User user)  {

        if (usernameExists(user.getUsername())) {
            throw new UserAlreadyExistException("User " + user.getUsername() + " is exists!");
        }

        Authority authority = new Authority();
        List<Authority> authorities = new ArrayList<>();
        authority.setAuthority("ROLE_USER");
        authorities.add(authority);

        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setAuthorities(authorities);
        newUser.setEnabled(true);

        userRepository.saveAndFlush(newUser);
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

    public void save(User user) {
        userRepository.saveAndFlush(user);
    }

    public void delete(User user) {
        userRepository.delete(user);
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
}
