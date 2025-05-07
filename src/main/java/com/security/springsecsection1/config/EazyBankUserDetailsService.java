package com.security.springsecsection1.config;

import com.security.springsecsection1.model.Customer;
import com.security.springsecsection1.repository.CustomerRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
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

    List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(customer.getRole()));

    return new User(customer.getEmail(), customer.getPwd(), authorities);
  }
}
