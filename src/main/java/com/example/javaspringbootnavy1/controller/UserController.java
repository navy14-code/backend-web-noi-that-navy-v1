package com.example.javaspringbootnavy1.controller;

import com.example.javaspringbootnavy1.modal.User;
import com.example.javaspringbootnavy1.response.AuthResponse;
import com.example.javaspringbootnavy1.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users/profile")
    public ResponseEntity<User> createUserHandle(
            @RequestHeader("Authorization") String jwt
            ) throws Exception {

        User user = userService.findUserByJwtToken(jwt);

        return  ResponseEntity.ok(user);

    }
}
