package com.example.betasolutions.controller;

import com.example.betasolutions.enums.Role;
import com.example.betasolutions.model.Profile;
import com.example.betasolutions.model.SubTask;
import com.example.betasolutions.service.ProfileService;
import com.example.betasolutions.service.ProjectService;
import com.example.betasolutions.service.SkillService;
import com.example.betasolutions.service.SubTaskService;
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

@WebMvcTest(SubTaskController.class)
class SubTaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SubTaskService subTaskService;

    @MockitoBean
    private SkillService skillService;

    @MockitoBean
    private ProfileService profileService;

    @MockitoBean
    private ProjectService projectService;

    private MockHttpSession authorizedSession() {
        Profile profile = new Profile();
        profile.setId(1);
        profile.setRole(Role.OWNER);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("profile", profile);
        session.setAttribute("currentUser", profile);
        return session;
    }

    private MockHttpSession juniorSession() {
        Profile profile = new Profile();
        profile.setId(2);
        profile.setRole(Role.JUNIOR);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("profile", profile);
        session.setAttribute("currentUser", profile);
        return session;
    }

    // -------------------- GET /tasks/{taskId}/subtasks --------------------

    @Test
    void shouldGetSubTasksByTask() throws Exception {
        SubTask subTask = new SubTask();
        subTask.setId(1);
        subTask.setName("SubTask 1");

        when(subTaskService.getSubTaskByTaskId(1)).thenReturn(List.of(subTask));

        mockMvc.perform(get("/tasks/1/subtasks").session(authorizedSession()))
                .andExpect(status().isOk())
                .andExpect(view().name("subtasks/index"))
                .andExpect(model().attributeExists("subTasks"))
                .andExpect(model().attribute("taskId", 1));
    }

    @Test
    void shouldRedirectJuniorFromGetSubTasksByTask() throws Exception {
        mockMvc.perform(get("/tasks/1/subtasks").session(juniorSession()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/unauthorized"));
    }

    @Test
    void shouldRedirectUnauthenticatedFromGetSubTasksByTask() throws Exception {
        mockMvc.perform(get("/tasks/1/subtasks"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/unauthorized"));
    }

    // -------------------- GET /tasks/{taskId}/subtasks/create --------------------

    @Test
    void shouldShowCreateForm() throws Exception {
        mockMvc.perform(get("/tasks/1/subtasks/create").session(authorizedSession()))
                .andExpect(status().isOk())
                .andExpect(view().name("subtasks/create"))
                .andExpect(model().attributeExists("subTask"))
                .andExpect(model().attribute("taskId", 1));
    }

    // -------------------- POST /tasks/{taskId}/subtasks/create --------------------

    @Test
    void shouldCreateSubTask() throws Exception {
        mockMvc.perform(post("/tasks/1/subtasks/create")
                        .session(authorizedSession())
                        .param("name", "New SubTask")
                        .param("description", "Description")
                        .param("duration", "5.0")
                        .param("status", "TODO")
                        .param("startDate", "2026-04-01")
                        .param("endDate", "2026-04-05"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/tasks/1/subtasks"));

        verify(subTaskService).createSubTask(any(SubTask.class), anyInt());
    }

    // -------------------- GET /tasks/{taskId}/subtasks/{id}/edit --------------------

    @Test
    void shouldShowEditForm() throws Exception {
        SubTask subTask = new SubTask();
        subTask.setId(1);

        when(subTaskService.getSubTaskById(1)).thenReturn(subTask);

        mockMvc.perform(get("/tasks/1/subtasks/1/edit").session(authorizedSession()))
                .andExpect(status().isOk())
                .andExpect(view().name("subtasks/edit"))
                .andExpect(model().attribute("subTask", subTask))
                .andExpect(model().attribute("taskId", 1));
    }

    // -------------------- POST /tasks/{taskId}/subtasks/{id}/edit --------------------

    @Test
    void shouldUpdateSubTask() throws Exception {
        mockMvc.perform(post("/tasks/1/subtasks/1/edit")
                        .session(authorizedSession())
                        .param("name", "Updated SubTask")
                        .param("description", "Updated description"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/tasks/1/subtasks"));

        verify(subTaskService).updateSubTask(anyInt(), any(SubTask.class));
    }

    // -------------------- POST /tasks/{taskId}/subtasks/{id}/delete --------------------

    @Test
    void shouldDeleteSubTask() throws Exception {
        mockMvc.perform(post("/tasks/1/subtasks/1/delete").session(authorizedSession()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/tasks/1/subtasks"));

        verify(subTaskService).deleteSubTask(1);
    }

    // -------------------- GET /subtasks/{subTaskId}/members --------------------

    @Test
    void shouldShowAssignProfiles() throws Exception {
        SubTask subTask = new SubTask();
        subTask.setId(1);

        when(subTaskService.getSubTaskById(1)).thenReturn(subTask);
        when(profileService.getAvailableProfilesForSubTask(1)).thenReturn(List.of());
        when(profileService.getProfilesBySubTaskId(1)).thenReturn(List.of());

        mockMvc.perform(get("/subtasks/1/members").session(authorizedSession()))
                .andExpect(status().isOk())
                .andExpect(view().name("subtasks/members"))
                .andExpect(model().attributeExists("subTask"))
                .andExpect(model().attributeExists("profiles"))
                .andExpect(model().attributeExists("assignedProfiles"));
    }

    // -------------------- POST /subtasks/{subTaskId}/members/{profileId} --------------------

    @Test
    void shouldAddProfileToSubTask() throws Exception {
        mockMvc.perform(post("/subtasks/1/members/2").session(authorizedSession()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/subtasks/1/members"));

        verify(subTaskService).addProfileToSubTask(2, 1);
    }

    // -------------------- POST /subtasks/{subTaskId}/members/{profileId}/remove --------------------

    @Test
    void shouldRemoveProfileFromSubTask() throws Exception {
        mockMvc.perform(post("/subtasks/1/members/1/remove").session(authorizedSession()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/subtasks/1/members"));

        verify(subTaskService).removeProfileFromSubTask(1, 1);
    }
}
