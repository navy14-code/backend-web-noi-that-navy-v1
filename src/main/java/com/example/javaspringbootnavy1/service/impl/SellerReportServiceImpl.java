package com.example.javaspringbootnavy1.service.impl;


import com.example.javaspringbootnavy1.modal.Seller;
import com.example.javaspringbootnavy1.modal.SellerReport;
import com.example.javaspringbootnavy1.repository.SellerReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SellerReportServiceImpl implements SellerReportService {
    private final SellerReportRepository sellerReportRepository;

    @Override
    public SellerReport getSellerReport(Seller seller) throws Exception {

        SellerReport sr = sellerReportRepository.findBySellerId(seller.getId());

        if (sr == null) {
            SellerReport newReport = new SellerReport();
            newReport.setId(seller.getId());
            return sellerReportRepository.save(newReport);
        }
        return sr;
    }

    @Override
    public SellerReport updateSellerReport(SellerReport sellerReport) throws Exception {
        return sellerReportRepository.save(sellerReport);
    }
}
