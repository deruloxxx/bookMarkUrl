package com.example.bookMarkUrl.controller;

import com.example.bookMarkUrl.entity.UrlInfo;
import com.example.bookMarkUrl.repository.UrlRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {
  private final UrlRepository urlRepository;
  public IndexController(UrlRepository urlInfoRepository) {
    this.urlRepository = urlInfoRepository;
  }
  @GetMapping("/")
  public String index(Model model) {
    model.addAttribute("urlInfos", urlRepository.findAll());
    return "index";
  }
  @PostMapping("/add")
  public String addUrl(@RequestParam String url) {
    UrlInfo urlInfo = new UrlInfo();
    urlInfo.setUrl(url);

    try {
      Document document = Jsoup.connect(url).get();
      Element titleElement = document.select("meta[property=og:title]").first();
      Element descriptionElement = document.select("meta[property=og:description]").first();
      Element thumbnailElement = document.select("meta[property=og:image]").first();

      urlInfo.setTitle(titleElement != null ? titleElement.attr("content") : document.title());
      urlInfo.setDescription(descriptionElement != null ? descriptionElement.attr("content") : "");
      urlInfo.setThumbnail(thumbnailElement != null ? thumbnailElement.attr("content") : "");

      urlRepository.save(urlInfo);
    } catch (Exception e) {
      e.printStackTrace();
    }

    return "redirect:/";
  }
  @PostMapping("/delete")
  public String deleteUrl(@RequestParam Long id) {
    urlRepository.deleteById(id);
    return "redirect:/";
  }
}
