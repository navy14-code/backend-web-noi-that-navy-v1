package com.example.javaspringbootnavy1.service;

import com.example.javaspringbootnavy1.modal.User;

public interface UserService {

    User findUserByJwtToken(String jwt) throws Exception;
    User findUserByEmail(String email) throws Exception;
}
