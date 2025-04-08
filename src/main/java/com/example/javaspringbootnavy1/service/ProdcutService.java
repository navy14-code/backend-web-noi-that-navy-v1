package com.example.javaspringbootnavy1.service;

import com.example.javaspringbootnavy1.exceptions.ProductException;
import com.example.javaspringbootnavy1.modal.Product;
import com.example.javaspringbootnavy1.modal.Seller;
import com.example.javaspringbootnavy1.request.CreateProductRequest;
import org.springframework.data.domain.Page;


import java.util.List;

public interface ProdcutService {

    public Product createProduct(CreateProductRequest req, Seller seller);
    public void deleteProduct(Long productId) throws ProductException;
    public Product updateProduct(Long productId, Product product) throws ProductException;
    Product findProductById(Long productId) throws ProductException;

    List<Product> searchProduct(String query );
    public Page<Product> getAllProduct(
            String category,
            String brand,
            String sizes,
            Integer minPrice,
            Integer maxPrice,
            Integer minDiscount,
            String sort,
            String stock,
            Integer pageNumber
    );
    List<Product> getProductBySellerId(Long sellerId);

}
