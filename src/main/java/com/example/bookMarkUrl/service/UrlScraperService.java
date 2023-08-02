package com.example.bookMarkUrl.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class UrlScraperService {
  public Document connect(String url) throws IOException {
    return Jsoup.connect(url).get();
  }
}
