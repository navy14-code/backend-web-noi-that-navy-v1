package com.example.javaspringbootnavy1.repository;

import com.example.javaspringbootnavy1.modal.Cart;
import com.example.javaspringbootnavy1.modal.CartItem;
import com.example.javaspringbootnavy1.modal.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    CartItem findByCartAndProductAndSize(Cart cart, Product product, String size);

}
