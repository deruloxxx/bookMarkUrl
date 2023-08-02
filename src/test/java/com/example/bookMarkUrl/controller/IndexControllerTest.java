package com.example.bookMarkUrl.controller;

import com.example.bookMarkUrl.entity.UrlInfo;
import com.example.bookMarkUrl.repository.UrlRepository;
import com.example.bookMarkUrl.service.UrlScraperService;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
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
  @MockBean
  private UrlScraperService urlScraperService;
  @Test
  public void shouldReturnTopPage() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/"))
        .andExpect(MockMvcResultMatchers.status().isOk());
  }
  @Test
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
    Mockito.when(urlScraperService.connect(Mockito.anyString())).thenReturn(document);

    mockMvc.perform(MockMvcRequestBuilders.post("/add").param("url", "http://example.com"))
        .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
        .andExpect(MockMvcResultMatchers.redirectedUrl("/"));

    Mockito.verify(urlRepository, Mockito.times(1)).save(Mockito.any(UrlInfo.class));
  }
}
