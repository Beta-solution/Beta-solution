package com.example.betasolutions.service;

import com.example.betasolutions.enums.Status;
import com.example.betasolutions.model.Project;
import com.example.betasolutions.model.Task;
import com.example.betasolutions.repository.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private TaskService taskService;

    @Mock
    private CalculationService calculationService;

    @InjectMocks
    private ProjectService projectService;

    // -------------------- getAllProjects --------------------

    @Test
    void getAllProjects_ReturnsList() {
        Project project = new Project();
        project.setId(1);
        project.setName("Project 1");

        when(projectRepository.getAllProjects()).thenReturn(List.of(project));

        List<Project> result = projectService.getAllProjects();

        assertEquals(1, result.size());
        verify(projectRepository).getAllProjects();
    }

    // -------------------- getProjectById --------------------

    @Test
    void getProjectById_ReturnsProject() {
        Project project = new Project();
        project.setId(1);
        project.setName("Project 1");

        when(projectRepository.getProjectById(1)).thenReturn(project);

        Project result = projectService.getProjectById(1);

        assertEquals("Project 1", result.getName());
        verify(projectRepository).getProjectById(1);
    }

    // -------------------- createProject --------------------

    @Test
    void createProject_CallsRepository() {
        Project project = new Project();
        project.setName("New Project");

        projectService.createProject(project);

        verify(projectRepository).createProject(project);
    }

    // -------------------- updateProject --------------------

    @Test
    void updateProject_ReturnsTrue() {
        Project project = new Project();

        when(projectRepository.updateProject(1, project)).thenReturn(true);

        boolean result = projectService.updateProject(1, project);

        assertTrue(result);
        verify(projectRepository).updateProject(1, project);
    }

    // -------------------- deleteProject --------------------

    @Test
    void deleteProject_ReturnsTrue() {
        when(projectRepository.deleteProject(1)).thenReturn(true);

        boolean result = projectService.deleteProject(1);

        assertTrue(result);
        verify(projectRepository).deleteProject(1);
    }

    // -------------------- getProjectByStatus --------------------

    @Test
    void getProjectByStatus_ReturnsList() {
        Project project = new Project();
        project.setStatus(Status.IN_PROGRESS);

        when(projectRepository.getProjectByStatus(Status.IN_PROGRESS)).thenReturn(List.of(project));

        List<Project> result = projectService.getProjectByStatus(Status.IN_PROGRESS);

        assertEquals(1, result.size());
        verify(projectRepository).getProjectByStatus(Status.IN_PROGRESS);
    }

    // -------------------- addProfileToProject --------------------

    @Test
    void addProfileToProject_ReturnsTrue() {
        when(projectRepository.addProfileToProject(1, 1)).thenReturn(true);

        boolean result = projectService.addProfileToProject(1, 1);

        assertTrue(result);
        verify(projectRepository).addProfileToProject(1, 1);
    }

    // -------------------- removeProfileFromProject --------------------

    @Test
    void removeProfileFromProject_ReturnsTrue() {
        when(projectRepository.removeProfileFromProject(1, 1)).thenReturn(true);

        boolean result = projectService.removeProfileFromProject(1, 1);

        assertTrue(result);
        verify(projectRepository).removeProfileFromProject(1, 1);
    }

    // -------------------- getProjectDuration --------------------

    @Test
    void getProjectDuration_SumsDurationAcrossTasks() {
        Task task1 = new Task();
        task1.setId(1);

        Task task2 = new Task();
        task2.setId(2);

        when(taskService.getTaskByProjectId(1)).thenReturn(List.of(task1, task2));
        when(taskService.getTaskDuration(1)).thenReturn(BigDecimal.valueOf(30));
        when(taskService.getTaskDuration(2)).thenReturn(BigDecimal.valueOf(45));

        BigDecimal result = projectService.getProjectDuration(1);

        assertEquals(BigDecimal.valueOf(75), result);
    }

    // -------------------- getEstimatedPrice --------------------

    @Test
    void getEstimatedPrice_ReturnsCalculatedPrice() {
        Project project = new Project();
        project.setId(1);
        project.setHourlyRate(BigDecimal.valueOf(500));

        Task task = new Task();
        task.setId(1);

        when(projectRepository.getProjectById(1)).thenReturn(project);
        when(taskService.getTaskByProjectId(1)).thenReturn(List.of(task));
        when(taskService.getTaskDuration(1)).thenReturn(BigDecimal.valueOf(120));
        when(calculationService.calculateEstimatedPrice(BigDecimal.valueOf(120), BigDecimal.valueOf(500)))
                .thenReturn(BigDecimal.valueOf(60000));

        BigDecimal result = projectService.getEstimatedPrice(1);

        assertEquals(BigDecimal.valueOf(60000), result);
    }
}
