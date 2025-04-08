package com.example.javaspringbootnavy1.controller;

import com.example.javaspringbootnavy1.doman.User_Role;
import com.example.javaspringbootnavy1.modal.VerificationCode;
import com.example.javaspringbootnavy1.repository.UserReponstitory;
import com.example.javaspringbootnavy1.request.LoginOtpRequest;
import com.example.javaspringbootnavy1.request.LoginRequest;
import com.example.javaspringbootnavy1.response.ApiResponse;
import com.example.javaspringbootnavy1.response.AuthResponse;
import com.example.javaspringbootnavy1.response.SignupRequest;
import com.example.javaspringbootnavy1.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/")
@RequiredArgsConstructor
public class AuthController {

    private final UserReponstitory userReponstitory;
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandle(@RequestBody SignupRequest req) throws Exception {

        String jwt= authService.createUser(req);

        AuthResponse res = new AuthResponse();
        res.setJwt(jwt);
        res.setMessage("register successful");
        res.setRole(User_Role.Role_Customer);

        return  ResponseEntity.ok(res);
    }

    @PostMapping("/sent/login-signup-otp")
    public ResponseEntity<ApiResponse> sendOtpHandle(
            @RequestBody LoginOtpRequest req) throws Exception {

        authService.sentLoginOtp(req.getEmail(), req.getRole());

        ApiResponse res = new ApiResponse();

        res.setMessage("otp sent successful");


        return  ResponseEntity.ok(res);
    }

    @PostMapping("/signing")
    public ResponseEntity<AuthResponse> loginHandle(
            @RequestBody LoginRequest req) throws Exception {

        AuthResponse authResponse = authService.signing(req);

        return  ResponseEntity.ok(authResponse);
    }

}
