package com.example.bookMarkUrl.repository;

import com.example.bookMarkUrl.entity.MUrlScrapeInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MUrlInfoRepository extends JpaRepository<MUrlScrapeInfo, Long> {}
