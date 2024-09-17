package com.hoony0321.URL_Shortcut.service;

import com.hoony0321.URL_Shortcut.domain.UrlShortcut;
import com.hoony0321.URL_Shortcut.form.CreateUrlShortcutForm;
import com.hoony0321.URL_Shortcut.form.UpdateShortcutForm;
import com.hoony0321.URL_Shortcut.repository.UrlShortcutRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UrlShortcutService {

    private final UrlShortcutRepository urlShortcutRepository;

    public UrlShortcut findUrlShortcut(String url){
        UrlShortcut entity = urlShortcutRepository.findByOriginalUrl(url).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 URL입니다."));
        return entity;
    }

    public List<UrlShortcut> findAllUrlShortcut(){
        return urlShortcutRepository.findAll();
    }

    @Transactional
    public void createUrlShortcut(CreateUrlShortcutForm form){
        urlShortcutRepository.findByOriginalUrl(form.getUrl()).ifPresent(urlShortcut -> {
            throw new IllegalStateException("이미 존재하는 URL입니다.");
        });

        urlShortcutRepository.findByShortUrl(form.getShortcut()).ifPresent(urlShortcut -> {
            throw new IllegalStateException("이미 존재하는 단축 URL입니다.");
        });

        UrlShortcut entity = UrlShortcut.createEntity(form);
        urlShortcutRepository.save(entity);
    }

    @Transactional
    public void updateShortcut(UpdateShortcutForm form){
        UrlShortcut entity = urlShortcutRepository.findByOriginalUrl(form.getUrl()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 URL입니다."));

        urlShortcutRepository.findByShortUrl(form.getNewShortcut()).ifPresent(urlShortcut -> {
            throw new IllegalStateException("이미 존재하는 단축 URL입니다.");
        });

        entity.setShortUrl(form.getNewShortcut());
    }

    @Transactional void updateCount(Long id){
        UrlShortcut entity = urlShortcutRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 URL입니다."));
        entity.addCount();
    }

    @Transactional
    public void deleteUrlShortcut(String url){
        UrlShortcut entity = urlShortcutRepository.findByOriginalUrl(url).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 URL입니다."));
        urlShortcutRepository.delete(entity);
    }

}
