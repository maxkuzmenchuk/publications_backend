package com.kuzmenchuk.publications.repository;

import com.kuzmenchuk.publications.repository.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    Order findOrderById(Integer id);

    Order findOrderByUserId(Integer id);
}
