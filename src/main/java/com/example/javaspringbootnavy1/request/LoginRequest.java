package com.example.javaspringbootnavy1.request;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String otp;
}
