package com.example.javaspringbootnavy1.response;

import com.example.javaspringbootnavy1.doman.User_Role;
import lombok.Data;

@Data
public class AuthResponse {
    private String jwt;
    private String message;
    private User_Role role;

}
