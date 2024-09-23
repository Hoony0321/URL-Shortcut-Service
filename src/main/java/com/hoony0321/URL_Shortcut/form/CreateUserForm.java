package com.hoony0321.URL_Shortcut.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class CreateUserForm {
    private final String name;
    private final String email;
    private final String password;
}
