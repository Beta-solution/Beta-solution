package com.example.betasolutions.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StatusTest {

    @Test
    void shouldReturnTodo() {
        Status status = Status.fromDb("todo");

        assertEquals(Status.TODO, status);
    }

    @Test
    void shouldReturnInProgress() {
        Status status = Status.fromDb("in_progress");

        assertEquals(Status.IN_PROGRESS, status);
    }

    @Test
    void shouldReturnDone() {
        Status status = Status.fromDb("done");

        assertEquals(Status.DONE, status);
    }

    @Test
    void shouldReturnNullWhenValueIsNull() {
        assertNull(Status.fromDb(null));
    }

    @Test
    void shouldThrowExceptionForInvalidStatus() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> Status.fromDb("invalid_status")
        );

        assertEquals("Unknown status: invalid_status", exception.getMessage());
    }
}