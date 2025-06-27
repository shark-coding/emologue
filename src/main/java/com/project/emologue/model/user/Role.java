package com.project.emologue.model.user;

public enum Role {
    USER("ROLE_USER", "일반 사용자"),
    ADMIN("ROLE_ADMIN", "관리자 계정");

    private final String role;
    private final String description;

    public String getRole() {
        return role;
    }

    public String getDescription() {
        return description;
    }

    Role(String role, String description) {
        this.role = role;
        this.description = description;
    }
}
