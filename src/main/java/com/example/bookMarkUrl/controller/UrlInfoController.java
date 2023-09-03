package com.example.bookMarkUrl.controller;

import com.example.bookMarkUrl.repository.MUrlInfoRepository;
import com.example.bookMarkUrl.service.UrlInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UrlInfoController {

  private final MUrlInfoRepository mUrlInfoRepository;
  private final UrlInfoService urlInfoService;

  @Autowired
  public UrlInfoController(MUrlInfoRepository mUrlInfoRepository, UrlInfoService urlInfoService) {
    this.mUrlInfoRepository = mUrlInfoRepository;
    this.urlInfoService = urlInfoService;
  }

  @GetMapping("/")
  public String index(Model model) {
    model.addAttribute("mUrlInfos", mUrlInfoRepository.findAll());
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
    mUrlInfoRepository.deleteById(id);
    return "redirect:/";
  }
}
