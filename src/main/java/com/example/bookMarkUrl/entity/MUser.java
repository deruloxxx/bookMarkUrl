package com.example.bookMarkUrl.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "m_user")
public class MUser {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private String userId;
  private String name;
  private String password;
}
