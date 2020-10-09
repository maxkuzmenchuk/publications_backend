package com.kuzmenchuk.publications.repository.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "publications", name = "user_account")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    @NotEmpty(message = "Username cannot be empty")
    @Pattern(regexp = "[A-Za-z0-9]\\w+", message = "Incorrect username")
    private String username;
    @NotEmpty(message = "Password cannot be empty")
    @Pattern(regexp = "^.*(?=.*[a-z])(?=.*\\d).*$", message = "Invalid password (example: Pass1234, pass1234)")
    private String password;
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(schema = "publications", name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> role;
}
