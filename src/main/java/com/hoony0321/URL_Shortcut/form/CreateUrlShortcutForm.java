package com.hoony0321.URL_Shortcut.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class CreateUrlShortcutForm {
    private final String url;
    private final String shortcut;
    private final Long userId;
}
