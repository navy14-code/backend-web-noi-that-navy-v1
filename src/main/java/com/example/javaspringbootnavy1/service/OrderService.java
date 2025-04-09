package com.example.javaspringbootnavy1.service;

import com.example.javaspringbootnavy1.doman.OrderStatus;
import com.example.javaspringbootnavy1.modal.*;

import java.util.List;
import java.util.Set;

public interface OrderService {
    Set<Order> createOrder(User user, Address shippingAddress, Cart cart);
    Order findOrderById(long id) throws Exception;
    List<Order> userOrderHistory(long userId);
    List<Order> sellersOrder(long sellerId);
    Order updateOrderStatus(long orderId, OrderStatus orderStatus) throws Exception;
    Order cancelOrder(long orderId, User user) throws Exception;
    OrderItem getOrderItemById(long Id) throws Exception;

}
