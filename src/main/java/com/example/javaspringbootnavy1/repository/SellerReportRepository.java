package com.example.javaspringbootnavy1.repository;

import com.example.javaspringbootnavy1.modal.SellerReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerReportRepository extends JpaRepository<SellerReport, Long> {
    SellerReport findBySellerId(long sellerId);
}
