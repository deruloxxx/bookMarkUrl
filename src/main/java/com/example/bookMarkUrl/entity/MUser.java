package com.example.bookMarkUrl.entity;

import jakarta.persistence.*;
import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
@Table(name = "m_user")
public class MUser {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  private Long id;

  @Column(name = "user_id", unique = true)
  @Email(message = "メールアドレスの形式が無効です")
  private String userId;

  @Size(min = 7, message = "パスワードは7文字以上である必要があります")
  @Pattern(regexp = "[A-Za-z0-9]+", message = "パスワードは半角英数字である必要があります")
  private String password;
  private String name;
  private String role;

  @OneToMany(mappedBy = "mUser", cascade = CascadeType.ALL)
  private List<UrlInfo> urlInfos;
}
