package com.security.springsecsection1.repository;

import com.security.springsecsection1.model.Customer;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

  Optional<Customer> findByEmail(String email);
}
