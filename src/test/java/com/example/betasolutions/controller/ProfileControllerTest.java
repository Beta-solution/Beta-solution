package com.example.betasolutions.controller;

import com.example.betasolutions.enums.Role;
import com.example.betasolutions.model.Profile;
import com.example.betasolutions.service.ProfileService;
import com.example.betasolutions.service.SkillService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProfileController.class)
class ProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProfileService profileService;

    @MockitoBean
    private SkillService skillService;

    private MockHttpSession ownerSession() {
        Profile profile = new Profile();
        profile.setId(1);
        profile.setRole(Role.OWNER);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("profile", profile);
        session.setAttribute("currentUser", profile);
        return session;
    }

    private MockHttpSession seniorSession() {
        Profile profile = new Profile();
        profile.setId(2);
        profile.setRole(Role.SENIOR);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("profile", profile);
        session.setAttribute("currentUser", profile);
        return session;
    }

    private MockHttpSession juniorSession() {
        Profile profile = new Profile();
        profile.setId(3);
        profile.setRole(Role.JUNIOR);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("profile", profile);
        session.setAttribute("currentUser", profile);
        return session;
    }

    // -------------------- GET /profiles --------------------

    @Test
    void shouldGetAllProfiles() throws Exception {
        Profile profile = new Profile();
        profile.setId(1);
        profile.setName("Alice");

        when(profileService.getAllProfiles()).thenReturn(List.of(profile));

        mockMvc.perform(get("/profiles").session(ownerSession()))
                .andExpect(status().isOk())
                .andExpect(view().name("profiles/index"))
                .andExpect(model().attributeExists("profiles"));
    }

    @Test
    void shouldRedirectJuniorFromGetAllProfiles() throws Exception {
        mockMvc.perform(get("/profiles").session(juniorSession()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/unauthorized"));
    }

    @Test
    void shouldRedirectUnauthenticatedFromGetAllProfiles() throws Exception {
        mockMvc.perform(get("/profiles"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/unauthorized"));
    }

    // -------------------- GET /profiles/{id} --------------------

    @Test
    void shouldGetProfileById() throws Exception {
        Profile profile = new Profile();
        profile.setId(1);
        profile.setName("Alice");

        when(profileService.getProfileById(1)).thenReturn(profile);

        mockMvc.perform(get("/profiles/1").session(ownerSession()))
                .andExpect(status().isOk())
                .andExpect(view().name("profiles/profile"))
                .andExpect(model().attribute("profile", profile));
    }

    // -------------------- GET /profiles/create --------------------

    @Test
    void shouldShowCreateForm() throws Exception {
        mockMvc.perform(get("/profiles/create").session(ownerSession()))
                .andExpect(status().isOk())
                .andExpect(view().name("profiles/form"))
                .andExpect(model().attributeExists("profileForm"))
                .andExpect(model().attributeExists("isOwner"));
    }

    @Test
    void shouldRedirectJuniorFromCreateForm() throws Exception {
        mockMvc.perform(get("/profiles/create").session(juniorSession()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/unauthorized"));
    }

    // -------------------- POST /profiles/create --------------------

    @Test
    void shouldCreateProfile() throws Exception {
        mockMvc.perform(post("/profiles/create")
                        .session(ownerSession())
                        .param("name", "Charlie")
                        .param("username", "charlie")
                        .param("password", "password")
                        .param("email", "charlie@test.dk")
                        .param("role", "JUNIOR"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/profiles"));

        verify(profileService).createProfile(any(Profile.class));
    }

    @Test
    void shouldRejectSeniorCreationByNonOwner() throws Exception {
        mockMvc.perform(post("/profiles/create")
                        .session(seniorSession())
                        .param("name", "Dave")
                        .param("username", "dave")
                        .param("password", "password")
                        .param("email", "dave@test.dk")
                        .param("role", "SENIOR"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/unauthorized"));
    }

    // -------------------- GET /profiles/{id}/edit --------------------

    @Test
    void shouldShowEditForm() throws Exception {
        Profile profile = new Profile();
        profile.setId(1);

        when(profileService.getProfileById(1)).thenReturn(profile);

        mockMvc.perform(get("/profiles/1/edit").session(ownerSession()))
                .andExpect(status().isOk())
                .andExpect(view().name("profiles/edit"))
                .andExpect(model().attribute("profileEdit", profile));
    }

    // -------------------- POST /profiles/{id}/edit --------------------

    @Test
    void shouldUpdateProfile() throws Exception {
        mockMvc.perform(post("/profiles/1/edit")
                        .session(ownerSession())
                        .param("name", "Alice Updated")
                        .param("username", "alice")
                        .param("password", "password")
                        .param("email", "alice@test.dk")
                        .param("role", "OWNER"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/profiles"));

        verify(profileService).updateProfile(any(Profile.class), anyInt());
    }

    // -------------------- POST /profiles/{id}/delete --------------------

    @Test
    void shouldDeleteProfile() throws Exception {
        Profile profileToDelete = new Profile();
        profileToDelete.setId(2);
        profileToDelete.setRole(Role.JUNIOR);

        when(profileService.getProfileById(2)).thenReturn(profileToDelete);

        mockMvc.perform(post("/profiles/2/delete").session(ownerSession()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/profiles"));

        verify(profileService).deleteProfile(2);
    }

    @Test
    void shouldRejectSeniorDeletionByNonOwner() throws Exception {
        Profile profileToDelete = new Profile();
        profileToDelete.setId(2);
        profileToDelete.setRole(Role.SENIOR);

        when(profileService.getProfileById(2)).thenReturn(profileToDelete);

        mockMvc.perform(post("/profiles/2/delete").session(seniorSession()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/unauthorized"));
    }
}
