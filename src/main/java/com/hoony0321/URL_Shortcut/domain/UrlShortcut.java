package com.hoony0321.URL_Shortcut.domain;

import com.hoony0321.URL_Shortcut.form.CreateUrlShortcutForm;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "url_shortcut")
public class UrlShortcut {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "url_shortcut_id")
        private Long id;

        private String originalUrl;

        private String shortUrl;

        private Integer count = 0;

        @ManyToOne
        @JoinColumn(name = "user_id")
        private User user;

        public static UrlShortcut createEntity(CreateUrlShortcutForm form, User user) {
            UrlShortcut entity = new UrlShortcut();
            entity.originalUrl = form.getUrl();
            entity.shortUrl = form.getShortcut();
            entity.user = user;
            entity.count = 0;
            return entity;
        }

        public void setShortUrl(String shortUrl){
            this.shortUrl = shortUrl;
        }

        public void addCount() {
            this.count++;
        }

        public void setCount(int count){
            this.count = count;
        }
}
