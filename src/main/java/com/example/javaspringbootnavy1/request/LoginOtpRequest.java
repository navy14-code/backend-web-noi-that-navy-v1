package com.example.javaspringbootnavy1.request;

import com.example.javaspringbootnavy1.doman.User_Role;
import lombok.Data;

@Data
public class LoginOtpRequest {
    private String email;
    private String otp;
    private User_Role role;
}
