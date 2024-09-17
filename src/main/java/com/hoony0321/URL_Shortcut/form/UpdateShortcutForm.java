package com.hoony0321.URL_Shortcut.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class UpdateShortcutForm {
    private final String url;
    private final String newShortcut; // Shortcut 변경 시 새로운 Shortcut
}
