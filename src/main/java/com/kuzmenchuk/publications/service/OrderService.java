package com.kuzmenchuk.publications.service;

import com.kuzmenchuk.publications.repository.OrderRepository;
import com.kuzmenchuk.publications.repository.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public void saveOrder(Order orderData) {
        Order order = new Order();


        order.setPrice(orderData.getPrice());
        order.setDate(new Timestamp(new Date().getTime()));
        order.setStatus("WAITING");
//        order.se(new HashSet<>(orderData.getPublicationsId()));

        orderRepository.saveAndFlush(order);
    }

    public Order showOrder(Integer id) {
        return orderRepository.findOrderById(id);
    }

    public List<Order> showAllOrders() {
        return orderRepository.findAll();
    }
}
