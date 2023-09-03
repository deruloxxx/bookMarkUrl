package com.example.bookMarkUrl.service;

import com.example.bookMarkUrl.entity.MUrlInfo;
import com.example.bookMarkUrl.entity.MUser;
import com.example.bookMarkUrl.entity.UrlInfo;
import com.example.bookMarkUrl.repository.MUrlInfoRepository;
import com.example.bookMarkUrl.repository.MUserRepository;
import com.example.bookMarkUrl.repository.UrlInfoRepository;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UrlInfoService {

  private MUrlInfoRepository mUrlInfoRepository;
  private UrlInfoRepository urlInfoRepository;
  private MUserRepository mUserRepository;

  @Autowired
  public UrlInfoService(
    MUrlInfoRepository mUrlInfoRepository,
    UrlInfoRepository urlInfoRepository,
    MUserRepository mUserRepository
  ) {
    this.mUrlInfoRepository = mUrlInfoRepository;
    this.urlInfoRepository = urlInfoRepository;
    this.mUserRepository = mUserRepository;
  }

  public Document connect(String url) throws IOException {
    return Jsoup.connect(url).get();
  }

  public void scrapeAndSaveUserUrl(String url, String userId) throws IOException {
    UrlInfo UrlInfo = new UrlInfo();
    UrlInfo.setUrl(url);

    MUser mUser = mUserRepository.findByUserId(userId);
    if (mUser != null) {
      UrlInfo.setMUser(mUser); // mUser エンティティをセット
    }

    Document document = Jsoup.connect(url).get();
    Element titleElement = document.select("meta[property=og:title]").first();
    Element descriptionElement = document.select("meta[property=og:description]").first();
    Element thumbnailElement = document.select("meta[property=og:image]").first();

    UrlInfo.setTitle(titleElement != null ? titleElement.attr("content") : document.title());
    UrlInfo.setDescription(descriptionElement != null ? descriptionElement.attr("content") : null);
    UrlInfo.setThumbnail(thumbnailElement != null ? thumbnailElement.attr("content") : null);

    urlInfoRepository.save(UrlInfo);
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
