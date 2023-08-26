package com.example.bookMarkUrl.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "m_user")
public class MUser {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "user_id")
  private String userId;

  private String password;
  private String name;
  private String role;
}
