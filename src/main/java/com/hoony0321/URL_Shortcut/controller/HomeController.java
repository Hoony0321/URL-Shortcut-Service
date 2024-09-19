package com.hoony0321.URL_Shortcut.controller;

import com.hoony0321.URL_Shortcut.domain.UrlShortcut;
import com.hoony0321.URL_Shortcut.service.UrlShortcutService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@AllArgsConstructor
public class HomeController {

    private final UrlShortcutService urlShortcutService;

    @GetMapping("/")
    public String home(Model model) {
        List<UrlShortcut> urlList = urlShortcutService.findAllUrlShortcut();
        model.addAttribute("urlList", urlList);
        return "home";
    }
}
