package com.java.developer.test.service;

import com.java.developer.test.dto.UserDTO;
import com.java.developer.test.model.UserDAO;
import com.java.developer.test.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUserDtlService implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private PasswordEncoder bcyptEncoder;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    final UserDAO user = userRepository.findByUsername(username);
    if (user == null) {
      throw new UsernameNotFoundException("User not found with username: " + username);
    }
    return new User(user.getUsername(), user.getPassword(), new ArrayList<>());
  }

  public UserDAO save(UserDTO user) {
    UserDAO newUser = new UserDAO(user.getUsername(),
        bcyptEncoder.encode(user.getPassword()));
    return userRepository.save(newUser);
  }
}
