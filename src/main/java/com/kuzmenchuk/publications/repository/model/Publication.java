package com.kuzmenchuk.publications.repository.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(schema = "publications", name = "publications")
public class Publication {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private int id;
    @NotEmpty(message = "Name cannot be empty")
    @Column(name = "name")
    private String name;
    @Min(value = 0, message = "Price could be equal or more than 0")
    @Column(name = "price")
    private int price;
    @Column(name = "image_name")
    private String imageName;
    @Column(name = "description")
    private String description;
}



