package com.example.javaspringbootnavy1.service.impl;

import com.example.javaspringbootnavy1.doman.User_Role;
import com.example.javaspringbootnavy1.modal.Seller;
import com.example.javaspringbootnavy1.modal.User;
import com.example.javaspringbootnavy1.repository.SellerRepository;
import com.example.javaspringbootnavy1.repository.UserReponstitory;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CustomerUserServiceImpl implements UserDetailsService {

    private final UserReponstitory userReponstitory;
    private final SellerRepository sellerRepository;
    private static final String SELLER_PREFIX = "seller_";

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(username.startsWith(SELLER_PREFIX)){
            String actualUsername = username.substring(SELLER_PREFIX.length());
            Seller seller = sellerRepository.findByEmail(actualUsername);
            if(seller != null){
                return buildUserDetails(seller.getEmail(),seller.getPassword(),seller.getRole());
            }
        }else {
            User user = userReponstitory.findByEmail(username);
            if(user != null){
                return buildUserDetails(user.getEmail(),user.getPassword(),user.getRole());
            }
        }
        throw new UsernameNotFoundException("user or seller not found with email: " + username);
    }

    private UserDetails buildUserDetails(String email, String password, User_Role role) {

        if(role==null) role= User_Role.Role_Customer;
        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority(role.toString()));
        return new org.springframework.security.core.userdetails.User(
                email,
                password, authorityList
        );
    }
}
