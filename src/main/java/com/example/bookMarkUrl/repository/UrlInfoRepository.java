package com.example.bookMarkUrl.repository;
import com.example.bookMarkUrl.entity.UrlInfo;
import org.springframework.data.jpa.repository.JpaRepository;
public interface UrlInfoRepository extends JpaRepository<UrlInfo, Long> {
}
