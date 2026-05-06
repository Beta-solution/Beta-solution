package com.example.betasolutions.enums;

public enum Role {
    ADMIN,
    PROJECT_MANAGER,
    DEVELOPER;

    public static Role fromDb(String value) {
        if (value == null) return null;

        return switch (value.toLowerCase()) {
            case "admin" -> ADMIN;
            case "project_manager" -> PROJECT_MANAGER;
            case "developer" -> DEVELOPER;
            default -> throw new IllegalArgumentException("Unknown role: " + value);
        };
    }
}
