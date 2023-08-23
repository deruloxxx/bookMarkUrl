package com.example.bookMarkUrl.service;

import com.example.bookMarkUrl.entity.MUser;
import com.example.bookMarkUrl.repository.MUserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MUserService {
  @Autowired
  MUserRepository userRepository;

  @Transactional
  public MUser createUser(MUser user) {
    return userRepository.save(user);
  }
}
