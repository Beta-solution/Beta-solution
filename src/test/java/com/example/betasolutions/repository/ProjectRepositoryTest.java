package com.example.betasolutions.repository;

import com.example.betasolutions.enums.Status;
import com.example.betasolutions.exception.ProjectNotFoundException;
import com.example.betasolutions.model.Project;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = {"/schema.sql", "/data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)

public class ProjectRepositoryTest {
    @Autowired
    private ProjectRepository projectRepository;

    @Test
    void getAllProjects_ReturnsList() {
        List<Project> result = projectRepository.getAllProjects();

        assertEquals(2, result.size());
    }

    @Test
    void getAllProjects_ContainsExpectedNames() {
        List<Project> result = projectRepository.getAllProjects();

        List<String> names = result.stream().map(Project::getName).toList();
        assertTrue(names.contains("Project1"));
        assertTrue(names.contains("Project2"));
    }

    @Test
    void getProjectById_ProjectExists_ReturnsProject() {
        Project result = projectRepository.getProjectById(1);

        assertEquals("Project1", result.getName());
        assertEquals("First project", result.getDescription());
        assertEquals(Status.IN_PROGRESS, result.getStatus());
        assertEquals(0, BigDecimal.valueOf(500.00).compareTo(result.getHourlyRate()));
    }

    @Test
    void getProjectById_ProjectNotFound_ThrowsException() {
        assertThrows(ProjectNotFoundException.class, () -> projectRepository.getProjectById(999));
    }

    @Test
    void createProject_ValidProject_IsPersistedAndRetrievable() {
        Project newProject = new Project();
        newProject.setName("Project 3");
        newProject.setDescription("Third project");
        newProject.setStatus(Status.IN_PROGRESS);
        newProject.setHourlyRate(BigDecimal.valueOf(500.00));
        newProject.setStartDate(LocalDate.of(2026, 5, 1));
        newProject.setEndDate(LocalDate.of(2026, 6, 1));
        newProject.setFinalPrice(BigDecimal.valueOf(60000.00));
        projectRepository.createProject(newProject);

        List<Project> all = projectRepository.getAllProjects();
        assertEquals(3, all.size());

        Project saved = all.stream()
                .filter(p -> p.getName().equals("Project 3"))
                .findFirst()
                .orElseThrow();

        assertEquals("Third project", saved.getDescription());
        assertEquals(0, BigDecimal.valueOf(750.00).compareTo(saved.getHourlyRate()));
        assertEquals(Status.TODO, saved.getStatus());
    }

    @Test
    void createProject_CanBeRetrievedById() {
        Project newProject = new Project();
        newProject.setName("Project 3");
        newProject.setDescription("Third project");
        newProject.setHourlyRate(BigDecimal.valueOf(750.00));
        newProject.setStartDate(LocalDate.of(2026, 4, 1));
        newProject.setEndDate(LocalDate.of(2026, 5, 1));
        newProject.setEstimatedDeadline(LocalDate.of(2026, 5, 10));
        newProject.setFinalPrice(BigDecimal.valueOf(60000.00));
        newProject.setStatus(Status.TODO);

        projectRepository.createProject(newProject);

        // ID 3 is next after the two seeded projects
        Project result = projectRepository.getProjectById(3);
        assertEquals("Project 3", result.getName());
    }

    // -------------------- updateProject --------------------

    @Test
    void updateProject_ValidUpdate_ChangesArePersisted() {
        Project updated = new Project();
        updated.setName("Project 1 Updated");
        updated.setDescription("Updated description");
        updated.setHourlyRate(BigDecimal.valueOf(600.00));
        updated.setStartDate(LocalDate.of(2026, 1, 1));
        updated.setEndDate(LocalDate.of(2026, 3, 1));
        updated.setEstimatedDeadline(LocalDate.of(2026, 3, 5));
        updated.setFinalPrice(BigDecimal.valueOf(55000.00));
        updated.setStatus(Status.DONE);

        boolean result = projectRepository.updateProject(1, updated);

        assertTrue(result);
        Project fetched = projectRepository.getProjectById(1);
        assertEquals("Project 1 Updated", fetched.getName());
        assertEquals("Updated description", fetched.getDescription());
        assertEquals(Status.DONE, fetched.getStatus());
        assertEquals(0, BigDecimal.valueOf(600.00).compareTo(fetched.getHourlyRate()));
    }

    @Test
    void updateProject_ProjectNotFound_ThrowsException() {
        Project updated = new Project();
        updated.setName("Ghost Project");
        updated.setDescription("Does not exist");
        updated.setHourlyRate(BigDecimal.valueOf(100.00));
        updated.setStartDate(LocalDate.of(2026, 1, 1));
        updated.setEndDate(LocalDate.of(2026, 2, 1));
        updated.setEstimatedDeadline(LocalDate.of(2026, 2, 5));
        updated.setFinalPrice(BigDecimal.ZERO);
        updated.setStatus(Status.TODO);

        assertThrows(ProjectNotFoundException.class,
                () -> projectRepository.updateProject(999, updated));
    }

    // -------------------- deleteProject --------------------

    @Test
    void deleteProject_ProjectExists_ReturnsTrue() {
        boolean result = projectRepository.deleteProject(2);

        assertTrue(result);
        assertEquals(1, projectRepository.getAllProjects().size());
    }

    @Test
    void deleteProject_ProjectExists_CanNoLongerBeRetrieved() {
        projectRepository.deleteProject(2);

        assertThrows(ProjectNotFoundException.class,
                () -> projectRepository.getProjectById(2));
    }

    @Test
    void deleteProject_ProjectNotFound_ReturnsFalse() {
        boolean result = projectRepository.deleteProject(999);

        assertFalse(result);
    }

    // -------------------- getProjectByStatus --------------------

    @Test
    void getProjectByStatus_InProgress_ReturnsMatchingProjects() {
        List<Project> result = projectRepository.getProjectByStatus(Status.IN_PROGRESS);

        assertEquals(1, result.size());
        assertEquals("Project 1", result.get(0).getName());
    }

    @Test
    void getProjectByStatus_Todo_ReturnsMatchingProjects() {
        List<Project> result = projectRepository.getProjectByStatus(Status.TODO);

        assertEquals(1, result.size());
        assertEquals("Project 2", result.get(0).getName());
    }

    @Test
    void getProjectByStatus_Done_ReturnsEmptyList() {
        List<Project> result = projectRepository.getProjectByStatus(Status.DONE);

        assertTrue(result.isEmpty());
    }
}
