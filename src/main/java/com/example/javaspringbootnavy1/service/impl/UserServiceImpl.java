package com.example.javaspringbootnavy1.service.impl;

import com.example.javaspringbootnavy1.config.JwtProvider;
import com.example.javaspringbootnavy1.modal.User;
import com.example.javaspringbootnavy1.repository.UserReponstitory;
import com.example.javaspringbootnavy1.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserReponstitory userReponstitory;
    private final JwtProvider jwtProvider;

    @Override
    public User findUserByJwtToken(String jwt) throws Exception {
        String email= jwtProvider.getEmailFromJwtToken(jwt);


        return this.findUserByEmail(email);
    }

    @Override
    public User findUserByEmail(String email) throws Exception {
        User user= userReponstitory.findByEmail(email);
        if(user==null){
            throw new Exception("user not found with email -"+ email);
        }
        return user;
    }

}
