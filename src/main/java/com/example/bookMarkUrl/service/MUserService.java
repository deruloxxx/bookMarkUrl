package com.example.bookMarkUrl.service;

import com.example.bookMarkUrl.entity.MUser;
import com.example.bookMarkUrl.entity.UrlInfo;
import com.example.bookMarkUrl.repository.MUserRepository;
import com.example.bookMarkUrl.repository.UrlInfoRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MUserService {

  private PasswordEncoder passwordEncoder;

  private MUserRepository mUserRepository;

  private UrlInfoRepository urlInfoRepository;

  @Autowired
  public MUserService(
    PasswordEncoder passwordEncoder,
    MUserRepository mUserRepository,
    UrlInfoRepository urlInfoRepository
  ) {
    this.passwordEncoder = passwordEncoder;
    this.mUserRepository = mUserRepository;
    this.urlInfoRepository = urlInfoRepository;
  }

  public List<UrlInfo> getUserUrls(String userId) {
    MUser user = mUserRepository.findByUserId(userId);
    if (user == null) {
      throw new UsernameNotFoundException("User not found");
    }
    return urlInfoRepository.findByMUserUserId(userId);
  }

  @Transactional
  public MUser createUser(MUser user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    user.setRole("ROLE_USER");
    return mUserRepository.save(user);
  }

  @Transactional
  public void deleteUser(String userId) {
    mUserRepository.deleteById(userId);
  }
}
