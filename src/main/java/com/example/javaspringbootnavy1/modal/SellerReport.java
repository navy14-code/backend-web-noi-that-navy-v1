package com.example.javaspringbootnavy1.modal;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SellerReport {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Long id;

    @OneToOne
    private Seller seller;

    private Long totalEarnings= 0L;

    private Long totalSales= 0L;

    private Long totalRefunds= 0L;

    private Long totalTax= 0L;

    private Long netEarnings= 0L;

    private Integer totlalOrders= 0;

    private Integer cansedOrders= 0;

    private Integer totalTransactions= 0;

}
