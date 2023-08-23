package com.example.bookMarkUrl.controller;

import com.example.bookMarkUrl.entity.MUser;
import com.example.bookMarkUrl.service.MUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MUserController {
  @Autowired
  private MUserService userService;

  @GetMapping("/user/login")
  public String userLogin(Model model) {
    return "user/login";
  }

  @GetMapping("/user/signup")
  public String userSignUp(Model model) {
    model.addAttribute("user", new MUser());
    return "user/signup";
  }

  @PostMapping("/user/signup")
  public String createUser(@ModelAttribute MUser user) {
    userService.createUser(user);
    return "redirect:/user/login";
  }
}
