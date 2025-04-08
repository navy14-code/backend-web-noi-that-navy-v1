package com.example.javaspringbootnavy1.repository;

import com.example.javaspringbootnavy1.doman.AccountStatus;
import com.example.javaspringbootnavy1.modal.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SellerRepository extends JpaRepository<Seller, Long> {
    Seller findByEmail(String email);
    List<Seller> findByAccountStatus(AccountStatus status);
}
