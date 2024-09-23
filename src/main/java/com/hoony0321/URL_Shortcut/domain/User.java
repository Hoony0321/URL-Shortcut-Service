package com.hoony0321.URL_Shortcut.domain;

import com.hoony0321.URL_Shortcut.form.CreateUserForm;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    @OneToMany(mappedBy = "user")
    private List<UrlShortcut> urlShortcuts = new ArrayList<>();

    public static User createEntity(CreateUserForm form) {
        User entity = new User();
        entity.name = form.getName();
        entity.email = form.getEmail();
        entity.password = form.getPassword();
        return entity;
    }

    public void changePassword(String newPassword) {
        this.password = newPassword;
    }
}
