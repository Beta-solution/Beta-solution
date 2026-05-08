package com.example.betasolutions.enums;

public enum Role {
    OWNER,
    PROJECT_MANAGER,
    DEVELOPER;

    public static Role fromDb(String value) {
        if (value == null) return null;

        return switch (value.toLowerCase()) {
            case "owner" -> OWNER;
            case "project_manager" -> PROJECT_MANAGER;
            case "developer" -> DEVELOPER;
            default -> throw new IllegalArgumentException("Unknown role: " + value);
        };
    }
}
