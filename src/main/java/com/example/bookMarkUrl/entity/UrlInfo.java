package com.example.bookMarkUrl.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "m_url_info")
public class UrlInfo {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @ManyToOne
  @JoinColumn(name = "user_id")
  private MUser mUser;

  private String url;
  private String title;
  private String description;
  private String thumbnail;
}
