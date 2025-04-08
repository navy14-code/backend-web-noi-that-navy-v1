package com.example.javaspringbootnavy1.modal;

import com.example.javaspringbootnavy1.doman.PaymantStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Table(name ="orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Long id;

    private String orderId;

    @ManyToOne
    private User user;

    private long sellerId;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> oderItems = new ArrayList<>();

    @ManyToOne
    private Address shippingAddress;

    @Embedded
    private PaymentDetails paymentDetails = new PaymentDetails();

    private double totalMrpPrice;

    private Integer totalSellingPrice;

    private Integer discount;

    private OrderStatus orderStutus;

    private int totalItem;

    private PaymantStatus paymantStatus = PaymantStatus.PENDING;

    private LocalDateTime orderDate = LocalDateTime.now();

    private LocalDateTime deliverDate = orderDate.plusDays(7);
}
