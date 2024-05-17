package com.example.amazon_server.models;

public enum Role {
    GENERAL("GENERAL"),
    USER("USER"),
    SELLER("SELLER"),
    ADMIN("ADMIN");

    private final String roleName;

    Role(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }
}
