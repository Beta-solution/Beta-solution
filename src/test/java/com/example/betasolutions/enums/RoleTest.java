package com.example.betasolutions.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoleTest {

    @Test
    void shouldReturnOwner() {
        Role role = Role.fromDb("owner");
        assertEquals(Role.OWNER, role);
    }

    @Test
    void shouldReturnSenior() {
        Role role = Role.fromDb("senior");
        assertEquals(Role.SENIOR, role);
    }

    @Test
    void shouldReturnJunior() {
        Role role = Role.fromDb("junior");
        assertEquals(Role.JUNIOR, role);
    }

    @Test
    void shouldReturnNullWhenValueIsNull() {
        assertNull(Role.fromDb(null));
    }

    @Test
    void shouldThrowExceptionForInvalidRole() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> Role.fromDb("invalid_role")
        );

        assertEquals("Unknown role: invalid_role", exception.getMessage());
    }
}