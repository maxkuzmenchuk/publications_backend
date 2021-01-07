package com.kuzmenchuk.publications.controller;

import com.kuzmenchuk.publications.repository.model.Order;
import com.kuzmenchuk.publications.repository.model.User;
import com.kuzmenchuk.publications.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@CrossOrigin(origins = "http://localhost:8080")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/show")
    public Order showOrder(@RequestParam("orderId") Integer id){
        return orderService.showOrder(id);
    }

    @GetMapping("/show-all")
    public List<Order> showAllOrders() {
        return orderService.showAllOrders();
    }

    @PostMapping("/save")
    public String saveOrder(@RequestBody Order order) {
        orderService.saveOrder(order);

        return "successful";
    }
}
