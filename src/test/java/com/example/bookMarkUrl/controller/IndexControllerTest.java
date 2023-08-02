package com.example.bookMarkUrl.controller;

import com.example.bookMarkUrl.repository.UrlRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(IndexController.class)
class IndexControllerTest {
  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private UrlRepository urlRepository;
  @Test
  public void shouldReturnTopPage() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/"))
        .andExpect(MockMvcResultMatchers.status().isOk());
  }
}
