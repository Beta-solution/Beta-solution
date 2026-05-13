package com.example.betasolutions.controller;

import com.example.betasolutions.enums.Role;
import com.example.betasolutions.model.Profile;
import com.example.betasolutions.model.Task;
import com.example.betasolutions.service.CalculationService;
import com.example.betasolutions.service.ProfileService;
import com.example.betasolutions.service.SkillService;
import com.example.betasolutions.service.TaskService;
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

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TaskService taskService;

    @MockitoBean
    private SkillService skillService;

    @MockitoBean
    private ProfileService profileService;

    @MockitoBean
    private CalculationService calculationService;

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

    // -------------------- GET /projects/{projectId}/tasks --------------------

    @Test
    void shouldGetTasksByProject() throws Exception {
        Task task = new Task();
        task.setId(1);
        task.setName("Task 1");

        when(taskService.getTasksWithDuration(1)).thenReturn(List.of(task));

        mockMvc.perform(get("/projects/1/tasks").session(authorizedSession()))
                .andExpect(status().isOk())
                .andExpect(view().name("tasks/index"))
                .andExpect(model().attributeExists("tasks"))
                .andExpect(model().attribute("projectId", 1));
    }

    @Test
    void shouldRedirectJuniorFromGetTasksByProject() throws Exception {
        mockMvc.perform(get("/projects/1/tasks").session(juniorSession()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/unauthorized"));
    }

    @Test
    void shouldRedirectUnauthenticatedFromGetTasksByProject() throws Exception {
        mockMvc.perform(get("/projects/1/tasks"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/unauthorized"));
    }

    // -------------------- GET /projects/{projectId}/tasks/create --------------------

    @Test
    void shouldShowCreateForm() throws Exception {
        mockMvc.perform(get("/projects/1/tasks/create").session(authorizedSession()))
                .andExpect(status().isOk())
                .andExpect(view().name("tasks/create"))
                .andExpect(model().attributeExists("task"))
                .andExpect(model().attribute("projectId", 1));
    }

    // -------------------- POST /projects/{projectId}/tasks/create --------------------

    @Test
    void shouldCreateTask() throws Exception {
        mockMvc.perform(post("/projects/1/tasks/create")
                        .session(authorizedSession())
                        .param("name", "New Task")
                        .param("description", "Description")
                        .param("status", "TODO")
                        .param("startDate", "2026-04-01")
                        .param("endDate", "2026-04-15"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/projects/1/tasks"));

        verify(taskService).createTask(any(Task.class), anyInt());
    }

    // -------------------- GET /projects/{projectId}/tasks/{id}/edit --------------------

    @Test
    void shouldShowEditForm() throws Exception {
        Task task = new Task();
        task.setId(1);

        when(taskService.getTaskById(1)).thenReturn(task);

        mockMvc.perform(get("/projects/1/tasks/1/edit").session(authorizedSession()))
                .andExpect(status().isOk())
                .andExpect(view().name("tasks/edit"))
                .andExpect(model().attribute("task", task))
                .andExpect(model().attribute("projectId", 1));
    }

    // -------------------- POST /projects/{projectId}/tasks/{id}/edit --------------------

    @Test
    void shouldUpdateTask() throws Exception {
        mockMvc.perform(post("/projects/1/tasks/1/edit")
                        .session(authorizedSession())
                        .param("name", "Updated Task")
                        .param("description", "Updated description"))
                .andExpect(status().is3xxRedirection());

        verify(taskService).updateTask(anyInt(), any(Task.class));
    }

    // -------------------- POST /projects/{projectId}/tasks/{id}/delete --------------------

    @Test
    void shouldDeleteTask() throws Exception {
        mockMvc.perform(post("/projects/1/tasks/1/delete").session(authorizedSession()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/projects/1/tasks"));

        verify(taskService).deleteTask(1);
    }

    // -------------------- GET /projects/{projectId}/tasks/{taskId} --------------------

    @Test
    void shouldGetTaskDetail() throws Exception {
        Task task = new Task();
        task.setId(1);
        task.setName("Task 1");

        when(taskService.getTaskById(1)).thenReturn(task);
        when(taskService.getProfilesByTaskId(1)).thenReturn(List.of());

        mockMvc.perform(get("/projects/1/tasks/1").session(authorizedSession()))
                .andExpect(status().isOk())
                .andExpect(view().name("tasks/detail"))
                .andExpect(model().attribute("task", task))
                .andExpect(model().attribute("projectId", 1));
    }
}
