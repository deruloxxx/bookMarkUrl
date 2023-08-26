package com.example.bookMarkUrl.repository;

import com.example.bookMarkUrl.entity.MUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MUserRepository extends JpaRepository<MUser, String> {
  MUser findByUserId(String userId);
}
