package com.example.betasolutions.repository;

import com.example.betasolutions.enums.Role;
import com.example.betasolutions.exception.InvalidProfileException;
import com.example.betasolutions.exception.ProfileNotFoundException;
import com.example.betasolutions.model.Profile;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = {"/schema.sql", "/data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class ProfileRepositoryTest {

    @Autowired ProfileRepository profileRepository;


    @Test
    void getAllProfiles_ReturnsList() {
        List<Profile> result = profileRepository.getAllProfiles();
        assertEquals(2, result.size());
    }

    // -------------------- getProfileById --------------------

    @Test
    void getProfileById_ProfileExists_ReturnsProfile() {
        Profile result = profileRepository.getProfileById(1);
        assertEquals("Alice", result.getName());
        assertEquals("OWNER", result.getRole().name());
    }

    @Test
    void getProfileById_ProfileNotFound_ThrowsException() {
        assertThrows(ProfileNotFoundException.class, () -> profileRepository.getProfileById(999));
    }

    // -------------------- getProfileByUsername --------------------

    @Test
    void getProfileByUsername_ValidCredentials_ReturnsProfile() {
        Profile result = profileRepository.getProfileByUsername("alice", "password");
        assertEquals("Alice", result.getName());
    }


    @Test
    void getProfileByUsername_InvalidCredentials_ThrowsException() {
        assertThrows(ProfileNotFoundException.class, () ->
                profileRepository.getProfileByUsername("alice", "forkert"));
    }

    // -------------------- createProfile --------------------

    @Test
    void createProfile_ValidProfile_ReturnsTrue() {
        Profile newProfile = new Profile();
        newProfile.setName("Nyt Medlem");
        newProfile.setUsername("nyt123");
        newProfile.setPassword("password");
        newProfile.setRole(Role.OWNER);
        newProfile.setEmail("nyt@email.com");

        boolean result = profileRepository.createProfile(newProfile);

        assertTrue(result);
        assertEquals(3, profileRepository.getAllProfiles().size());
    }

    @Test
    void createProfile_EmptyName_ThrowsException() {
        Profile invalid = new Profile();
        invalid.setName("");
        invalid.setUsername("nyt123");
        invalid.setPassword("password");
        invalid.setEmail("nyt@email.com");

        assertThrows(InvalidProfileException.class, () -> profileRepository.createProfile(invalid));
    }

    @Test
    void createProfile_NullUsername_ThrowsException() {
        Profile invalid = new Profile();
        invalid.setName("Nyt Medlem");
        invalid.setUsername(null);
        invalid.setPassword("password");
        invalid.setEmail("nyt@email.com");

        assertThrows(InvalidProfileException.class, () -> profileRepository.createProfile(invalid));
    }

    @Test
    void createProfile_EmptyPassword_ThrowsException() {
        Profile invalid = new Profile();
        invalid.setName("Nyt Medlem");
        invalid.setUsername("nyt123");
        invalid.setPassword("");
        invalid.setEmail("nyt@email.com");

        assertThrows(InvalidProfileException.class, () -> profileRepository.createProfile(invalid));
    }

    @Test
    void createProfile_NullEmail_ThrowsException() {
        Profile invalid = new Profile();
        invalid.setName("Nyt Medlem");
        invalid.setUsername("nyt123");
        invalid.setPassword("password");
        invalid.setEmail(null);

        assertThrows(InvalidProfileException.class, () -> profileRepository.createProfile(invalid));
    }

    // -------------------- updateProfile --------------------

    @Test
    void updateProfile_ValidProfile_ReturnsTrue() {
        Profile updated = new Profile();
        updated.setName("Alice Opdateret");
        updated.setUsername("alice");
        updated.setPassword("password");
        updated.setRole(Role.OWNER);
        updated.setEmail("alice@test.dk");

        boolean result = profileRepository.updateProfile(updated, 1);
        assertTrue(result);
        assertEquals("Alice Opdateret", profileRepository.getProfileById(1).getName());
    }

    @Test
    void updateProfile_ProfileNotFound_ThrowsException() {
        Profile updated = new Profile();
        updated.setName("Magnus Opdateret");
        updated.setUsername("magnus123");
        updated.setPassword("password");
        updated.setRole(Role.DEVELOPER);
        updated.setEmail("magnus@email.com");

        assertThrows(ProfileNotFoundException.class, () -> profileRepository.updateProfile(updated, 999));
    }

    @Test
    void updateProfile_EmptyName_ThrowsException() {
        Profile invalid = new Profile();
        invalid.setName("");
        invalid.setUsername("magnus123");
        invalid.setPassword("password");
        invalid.setEmail("magnus@email.com");

        assertThrows(InvalidProfileException.class, () -> profileRepository.updateProfile(invalid, 1));
    }

    // -------------------- deleteProfile --------------------

    @Test
    void deleteProfile_ProfileExists_ReturnsTrue() {
        boolean result = profileRepository.deleteProfile(1);

        assertTrue(result);
        assertThrows(ProfileNotFoundException.class, () -> profileRepository.getProfileById(1));
    }

    @Test
    void deleteProfile_ProfileNotFound_ThrowsException() {
        assertThrows(ProfileNotFoundException.class, () -> profileRepository.deleteProfile(999));
    }
}