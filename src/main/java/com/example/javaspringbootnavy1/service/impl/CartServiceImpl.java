package com.example.javaspringbootnavy1.service.impl;

import com.example.javaspringbootnavy1.modal.Cart;
import com.example.javaspringbootnavy1.modal.CartItem;
import com.example.javaspringbootnavy1.modal.Product;
import com.example.javaspringbootnavy1.modal.User;
import com.example.javaspringbootnavy1.repository.CartItemRepository;
import com.example.javaspringbootnavy1.repository.CartRepository;
import com.example.javaspringbootnavy1.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;

    @Override
    public CartItem addCartItem(User user, Product product, String size, int quality) {
        Cart cart= findUserCart(user);

        CartItem isPresent = cartItemRepository.findByCartAndProductAndSize(cart, product, size);

        if (isPresent == null) {
            CartItem cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setQuantity(quality);
            cartItem.setUserId(user.getId());
            cartItem.setSize(size);

            int totalPrice= quality*product.getSellingPrice();
            cartItem.setSellingPrice(totalPrice);
            cartItem.setMrpPrice(quality*product.getMrpPrice());

            cart.getCartItems().add(cartItem);
            cartItem.setCart(cart);

            return cartItemRepository.save(cartItem);

        }
        return isPresent;
    }

    @Override
    public Cart findUserCart(User user) {
        Cart cart = cartRepository.findByUserId(user.getId());

        int totalPrice = 0;
        int totalDiscountedPrice = 0;
        int totalItem = 0;
        for (CartItem cartItem: cart.getCartItems()){
            totalPrice+=cartItem.getMrpPrice();
            totalDiscountedPrice+=cartItem.getSellingPrice();
            totalItem+=cartItem.getQuantity();
        }

        cart.setTotalMrpPrice(totalPrice);
        cart.setTotalItem(totalItem);
        cart.setTotalSellingPrice(totalDiscountedPrice);
        cart.setDiscount((caculateDiscountPercentage(totalPrice, totalDiscountedPrice)));
        cart.setTotalItem(totalItem);
        return cart;
    }
    private int caculateDiscountPercentage(int mrpPrice, int sellingPrice) {
        if(mrpPrice <= 0) {
            return 0;
//            throw new IllegalArgumentException("Giá thực tế phải lớn hơn 0");
        }
        double discount = mrpPrice - sellingPrice;
        double discountPercentage =(discount/mrpPrice)*100;
        return (int)discountPercentage;

    }
}
