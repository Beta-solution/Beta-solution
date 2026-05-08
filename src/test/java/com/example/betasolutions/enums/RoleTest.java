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
    void shouldReturnProjectManager() {
        Role role = Role.fromDb("project_manager");

        assertEquals(Role.PROJECT_MANAGER, role);
    }

    @Test
    void shouldReturnDeveloper() {
        Role role = Role.fromDb("developer");

        assertEquals(Role.DEVELOPER, role);
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