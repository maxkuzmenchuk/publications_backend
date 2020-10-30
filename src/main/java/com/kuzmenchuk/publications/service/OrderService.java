package com.kuzmenchuk.publications.service;

import com.kuzmenchuk.publications.repository.OrderRepository;
import com.kuzmenchuk.publications.repository.model.Order;
import com.kuzmenchuk.publications.repository.model.Publication;
import com.kuzmenchuk.publications.repository.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public boolean saveOrder(User user, Double price, List<Integer> publicationsId) {
        Order order = new Order();

        order.setUserId(user.getId());
        order.setUsername(user.getUsername());
        order.setPrice(price);
        order.setDate(new Timestamp(new Date().getTime()));
        order.setStatus("WAITING");
        order.setPublicationsId(new HashSet<>(publicationsId));

        orderRepository.saveAndFlush(order);

        return true;
    }
}
