package com.example.betasolutions.controller;

import com.example.betasolutions.enums.Role;
import com.example.betasolutions.model.Profile;
import com.example.betasolutions.model.Project;
import com.example.betasolutions.model.ProjectTemplate;
import com.example.betasolutions.service.ProjectService;
import com.example.betasolutions.service.ProjectTemplateService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProjectTemplateController.class)
class ProjectTemplateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProjectTemplateService projectTemplateService;

    @MockitoBean
    private ProjectService projectService;

    // -------------------- Hjælpemetoder --------------------

    private MockHttpSession ownerSession() {
        Profile profile = new Profile();
        profile.setId(1);
        profile.setRole(Role.OWNER);
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("profile", profile);
        return session;
    }

    private MockHttpSession seniorSession() {
        Profile profile = new Profile();
        profile.setId(2);
        profile.setRole(Role.SENIOR);
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("profile", profile);
        return session;
    }

    private MockHttpSession juniorSession() {
        Profile profile = new Profile();
        profile.setId(3);
        profile.setRole(Role.JUNIOR);
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("profile", profile);
        return session;
    }

    // -------------------- getAllTemplates --------------------

    @Test
    void shouldGetAllTemplates() throws Exception {
        ProjectTemplate template = new ProjectTemplate();
        template.setId(1);
        template.setName("Betalingsløsning");

        when(projectTemplateService.getAllTemplates())
                .thenReturn(List.of(template));

        mockMvc.perform(get("/templates")
                        .session(ownerSession()))
                .andExpect(status().isOk())
                .andExpect(view().name("templates/index"))
                .andExpect(model().attributeExists("templates"));
    }

    @Test
    void shouldGetAllTemplates_AsSenior() throws Exception {
        when(projectTemplateService.getAllTemplates())
                .thenReturn(List.of());

        mockMvc.perform(get("/templates")
                        .session(seniorSession()))
                .andExpect(status().isOk())
                .andExpect(view().name("templates/index"));
    }

    @Test
    void shouldGetAllTemplates_AsJunior() throws Exception {
        mockMvc.perform(get("/templates")
                        .session(juniorSession()))
                .andExpect(status().isOk())
                .andExpect(view().name("templates/index"));
    }

    @Test
    void shouldRedirectUnauthenticatedFromGetAllTemplates() throws Exception {
        mockMvc.perform(get("/templates"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/unauthorized"));
    }

    // -------------------- getTemplateById --------------------

    @Test
    void shouldGetTemplateById() throws Exception {
        ProjectTemplate template = new ProjectTemplate();
        template.setId(1);
        template.setName("Betalingsløsning");

        when(projectTemplateService.getTemplateById(1))
                .thenReturn(template);

        mockMvc.perform(get("/templates/1")
                        .session(ownerSession()))
                .andExpect(status().isOk())
                .andExpect(view().name("templates/detail"))
                .andExpect(model().attribute("template", template));
    }

    // -------------------- showEditForm --------------------

    @Test
    void shouldShowEditForm_AsSenior() throws Exception {
        ProjectTemplate template = new ProjectTemplate();
        template.setId(1);

        when(projectTemplateService.getTemplateById(1))
                .thenReturn(template);

        mockMvc.perform(get("/templates/1/edit")
                        .session(seniorSession()))
                .andExpect(status().isOk())
                .andExpect(view().name("templates/edit"))
                .andExpect(model().attribute("template", template));
    }

    @Test
    void shouldRejectJuniorFromEditForm() throws Exception {
        mockMvc.perform(get("/templates/1/edit")
                        .session(juniorSession()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/unauthorized"));
    }

    // -------------------- updateTemplate --------------------

    @Test
    void shouldUpdateTemplate() throws Exception {
        mockMvc.perform(post("/templates/1/edit")
                        .session(seniorSession())
                        .param("name", "Opdateret skabelon")
                        .param("description", "Ny beskrivelse")
                        .param("hourlyRate", "800")
                        .param("estimatedDeadlineDays", "2026-06-01"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/templates"));

        verify(projectTemplateService).updateTemplate(anyInt(), any(ProjectTemplate.class));
    }

    @Test
    void shouldRejectJuniorFromUpdateTemplate() throws Exception {
        mockMvc.perform(post("/templates/1/edit")
                        .session(juniorSession()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/unauthorized"));
    }

    // -------------------- saveAsTemplate --------------------

    @Test
    void shouldSaveAsTemplate() throws Exception {
        Project project = new Project();
        project.setId(1);
        project.setName("Project 1");
        project.setHourlyRate(new BigDecimal("500.00"));

        when(projectService.getProjectById(1))
                .thenReturn(project);

        mockMvc.perform(post("/projects/1/save-as-template")
                        .session(seniorSession()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/templates"));

        verify(projectTemplateService).saveAsTemplate(anyInt(), any(Project.class));
    }

    @Test
    void shouldRejectJuniorFromSaveAsTemplate() throws Exception {
        mockMvc.perform(post("/projects/1/save-as-template")
                        .session(juniorSession()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/unauthorized"));
    }

    // -------------------- deleteTemplate --------------------

    @Test
    void shouldDeleteTemplate() throws Exception {
        mockMvc.perform(post("/templates/1/delete")
                        .session(ownerSession()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/templates"));

        verify(projectTemplateService).deleteTemplate(1);
    }

    @Test
    void shouldRejectJuniorFromDeleteTemplate() throws Exception {
        mockMvc.perform(post("/templates/1/delete")
                        .session(juniorSession()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/unauthorized"));
    }
}