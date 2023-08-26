package com.example.bookMarkUrl.service;

import com.example.bookMarkUrl.entity.MUrlInfo;
import com.example.bookMarkUrl.repository.MUrlInfoRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class UrlInfoService {
  @Autowired
  private MUrlInfoRepository mUrlInfoRepository;

  public Document connect(String url) throws IOException {
    return Jsoup.connect(url).get();
  }

  public void scrapeAndSaveUrl(String url) throws IOException {
    MUrlInfo MUrlInfo = new MUrlInfo();
    MUrlInfo.setUrl(url);

    Document document = Jsoup.connect(url).get();
    Element titleElement = document.select("meta[property=og:title]").first();
    Element descriptionElement = document.select("meta[property=og:description]").first();
    Element thumbnailElement = document.select("meta[property=og:image]").first();

    MUrlInfo.setTitle(titleElement != null ? titleElement.attr("content") : document.title());
    MUrlInfo.setDescription(descriptionElement != null ? descriptionElement.attr("content") : null);
    MUrlInfo.setThumbnail(thumbnailElement != null ? thumbnailElement.attr("content") : null);

    mUrlInfoRepository.save(MUrlInfo);
  }
}
