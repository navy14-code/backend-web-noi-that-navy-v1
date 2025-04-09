package com.example.javaspringbootnavy1.repository;

import com.example.javaspringbootnavy1.modal.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(long userId);
    List<Order> findBySellerId(long userId);


}
