package com.example.bookMarkUrl.controller;

import com.example.bookMarkUrl.dto.UserRegisterDTO;
import com.example.bookMarkUrl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
  @Autowired
  private UserService userService;
  @GetMapping("/user/login")
  public String userLogin(Model model) {
    return "user/login";
  }
  @GetMapping("/user/signup")
  public String userAdd(Model model) {
    model.addAttribute("userRequest", new UserRegisterDTO());
    return "user/signup";
  }
}
