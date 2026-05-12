package com.example.betasolutions.enums;

public enum Role {
    OWNER,
    SENIOR,
    JUNIOR;

    public static Role fromDb(String value) {
        if (value == null) return null;

        return switch (value.toLowerCase()) {
            case "owner" -> OWNER;
            case "senior" -> SENIOR;
            case "junior" -> JUNIOR;
            default -> throw new IllegalArgumentException("Unknown role: " + value);
        };
    }
}
