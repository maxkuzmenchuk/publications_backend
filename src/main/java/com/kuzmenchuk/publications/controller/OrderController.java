package com.kuzmenchuk.publications.controller;

import com.kuzmenchuk.publications.repository.model.User;
import com.kuzmenchuk.publications.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/order?{price}&{list}&{user}")
    public String order(@PathVariable("user") User user,
                        @PathVariable("price") Double price,
                        @PathVariable("list") List<Integer> list) {

        orderService.saveOrder(user, price, list);

        return "successful";
    }
}
