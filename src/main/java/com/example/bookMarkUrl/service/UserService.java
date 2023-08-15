package com.example.bookMarkUrl.service;

import com.example.bookMarkUrl.entity.User;
import com.example.bookMarkUrl.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
  @Autowired
  private UserRepository userRepository;

  public User registerUser(User user) throws Exception {
    Optional<User> existingUser = userRepository.findByUserName(user.getUserName());
    if (existingUser.isPresent()) {
      throw new Exception("Username already exists.");
    }
    return userRepository.save(user);
  }
}
