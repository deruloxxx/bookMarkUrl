package com.example.bookMarkUrl.service;

import com.example.bookMarkUrl.entity.MUser;
import com.example.bookMarkUrl.repository.MUserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MUserService {
  @Autowired
  private PasswordEncoder passwordEncoder;
  @Autowired
  MUserRepository userRepository;

  @Transactional
  public MUser createUser(MUser user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    user.setRole("ROLE_USER");
    return userRepository.save(user);
  }
}
