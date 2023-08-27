package com.example.bookMarkUrl.repository;

import com.example.bookMarkUrl.entity.UrlInfo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UrlInfoRepository extends JpaRepository<UrlInfo, Long> {
  @Query("SELECT u FROM UrlInfo u WHERE u.mUser.userId = :userId")
  List<UrlInfo> findByMUserUserId(@Param("userId") String userId);
}
