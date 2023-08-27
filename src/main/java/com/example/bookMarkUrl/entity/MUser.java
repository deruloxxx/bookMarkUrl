package com.example.bookMarkUrl.entity;

import jakarta.persistence.*;
import java.util.List;
import jdk.jfr.Enabled;
import lombok.Data;

@Entity
@Data
@Table(name = "m_user")
public class MUser {
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Id
  @Column(name = "user_id")
  private String userId;

  private String password;
  private String name;
  private String role;

  @OneToMany(mappedBy = "mUser", cascade = CascadeType.ALL)
  private List<UrlInfo> urlInfos;
}
