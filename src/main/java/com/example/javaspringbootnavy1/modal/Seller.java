package com.example.javaspringbootnavy1.modal;

import com.example.javaspringbootnavy1.doman.AccountStatus;
import com.example.javaspringbootnavy1.doman.User_Role;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Long id;

    private String sellerName;

    private String phone;

    @Column(unique = true, nullable = true)
    private String email;
    private String password;

    @Embedded
    private BusinessDetails businessDetails = new BusinessDetails();

    @Embedded
    private BankDetails bankDetails = new BankDetails();

    @OneToOne(cascade = CascadeType.ALL)
    private Address pickupAddress = new Address();

    private String GSTIN;

    private User_Role role = User_Role.Role_Seller;

    private boolean isEmailVerified = false;

    private AccountStatus accountStatus = AccountStatus.PENDING_VERIFICATION;

}
