package com.example.bookMarkUrl.service;

import com.example.bookMarkUrl.entity.MUrlScrapeInfo;
import com.example.bookMarkUrl.entity.MUser;
import com.example.bookMarkUrl.entity.UrlInfo;
import com.example.bookMarkUrl.repository.MUrlInfoRepository;
import com.example.bookMarkUrl.repository.MUserRepository;
import com.example.bookMarkUrl.repository.UrlInfoRepository;
import com.example.bookMarkUrl.service.interfaces.UrlScrape;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
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

  private void scrapeUrlInfo(Document document, UrlScrape urlScrape) {
    Element titleElement = document.select("meta[property=og:title]").first();
    Element descriptionElement = document.select("meta[property=og:description]").first();
    Element thumbnailElement = document.select("meta[property=og:image]").first();

    urlScrape.setTitle(titleElement != null ? titleElement.attr("content") : document.title());
    urlScrape.setDescription(descriptionElement != null ? descriptionElement.attr("content") : null);
    urlScrape.setThumbnail(thumbnailElement != null ? thumbnailElement.attr("content") : null);
  }

  private void scrapeAndSave(String url, UrlScrape urlScrape, JpaRepository repository, String userId)
    throws IOException {
    urlScrape.setUrl(url);
    setMUserIfExists(userId, urlScrape);
    Document document = connect(url);
    scrapeUrlInfo(document, urlScrape);
    repository.save(urlScrape);
  }

  private void setMUserIfExists(String userId, UrlScrape urlScrape) {
    if (userId == null || !(urlScrape instanceof UrlInfo)) return;

    MUser mUser = mUserRepository.findByUserId(userId);
    if (mUser != null) {
      ((UrlInfo) urlScrape).setMUser(mUser);
    }
  }

  public void scrapeAndSaveUserUrl(String url, String userId) throws IOException {
    scrapeAndSave(url, new UrlInfo(), urlInfoRepository, userId);
  }

  public void scrapeAndSaveUrl(String url) throws IOException {
    scrapeAndSave(url, new MUrlScrapeInfo(), mUrlInfoRepository, null);
  }
}
