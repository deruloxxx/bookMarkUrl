package com.example.bookMarkUrl.service;

import com.example.bookMarkUrl.entity.MUrlScrapeInfo;
import com.example.bookMarkUrl.entity.MUser;
import com.example.bookMarkUrl.entity.UrlInfo;
import com.example.bookMarkUrl.repository.MUrlInfoRepository;
import com.example.bookMarkUrl.repository.MUserRepository;
import com.example.bookMarkUrl.repository.UrlInfoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UrlInfoServiceTest {

  @Mock
  private MUrlInfoRepository mUrlInfoRepository;

  @Mock
  private UrlInfoRepository urlInfoRepository;

  @Mock
  private MUserRepository mUserRepository;

  @InjectMocks
  private UrlInfoService urlInfoService;

  @Test
  @DisplayName("ログインしているユーザーがURLを登録できる")
  public void testScrapeAndSaveUserUrl() throws Exception {
    String url = "https://example.com/";
    String userId = "user";

    when(mUserRepository.findByUserId(anyString())).thenReturn(new MUser());

    urlInfoService.scrapeAndSaveUserUrl(url, userId);

    verify(urlInfoRepository, times(1)).save(any(UrlInfo.class));
  }

  @Test
  @DisplayName("ログインしていないユーザーがURLを登録できる")
  public void testScrapeAndSaveUrl() throws Exception {
    String url = "https://www.example.com";

    urlInfoService.scrapeAndSaveUrl(url);

    verify(mUrlInfoRepository, times(1)).save(any(MUrlScrapeInfo.class));
  }

  @Test
  @DisplayName("ログインしているしていないに関わらずユーザーがURL以外のものを登録しようとしたらエラーが発生する")
  public void testScrapeAndSaveUrlError() throws Exception {
    String invalidInput = "NotUrl";

    IllegalArgumentException exception = assertThrows(
      IllegalArgumentException.class,
      () -> urlInfoService.scrapeAndSaveUrl(invalidInput)
    );

    assertEquals("Malformed URL: NotUrl", exception.getMessage());

    verify(mUrlInfoRepository, times(0)).save(any(MUrlScrapeInfo.class));
  }
}
