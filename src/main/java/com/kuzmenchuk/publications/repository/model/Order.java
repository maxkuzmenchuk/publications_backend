package com.kuzmenchuk.publications.repository.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "publications", name = "orders")
public class Order {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "username")
    private String username;

    @Column(name = "amount")
    private int amount;

    @Column(name = "date")
    private Timestamp date;

    @Column(name = "status")
    private String status;

    @Column(name = "price")
    private double price;


    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(schema = "publications", name = "order_details", joinColumns = @JoinColumn(name = "order_id"))
    private Set<Integer> publicationsId = new HashSet<>();
}
