package com.example.bookMarkUrl.repository;
import com.example.bookMarkUrl.entity.UrlInfo;
import org.springframework.data.jpa.repository.JpaRepository;
public interface UrlRepository extends JpaRepository<UrlInfo, Long> {
}
