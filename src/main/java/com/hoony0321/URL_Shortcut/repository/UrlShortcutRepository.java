package com.hoony0321.URL_Shortcut.repository;

import com.hoony0321.URL_Shortcut.domain.UrlShortcut;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UrlShortcutRepository extends JpaRepository<UrlShortcut, Long> {
    Optional<UrlShortcut> findByOriginalUrl(String originalUrl);
    Optional<UrlShortcut> findByShortUrl(String shortUrl);
}
