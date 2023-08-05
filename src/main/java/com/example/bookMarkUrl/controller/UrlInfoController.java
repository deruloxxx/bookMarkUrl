package com.example.bookMarkUrl.controller;

import com.example.bookMarkUrl.repository.UrlInfoRepository;
import com.example.bookMarkUrl.service.UrlInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UrlInfoController {
  @Autowired
  private UrlInfoRepository urlRepository;
  @Autowired
  private UrlInfoService urlInfoService;

  public UrlInfoController(UrlInfoRepository urlInfoRepository) {
    this.urlRepository = urlInfoRepository;
  }
  @GetMapping("/")
  public String index(Model model) {
    model.addAttribute("urlInfos", urlRepository.findAll());
    return "index";
  }
  @PostMapping("/add")
  public String addUrl(@RequestParam String url) {
    try {
      urlInfoService.scrapeAndSaveUrl(url);
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
