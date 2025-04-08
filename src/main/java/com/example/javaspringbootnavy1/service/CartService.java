package com.example.javaspringbootnavy1.service;

import com.example.javaspringbootnavy1.modal.Cart;
import com.example.javaspringbootnavy1.modal.CartItem;
import com.example.javaspringbootnavy1.modal.Product;
import com.example.javaspringbootnavy1.modal.User;

public interface CartService {

    public CartItem addCartItem(
            User user,
            Product product,
            String size,
            int quality
    );
    public Cart findUserCart(User user);
}
