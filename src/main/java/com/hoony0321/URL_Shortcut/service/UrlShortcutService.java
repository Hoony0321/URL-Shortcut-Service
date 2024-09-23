package com.hoony0321.URL_Shortcut.service;

import com.hoony0321.URL_Shortcut.domain.UrlShortcut;
import com.hoony0321.URL_Shortcut.domain.User;
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
    private final UserService userService;

    public List<UrlShortcut> findAllUrlShortcut(User user){
        return urlShortcutRepository.findAllByUser(user);
    }

    @Transactional
    public void createUrlShortcut(CreateUrlShortcutForm form){
        User user = userService.findUser(form.getUserId());// 사용자가 존재하는지 확인 (없으면 예외 발생)

        urlShortcutRepository.findByUserAndOriginalUrl(user, form.getUrl()).ifPresent(urlShortcut -> {
            throw new IllegalStateException("이미 존재하는 URL입니다.");
        });

        urlShortcutRepository.findByUserAndShortUrl(user, form.getShortcut()).ifPresent(urlShortcut -> {
            throw new IllegalStateException("이미 존재하는 단축 URL입니다.");
        });

        UrlShortcut entity = UrlShortcut.createEntity(form, user);
        urlShortcutRepository.save(entity);
    }

    @Transactional
    public void updateShortcut(UpdateShortcutForm form){
        User user = userService.findUser(form.getUserId());// 사용자가 존재하는지 확인 (없으면 예외 발생)

        UrlShortcut urlShortcut = urlShortcutRepository.findByUserAndOriginalUrl(user, form.getUrl()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 URL입니다."));

        urlShortcutRepository.findByUserAndShortUrl(user, form.getNewShortcut()).ifPresent(shortcut -> {
            throw new IllegalStateException("이미 존재하는 단축 URL입니다.");
        });

        urlShortcut.setShortUrl(form.getNewShortcut());
    }

    @Transactional
    public void deleteUrlShortcut(Long id){
        UrlShortcut entity = urlShortcutRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 URL입니다."));
        urlShortcutRepository.delete(entity);
    }
}
