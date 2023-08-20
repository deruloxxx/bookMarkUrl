package com.example.bookMarkUrl.controller;

import com.example.bookMarkUrl.repository.UrlInfoRepository;
import com.example.bookMarkUrl.service.UrlInfoService;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@WebMvcTest(UrlInfoController.class)
class UrlInfoControllerTest {
  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private UrlInfoRepository urlInfoRepository;
  @MockBean
  private UrlInfoService urlInfoService;
  @Test
  @DisplayName("TOPページが表示される")
  public void shouldReturnTopPage() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/"))
        .andExpect(MockMvcResultMatchers.status().isOk());
  }
  @Test
  @DisplayName("URLを追加することができる")
  public void shouldAddUrl() throws Exception {

    // HTMLドキュメントオブジェクトの作成
    Document document = new Document("");
    // タイトルの要素の追加
    Element titleElement = new Element("meta");
    titleElement.attr("property", "og:title");
    titleElement.attr("content", "Example Title");
    document.head().appendChild(titleElement);

    // 説明の要素の追加
    Element descriptionElement = new Element("meta");
    descriptionElement.attr("property", "og:description");
    descriptionElement.attr("content", "Example Description");
    document.head().appendChild(descriptionElement);

    // サムネイルの要素の追加
    Element thumbnailElement = new Element("meta");
    thumbnailElement.attr("property", "og:image");
    thumbnailElement.attr("content", "http://example.com/image.jpg");
    document.head().appendChild(thumbnailElement);

    // urlScraperServiceが呼ばれた時にDocumentオブジェクトを返す
    Mockito.doNothing().when(urlInfoService).scrapeAndSaveUrl(Mockito.anyString());

    mockMvc.perform(MockMvcRequestBuilders.post("/add").param("url", "https://example.com"))
        .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
        .andExpect(MockMvcResultMatchers.redirectedUrl("/"));

    Mockito.verify(urlInfoService, Mockito.times(1)).scrapeAndSaveUrl(Mockito.anyString());
  }

  @Test
  @DisplayName("追加したURLにディスクリプションとサムネイル画像が設定されていない場合は'設定なし'の文言が表示される")
  public void shouldDisplayTextNoSetting() throws Exception {}

  @Test
  @DisplayName("登録されているURLを削除することができる")
  public void shouldDeleteUrlAndRedirect() throws Exception {
    Long idToDelete = 1L;

    mockMvc.perform(MockMvcRequestBuilders.post("/delete").param("id", idToDelete.toString()))
        .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
        .andExpect(MockMvcResultMatchers.redirectedUrl("/"));

    verify(urlInfoRepository, times(1)).deleteById(idToDelete);
  }

  @Test
  @DisplayName("emailとpasswordの登録でユーザーを登録することができる")
  public void shouldUserRegister() throws Exception {}

  @Test
  @DisplayName("登録されているユーザーでログインすることができる")
  public void shouldUserLogin() throws Exception {}

  @Test
  @DisplayName("追加したURLをお気に入りとして登録できる")
  public void shouldFavoriteRegisterUrl() throws Exception {}
}
