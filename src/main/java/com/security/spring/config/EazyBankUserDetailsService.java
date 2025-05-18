package com.security.spring.config;

import com.security.spring.model.Customer;
import com.security.spring.repository.CustomerRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EazyBankUserDetailsService implements UserDetailsService {

  private final CustomerRepository customerRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Customer customer = customerRepository.findByEmail(username)
        .orElseThrow(() -> new UsernameNotFoundException("User details not found:" + username));

    List<SimpleGrantedAuthority> authorities = customer.getAuthorities().stream()
        .map(authority -> new SimpleGrantedAuthority(authority.getName()))
        .toList();

    return new User(customer.getEmail(), customer.getPwd(), authorities);
  }
}
