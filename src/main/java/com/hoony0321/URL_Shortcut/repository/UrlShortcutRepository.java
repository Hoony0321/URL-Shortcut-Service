package com.hoony0321.URL_Shortcut.repository;

import com.hoony0321.URL_Shortcut.domain.UrlShortcut;
import com.hoony0321.URL_Shortcut.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UrlShortcutRepository extends JpaRepository<UrlShortcut, Long> {
    Optional<UrlShortcut> findByUserAndOriginalUrl(User user, String originalUrl);
    Optional<UrlShortcut> findByUserAndShortUrl(User user, String shortUrl);

    List<UrlShortcut> findAllByUser(User user);
}
