package com.example.javaspringbootnavy1.service.impl;


import com.example.javaspringbootnavy1.modal.Seller;
import com.example.javaspringbootnavy1.modal.SellerReport;

public interface SellerReportService {
    SellerReport getSellerReport(Seller seller) throws Exception;
    SellerReport updateSellerReport(SellerReport sellerReport) throws Exception;

}
