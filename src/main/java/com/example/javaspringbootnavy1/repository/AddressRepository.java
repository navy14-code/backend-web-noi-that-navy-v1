package com.example.javaspringbootnavy1.repository;

import com.example.javaspringbootnavy1.modal.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
