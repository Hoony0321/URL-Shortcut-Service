package com.hoony0321.URL_Shortcut.controller;

import com.hoony0321.URL_Shortcut.domain.User;
import com.hoony0321.URL_Shortcut.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalControllerAdvice {
    private final UserService userService;

    @ModelAttribute
    public void getUserInfo(HttpServletRequest request, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = null;

        // 경로에 따라 특정 경로에서 제외 ("/login" 또는 "/register")
        if (!isLoginOrRegisterPage(request)) {
            if (authentication == null || !authentication.isAuthenticated()) {
                throw new IllegalStateException("로그인 또는 회원가입 페이지가 아닌 경우에만 호출되어야 합니다.");
            }
            String email = authentication.getName();
            user = userService.findUserByEmail(email);
        }

        model.addAttribute("user", user);
    }

    private boolean isLoginOrRegisterPage(HttpServletRequest request) {
        String path = request.getRequestURI();
        return path.equals("/login") || path.equals("/register");
    }
}
