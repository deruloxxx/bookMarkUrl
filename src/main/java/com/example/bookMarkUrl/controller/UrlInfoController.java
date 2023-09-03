package com.example.bookMarkUrl.controller;

import com.example.bookMarkUrl.repository.MUrlInfoRepository;
import com.example.bookMarkUrl.service.UrlInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
  public String addUrl(@RequestParam String url, RedirectAttributes redirectAttributes) {
    try {
      urlInfoService.scrapeAndSaveUrl(url);
    } catch (Exception e) {
      e.printStackTrace();

      redirectAttributes.addFlashAttribute("errorMessage", "Failed to add URL. Please try again.");
      return "redirect:/";
    }
    return "redirect:/";
  }

  @PostMapping("/delete")
  public String deleteUrl(@RequestParam Long id, RedirectAttributes redirectAttributes) {
    try {
      mUrlInfoRepository.deleteById(id);
    } catch (Exception e) {
      e.printStackTrace();

      redirectAttributes.addFlashAttribute("errorMessage", "Failed to delete URL. Please try again.");
      return "redirect:/";
    }
    return "redirect:/";
  }
}
