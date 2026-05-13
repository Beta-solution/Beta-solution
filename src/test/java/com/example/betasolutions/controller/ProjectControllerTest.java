package com.example.betasolutions.controller;

import com.example.betasolutions.enums.Role;
import com.example.betasolutions.model.Profile;
import com.example.betasolutions.model.Project;
import com.example.betasolutions.service.ProfileService;
import com.example.betasolutions.service.ProjectService;
import com.example.betasolutions.service.SkillService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProjectController.class)
class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProjectService projectService;

    @MockitoBean
    private SkillService skillService;

    @MockitoBean
    private ProfileService profileService;

    private MockHttpSession authorizedSession() {
        Profile profile = new Profile();
        profile.setId(1);
        profile.setRole(Role.OWNER);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("profile", profile);
        session.setAttribute("currentUser", profile);

        return session;
    }

    @Test
    void shouldGetAllProjects() throws Exception {

        Project project = new Project();
        project.setId(1);
        project.setName("Project 1");

        when(projectService.getAllProjects())
                .thenReturn(List.of(project));

        mockMvc.perform(get("/projects")
                        .session(authorizedSession()))
                .andExpect(status().isOk())
                .andExpect(view().name("projects/index"))
                .andExpect(model().attributeExists("projects"))
                .andExpect(model().attributeExists("currentUser"));
    }

    @Test
    void shouldRedirectUnauthorizedUserFromGetAllProjects() throws Exception {

        mockMvc.perform(get("/projects"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/unauthorized"));
    }

    @Test
    void shouldGetProjectById() throws Exception {

        Project project = new Project();
        project.setId(1);
        project.setName("Project 1");

        when(projectService.getProjectById(1))
                .thenReturn(project);

        when(projectService.getProjectDuration(1))
                .thenReturn(BigDecimal.valueOf(120));

        when(projectService.getEstimatedPrice(1))
                .thenReturn(BigDecimal.valueOf(1000));

        mockMvc.perform(get("/projects/1")
                        .session(authorizedSession()))
                .andExpect(status().isOk())
                .andExpect(view().name("projects/detail"))
                .andExpect(model().attribute("project", project))
                .andExpect(model().attribute("totalDuration", BigDecimal.valueOf(120)))
                .andExpect(model().attribute("estimatedPrice", BigDecimal.valueOf(1000)));
    }

    @Test
    void shouldShowCreateForm() throws Exception {

        mockMvc.perform(get("/projects/create")
                        .session(authorizedSession()))
                .andExpect(status().isOk())
                .andExpect(view().name("projects/create"))
                .andExpect(model().attributeExists("project"));
    }

    @Test
    void shouldCreateProject() throws Exception {

        mockMvc.perform(post("/projects/create")
                        .session(authorizedSession())
                        .param("name", "Project 1")
                        .param("description", "Description")
                        .param("hourlyRate", "500.00")
                        .param("startDate", "2026-01-01")
                        .param("endDate", "2026-02-01")
                        .param("estimatedDeadline", "2026-02-05"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/projects"));

        verify(projectService).createProject(any(Project.class));
    }

    @Test
    void shouldShowEditForm() throws Exception {

        Project project = new Project();
        project.setId(1);

        when(projectService.getProjectById(1))
                .thenReturn(project);

        mockMvc.perform(get("/projects/1/edit")
                        .session(authorizedSession()))
                .andExpect(status().isOk())
                .andExpect(view().name("projects/edit"))
                .andExpect(model().attribute("project", project));
    }

    @Test
    void shouldUpdateProject() throws Exception {

        mockMvc.perform(post("/projects/1/edit")
                        .session(authorizedSession())
                        .param("name", "Updated Project")
                        .param("description", "Updated Description"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/projects"));

        verify(projectService).updateProject(any(Integer.class), any(Project.class));
    }

    @Test
    void shouldDeleteProject() throws Exception {

        mockMvc.perform(post("/projects/1/delete")
                        .session(authorizedSession()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/projects"));

        verify(projectService).deleteProject(1);
    }

    @Test
    void shouldRejectDeveloperAccess() throws Exception {

        Profile developer = new Profile();
        developer.setRole(Role.JUNIOR);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("profile", developer);

        mockMvc.perform(get("/projects")
                        .session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/unauthorized"));
    }
}