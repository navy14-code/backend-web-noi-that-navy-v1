package com.example.javaspringbootnavy1.repository;

import com.example.javaspringbootnavy1.modal.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Cart findByUser(Long id);
}
