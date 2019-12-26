package org.jarvis.kk.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Role
 */
@Getter
@RequiredArgsConstructor
public enum Role {

    MEMBER("ROLE_MEMBER", "사용자"),
    ADMIN("ROLE_ADMIN", "관리자");

    private final String key;
    private final String title;
}