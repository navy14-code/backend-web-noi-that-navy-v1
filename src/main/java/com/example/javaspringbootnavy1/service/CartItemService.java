package com.example.javaspringbootnavy1.service;

import com.example.javaspringbootnavy1.modal.CartItem;

public interface CartItemService {

    CartItem updateCartItem(Long userId,Long id, CartItem cartItem) throws Exception;
    void removeCartItem(Long userId,Long cartItemId) throws Exception;
    CartItem findCartItemById(Long id) throws Exception;
//    CartItem addCartItem(Long userId,Long cartItemId) throws Exception;
}
