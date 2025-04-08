package com.example.javaspringbootnavy1.controller;

import com.example.javaspringbootnavy1.exceptions.ProductException;
import com.example.javaspringbootnavy1.exceptions.SellerException;
import com.example.javaspringbootnavy1.modal.Product;
import com.example.javaspringbootnavy1.modal.Seller;
import com.example.javaspringbootnavy1.request.CreateProductRequest;
import com.example.javaspringbootnavy1.service.ProdcutService;
import com.example.javaspringbootnavy1.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sellers/products")
public class SellerProductController {

    private final ProdcutService prodcutService;
    private final SellerService sellerService;

    @GetMapping
    public ResponseEntity<List<Product>> getProductBySellerId(
            @RequestHeader("Authorization") String jwt)
            throws Exception {

        Seller seller = sellerService.getSellerProfile(jwt);

        List<Product> products = prodcutService.getProductBySellerId(seller.getId());
        return new ResponseEntity<>(products,HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<Product> creteProduct(
            @RequestBody CreateProductRequest request,
            @RequestHeader("Authorization") String jwt)
        throws Exception,ProductException,SellerException {

        Seller seller = sellerService.getSellerProfile(jwt);

        Product product = prodcutService.createProduct(request, seller);
        return new ResponseEntity<>(product,HttpStatus.CREATED);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        try {
            prodcutService.deleteProduct(productId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ProductException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long productId,@RequestBody Product product)
            throws ProductException {
            Product updateProduct = prodcutService.updateProduct(productId, product);
            return new ResponseEntity<>(updateProduct,HttpStatus.OK);
    }

}
