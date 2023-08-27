package com.example.bookMarkUrl.controller;

import com.example.bookMarkUrl.entity.MUser;
import com.example.bookMarkUrl.entity.UrlInfo;
import com.example.bookMarkUrl.repository.MUserRepository;
import com.example.bookMarkUrl.repository.UrlInfoRepository;
import com.example.bookMarkUrl.service.MUserService;
import com.example.bookMarkUrl.service.UrlInfoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class MUserController {

  @Autowired
  private MUserService mUserService;

  @Autowired
  private UrlInfoService urlInfoService;

  @Autowired
  private MUserRepository mUserRepository;

  @Autowired
  private UrlInfoRepository urlInfoRepository;

  @GetMapping("/login")
  public String userLogin(Model model) {
    return "user/login";
  }

  @PostMapping("/login")
  public String postUserLogin() {
    return "redirect:/url";
  }

  @PostMapping("/logout")
  public String userLogout() {
    return "redirect:/logout";
  }

  @GetMapping("/url")
  public String userUrl(Model model) {
    String currentUserId = SecurityContextHolder.getContext().getAuthentication().getName();
    List<UrlInfo> urlInfos = mUserService.getUserUrls(currentUserId);

    model.addAttribute("currentUserId", currentUserId);
    model.addAttribute("currentUserName", currentUserId);
    model.addAttribute("urlInfos", urlInfos);
    return "user/url";
  }

  @PostMapping("/add")
  public String addUserUrl(@RequestParam String url, @RequestParam String userId) {
    try {
      urlInfoService.scrapeAndSaveUserUrl(url, userId);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return "redirect:/user/url";
  }

  @GetMapping("/signup")
  public String userSignUp(Model model) {
    model.addAttribute("user", new MUser());
    return "user/signup";
  }

  @PostMapping("/signup")
  public String createUser(@ModelAttribute MUser user) {
    mUserService.createUser(user);
    return "redirect:/user/login";
  }
}
