package com.example.bookMarkUrl.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "t_url_info")
public class UrlInfo implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String url;
  private String title;
  private String description;
  private String thumbnail;

  @ManyToOne
  @JoinColumn(name = "user_id", referencedColumnName = "user_id")
  private MUser mUser;
}
