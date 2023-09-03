package com.example.bookMarkUrl.entity;

import com.example.bookMarkUrl.service.interfaces.UrlScrape;
import jakarta.persistence.*;
import java.io.Serializable;
import lombok.Data;

@Data
@Entity
@Table(name = "t_url_info")
public class UrlInfo implements Serializable, UrlScrape {

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
