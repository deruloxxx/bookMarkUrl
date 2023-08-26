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
  @Autowired
  private MUrlInfoRepository mUrlRepository;
  @Autowired
  private UrlInfoService urlInfoService;

  public UrlInfoController(MUrlInfoRepository mUrlInfoRepository) {
    this.mUrlRepository = mUrlInfoRepository;
  }
  @GetMapping("/")
  public String index(Model model) {
    model.addAttribute("mUrlInfos", mUrlRepository.findAll());
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
    mUrlRepository.deleteById(id);
    return "redirect:/";
  }
}
