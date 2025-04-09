package com.example.javaspringbootnavy1.repository;

import com.example.javaspringbootnavy1.modal.Order;
import com.example.javaspringbootnavy1.modal.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
