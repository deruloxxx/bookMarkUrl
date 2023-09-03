package com.example.bookMarkUrl.controller;

import com.example.bookMarkUrl.entity.MUser;
import com.example.bookMarkUrl.entity.UrlInfo;
import com.example.bookMarkUrl.repository.MUserRepository;
import com.example.bookMarkUrl.repository.UrlInfoRepository;
import com.example.bookMarkUrl.service.MUserService;
import com.example.bookMarkUrl.service.UrlInfoService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/user")
public class MUserController {

  private final MUserService mUserService;
  private final UrlInfoService urlInfoService;
  private final MUserRepository mUserRepository;
  private final UrlInfoRepository urlInfoRepository;

  @Autowired
  public MUserController(
    MUserService mUserService,
    UrlInfoService urlInfoService,
    MUserRepository mUserRepository,
    UrlInfoRepository urlInfoRepository
  ) {
    this.mUserService = mUserService;
    this.urlInfoService = urlInfoService;
    this.mUserRepository = mUserRepository;
    this.urlInfoRepository = urlInfoRepository;
  }

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
      return "redirect:/error";
    }
    return "redirect:/user/url";
  }

  @GetMapping("/signup")
  public String userSignUp(Model model) {
    model.addAttribute("user", new MUser());
    return "user/signup";
  }

  @PostMapping("/signup")
  public String createUser(
    @Valid @ModelAttribute MUser user,
    BindingResult bindingResult,
    Model model,
    RedirectAttributes redirectAttributes
  ) {
    if (bindingResult.hasErrors()) {
      redirectAttributes.addFlashAttribute("validationErrors", "正しいメールアドレスを入力してください");
      return "redirect:/user/signup";
    }

    MUser existingUser = mUserRepository.findByUserId(user.getUserId());
    if (existingUser != null) {
      redirectAttributes.addFlashAttribute("validationErrors", "このメールアドレスはすでに使用されています。");
      return "redirect:/user/signup";
    }
    try {
      mUserService.createUser(user);
    } catch (Exception e) {
      redirectAttributes.addFlashAttribute("errorMessage", "Failed to SignUp User. Please try again.");
      return "redirect:/user/signup";
    }
    return "redirect:/user/login";
  }

  @PostMapping("/delete")
  public String deleteUser(@RequestParam String deleteUserId, RedirectAttributes redirectAttributes) {
    try {
      mUserService.deleteUser(deleteUserId);
    } catch (Exception e) {
      e.printStackTrace();

      redirectAttributes.addFlashAttribute("errorMessage", "Failed to delete User. Please try again.");
      return "redirect:/user/url";
    }
    return "redirect:/user/login";
  }

  @PostMapping("/url/delete")
  public String deleteUrl(@RequestParam Long id, RedirectAttributes redirectAttributes) {
    try {
      urlInfoRepository.deleteById(id);
    } catch (Exception e) {
      e.printStackTrace();

      redirectAttributes.addFlashAttribute("errorMessage", "Failed to delete URL. Please try again.");
      return "redirect:/user/url";
    }
    return "redirect:/user/url";
  }
}
