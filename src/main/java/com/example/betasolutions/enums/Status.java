package com.example.betasolutions.enums;

public enum Status {
    TODO,
    IN_PROGRESS,
    DONE;

        public static Status fromDb(String value) {
            if (value == null) return Status.TODO; //

            return switch (value.toLowerCase()) {
                case "todo" -> TODO;
                case "in_progress" -> IN_PROGRESS;
                case "done" -> DONE;
                default -> throw new IllegalArgumentException("Unknown status: " + value);
            };
        }
}
