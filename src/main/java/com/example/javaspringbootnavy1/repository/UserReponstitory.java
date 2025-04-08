package com.example.javaspringbootnavy1.repository;

import com.example.javaspringbootnavy1.modal.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserReponstitory extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
