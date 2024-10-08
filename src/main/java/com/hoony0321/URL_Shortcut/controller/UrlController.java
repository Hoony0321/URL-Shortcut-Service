package com.hoony0321.URL_Shortcut.controller;

import com.hoony0321.URL_Shortcut.domain.User;
import com.hoony0321.URL_Shortcut.form.CreateUrlShortcutForm;
import com.hoony0321.URL_Shortcut.service.UrlShortcutService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class UrlController {

    private final UrlShortcutService urlShortcutService;

    @PostMapping("/url")
    public String createUrl(@RequestParam("originalUrl") String originalUrl,
                            @RequestParam("shortUrl") String shortUrl,
                            Model model) {

        User user = (User) model.getAttribute("user");

        CreateUrlShortcutForm form = CreateUrlShortcutForm.builder()
                .url(originalUrl)
                .shortcut(shortUrl)
                .userId(user.getId())
                .build();
        urlShortcutService.createUrlShortcut(form);

        return "redirect:/";
    }

    @PostMapping("/url/update")
    public String updateUrl(@RequestParam("id") Long urlId,
                            @RequestParam("originalUrl") String originalUrl,
                            @RequestParam("shortUrl") String shortUrl,
                            Model model) {

        User user = (User) model.getAttribute("user");

        urlShortcutService.deleteUrlShortcut(urlId);
        CreateUrlShortcutForm form = CreateUrlShortcutForm.builder()
                .url(originalUrl)
                .shortcut(shortUrl)
                .userId(user.getId())
                .build();

        urlShortcutService.createUrlShortcut(form);
        return "redirect:/";
    }

    @PostMapping("/url/delete")
    public String deleteUrl(@RequestParam("id") Long urlId) {
        urlShortcutService.deleteUrlShortcut(urlId);
        return "redirect:/";
    }


}
