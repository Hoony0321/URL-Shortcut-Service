package com.hoony0321.URL_Shortcut.config;

import com.hoony0321.URL_Shortcut.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomUserDetailsService customUserDetailsService;
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/register", "/css/**", "/js/**", "/error").permitAll()  // 로그인, 회원가입, 에러 페이지 허용
                        .anyRequest().authenticated()  // 나머지 요청은 인증 필요
                )
                .formLogin(form -> form
                        .loginPage("/login")  // 로그인 페이지 경로
                        .usernameParameter("email")  // 사용자 이름 대신 이메일 필드 사용
                        .passwordParameter("password")  // 비밀번호 필드 설정 (기본값 유지 가능)
                        .defaultSuccessUrl("/", true)  // 로그인 성공 후 이동할 경로
                        .permitAll()  // 로그인 페이지 접근 허용
                )
                .logout(logout -> logout.permitAll())  // 로그아웃 허용
                .csrf(csrf -> csrf.disable());  // CSRF 비활성화 (테스트용)

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder())
                .and()
                .build();
    }
}


