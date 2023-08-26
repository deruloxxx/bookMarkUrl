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
  private String url;
  private String title;
  private String description;
  private String thumbnail;
}
