package com.example.javaspringbootnavy1.service;

import com.example.javaspringbootnavy1.doman.User_Role;
import com.example.javaspringbootnavy1.modal.User;
import com.example.javaspringbootnavy1.request.LoginRequest;
import com.example.javaspringbootnavy1.response.AuthResponse;
import com.example.javaspringbootnavy1.response.SignupRequest;

public interface AuthService {

    void sentLoginOtp(String  email, User_Role role) throws Exception;
    String createUser(SignupRequest req) throws Exception;
    AuthResponse signing(LoginRequest req) throws Exception;


}
