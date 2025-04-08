package com.example.javaspringbootnavy1.service.impl;

import com.example.javaspringbootnavy1.config.JwtProvider;
import com.example.javaspringbootnavy1.doman.AccountStatus;
import com.example.javaspringbootnavy1.exceptions.SellerException;
import com.example.javaspringbootnavy1.modal.Address;
import com.example.javaspringbootnavy1.modal.Seller;
import com.example.javaspringbootnavy1.repository.AddressRepository;
import com.example.javaspringbootnavy1.repository.SellerRepository;
import com.example.javaspringbootnavy1.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SellerServiceImpl implements SellerService {

    private final SellerRepository sellerRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final AddressRepository addressRepository;


    @Override
    public Seller getSellerProfile(String jwt) throws Exception {
        String email = jwtProvider.getEmailFromJwtToken(jwt);

        return this.getSellerByEmail(email);
    }

    @Override
    public Seller createSeller(Seller seller) throws Exception {
        Seller sellerExits= sellerRepository.findByEmail(seller.getEmail());
        if(sellerExits!=null){
            throw new Exception("Seller đã tồn tại, sử dụng email khác");
        }
        Address savedAdress = addressRepository.save(seller.getPickupAddress());

        Seller newSeller = new Seller();
        newSeller.setEmail(seller.getEmail());
        newSeller.setPassword(passwordEncoder.encode(seller.getPassword()));
        newSeller.setPickupAddress(savedAdress);
        newSeller.setGSTIN(seller.getGSTIN());
        newSeller.setRole(seller.getRole());
        newSeller.setPhone(seller.getPhone());
        newSeller.setBankDetails(seller.getBankDetails());
        newSeller.setBusinessDetails(seller.getBusinessDetails());

        return sellerRepository.save(newSeller);
    }

    @Override
    public Seller getSellerById(Long id) throws SellerException {
        return sellerRepository.findById(id)
                .orElseThrow(()->new SellerException("Seller khồng tồn tại với id " + id));
    }

    @Override
    public Seller getSellerByEmail(String email) throws Exception {
        Seller seller = sellerRepository.findByEmail(email);
        if (seller == null) {
            throw new Exception("Seller không tồn tại...");
        }
        return seller;
    }

    @Override
    public List<Seller> getAllSellers(AccountStatus status) {
        return sellerRepository.findByAccountStatus(status);
    }

    @Override
    public Seller updateSeller(Long id, Seller seller) throws Exception {
        Seller existingSeller = this.getSellerById(id);
        if(seller.getSellerName() != null ){
            existingSeller.setSellerName(seller.getSellerName());
        }
        if(seller.getPhone() != null ){
            existingSeller.setPhone(seller.getPhone());
        }
        if (seller.getEmail() != null) {
            existingSeller.setEmail(seller.getEmail());
        }
        if(seller.getBusinessDetails() != null
                && seller.getBusinessDetails().getBusinessName() != null
        ){
            existingSeller.getBusinessDetails().setBusinessName(
                    seller.getBusinessDetails().getBusinessName()
            );
        }
        if(seller.getBankDetails() != null
                && seller.getBankDetails().getAccountNumber() != null
                && seller.getBankDetails().getAccountHolderName() !=null
                && seller.getBankDetails().getBankCode() != null
        ){
            existingSeller.getBankDetails().setAccountNumber(
                    seller.getBankDetails().getAccountNumber()
            );
            existingSeller.getBankDetails().setAccountHolderName(
                    seller.getBankDetails().getAccountHolderName()
            );
            existingSeller.getBankDetails().setBankCode(
                    seller.getBankDetails().getBankCode()
            );
        }
        if(seller.getPickupAddress() != null
                && seller.getPickupAddress().getAddress() != null
                && seller.getPickupAddress().getPhone() != null
                && seller.getPickupAddress().getCity() != null
                && seller.getPickupAddress().getState() != null
        ){
            existingSeller.getPickupAddress()
                    .setAddress(seller.getPickupAddress().getAddress());
            existingSeller.getPickupAddress().setCity(seller.getPickupAddress().getCity());
            existingSeller.getPickupAddress().setState(seller.getPickupAddress().getState());
            existingSeller.getPickupAddress().setPhone(seller.getPickupAddress().getPhone());
        }
        if(seller.getGSTIN() != null ){
            existingSeller.setGSTIN(seller.getGSTIN());
        }

        return sellerRepository.save(existingSeller);
    }

    @Override
    public void deleteSeller(Long id) throws Exception {
        Seller seller = getSellerById(id);
        sellerRepository.delete(seller);

    }

    @Override
    public Seller verifyEmail(String email, String otp) throws Exception {
        Seller seller = getSellerByEmail(email);
        seller.setEmailVerified(true);
        return sellerRepository.save(seller);
    }

    @Override
    public Seller updateSellerAccountStatus(Long sellerId, AccountStatus status) throws Exception {
        Seller seller = getSellerById(sellerId);
        seller.setAccountStatus(status);
        return sellerRepository.save(seller);
    }
}
