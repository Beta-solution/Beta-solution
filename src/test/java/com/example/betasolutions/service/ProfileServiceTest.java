package com.example.betasolutions.service;

import com.example.betasolutions.enums.Role;
import com.example.betasolutions.model.Profile;
import com.example.betasolutions.repository.ProfileRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProfileServiceTest {

    @Mock
    private ProfileRepository profileRepository;

    @InjectMocks
    private ProfileService profileService;

    // -------------------- getAllProfiles --------------------

    @Test
    void getAllProfiles_ReturnsList() {
        Profile profile = new Profile();
        profile.setId(1);
        profile.setName("Alice");

        when(profileRepository.getAllProfiles()).thenReturn(List.of(profile));

        List<Profile> result = profileService.getAllProfiles();

        assertEquals(1, result.size());
        assertEquals("Alice", result.getFirst().getName());
        verify(profileRepository).getAllProfiles();
    }

    // -------------------- getProfileById --------------------

    @Test
    void getProfileById_ReturnsProfile() {
        Profile profile = new Profile();
        profile.setId(1);
        profile.setName("Alice");

        when(profileRepository.getProfileById(1)).thenReturn(profile);

        Profile result = profileService.getProfileById(1);

        assertEquals("Alice", result.getName());
        verify(profileRepository).getProfileById(1);
    }

    // -------------------- getProfileByUsername --------------------

    @Test
    void getProfileByUsername_ValidCredentials_ReturnsProfile() {
        Profile profile = new Profile();
        profile.setName("Alice");

        when(profileRepository.getProfileByUsername("alice", "password")).thenReturn(profile);

        Profile result = profileService.getProfileByUsername("alice", "password");

        assertEquals("Alice", result.getName());
        verify(profileRepository).getProfileByUsername("alice", "password");
    }

    // -------------------- createProfile --------------------

    @Test
    void createProfile_ValidProfile_ReturnsTrue() {
        Profile profile = new Profile();
        profile.setName("Alice");
        profile.setUsername("alice");
        profile.setPassword("password");
        profile.setEmail("alice@test.dk");
        profile.setRole(Role.OWNER);

        when(profileRepository.createProfile(profile)).thenReturn(true);

        boolean result = profileService.createProfile(profile);

        assertTrue(result);
        verify(profileRepository).createProfile(profile);
    }

    // -------------------- updateProfile --------------------

    @Test
    void updateProfile_ValidProfile_ReturnsTrue() {
        Profile profile = new Profile();
        profile.setName("Alice Updated");

        when(profileRepository.updateProfile(profile, 1)).thenReturn(true);

        boolean result = profileService.updateProfile(profile, 1);

        assertTrue(result);
        verify(profileRepository).updateProfile(profile, 1);
    }

    // -------------------- deleteProfile --------------------

    @Test
    void deleteProfile_ProfileExists_ReturnsTrue() {
        when(profileRepository.deleteProfile(1)).thenReturn(true);

        boolean result = profileService.deleteProfile(1);

        assertTrue(result);
        verify(profileRepository).deleteProfile(1);
    }

    // -------------------- getProfilesByProjectId --------------------

    @Test
    void getProfilesByProjectId_ReturnsList() {
        Profile profile = new Profile();
        profile.setId(1);

        when(profileRepository.getProfilesByProjectId(1)).thenReturn(List.of(profile));

        List<Profile> result = profileService.getProfilesByProjectId(1);

        assertEquals(1, result.size());
        verify(profileRepository).getProfilesByProjectId(1);
    }

    // -------------------- getAvailableProfilesForProject --------------------

    @Test
    void getAvailableProfilesForProject_ReturnsList() {
        Profile profile = new Profile();
        profile.setId(2);

        when(profileRepository.getProfilesNotInProject(1)).thenReturn(List.of(profile));

        List<Profile> result = profileService.getAvailableProfilesForProject(1);

        assertEquals(1, result.size());
        verify(profileRepository).getProfilesNotInProject(1);
    }

    // -------------------- getProfilesBySubTaskId --------------------

    @Test
    void getProfilesBySubTaskId_ReturnsList() {
        Profile profile = new Profile();
        profile.setId(1);

        when(profileRepository.getProfilesBySubTaskId(1)).thenReturn(List.of(profile));

        List<Profile> result = profileService.getProfilesBySubTaskId(1);

        assertEquals(1, result.size());
        verify(profileRepository).getProfilesBySubTaskId(1);
    }

    // -------------------- getAvailableProfilesForSubTask --------------------

    @Test
    void getAvailableProfilesForSubTask_ReturnsList() {
        Profile profile = new Profile();
        profile.setId(2);

        when(profileRepository.getProfilesNotInSubTask(1)).thenReturn(List.of(profile));

        List<Profile> result = profileService.getAvailableProfilesForSubTask(1);

        assertEquals(1, result.size());
        verify(profileRepository).getProfilesNotInSubTask(1);
    }
}
