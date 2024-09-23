package com.hoony0321.URL_Shortcut.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class UpdateUserPasswordForm {
    private final Long id;
    private final String email;
    private final String newPassword;
}
