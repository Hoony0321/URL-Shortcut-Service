package com.hoony0321.URL_Shortcut.controller;

import com.hoony0321.URL_Shortcut.domain.UrlShortcut;
import com.hoony0321.URL_Shortcut.domain.User;
import com.hoony0321.URL_Shortcut.form.CreateUserForm;
import com.hoony0321.URL_Shortcut.service.UrlShortcutService;
import com.hoony0321.URL_Shortcut.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@AllArgsConstructor
public class HomeController {

    private final UrlShortcutService urlShortcutService;
    private final UserService userService;

    @GetMapping("/")
    public String home(Model model) {
        User user = (User) model.getAttribute("user");

        List<UrlShortcut> urlList = urlShortcutService.findAllUrlShortcut(user);
        model.addAttribute("urlList", urlList);
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String signUp(CreateUserForm form) {
        userService.createUser(form);
        return "redirect:/login";
    }

}
