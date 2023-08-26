package com.example.bookMarkUrl.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "m_user")
public class MUser {
  @Id
  @Column(name = "user_id")
  private String userId;

  private String password;
  private String name;
  private String role;

  @OneToMany(mappedBy = "mUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<UrlInfo> urlInfos;
}
