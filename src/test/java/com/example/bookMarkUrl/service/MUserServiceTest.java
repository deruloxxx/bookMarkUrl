package com.example.bookMarkUrl.service;

import com.example.bookMarkUrl.entity.MUser;
import com.example.bookMarkUrl.entity.UrlInfo;
import com.example.bookMarkUrl.repository.MUserRepository;
import com.example.bookMarkUrl.repository.UrlInfoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class MUserServiceTest {
  @Autowired
  MUserService mUserService;

  @MockBean
  private PasswordEncoder passwordEncoder;

  @MockBean
  private MUserRepository mUserRepository;

  @MockBean
  private UrlInfoRepository urlInfoRepository;

  MUserServiceTest() {
  }

  @Test
  public void testGetUserUrls() {
    MUser user = new MUser();
    user.setUserId("testUser");

    List<UrlInfo> mockUrlList = Arrays.asList(new UrlInfo(), new UrlInfo());
    when(mUserRepository.findByUserId("testUser")).thenReturn(user);
    when(urlInfoRepository.findByMUserUserId("testUser")).thenReturn(mockUrlList);

    List<UrlInfo> result = mUserService.getUserUrls("testUser");
    assertNotNull(result);
    assertEquals(2, result.size());
  }
}