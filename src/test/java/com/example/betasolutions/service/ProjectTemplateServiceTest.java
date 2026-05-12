package com.example.betasolutions.service;

import com.example.betasolutions.model.*;
import com.example.betasolutions.repository.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjectTemplateServiceTest {

    @Mock
    private ProjectTemplateRepository projectTemplateRepository;

    @Mock
    private TaskTemplateRepository taskTemplateRepository;

    @Mock
    private SubTaskTemplateRepository subTaskTemplateRepository;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private SubTaskRepository subTaskRepository;

    @InjectMocks
    private ProjectTemplateService projectTemplateService;

    // -------------------- getAllTemplates --------------------

    @Test
    void getAllTemplates_ReturnsList() {
        ProjectTemplate template1 = new ProjectTemplate();
        template1.setId(1);
        template1.setName("Betalingsløsning");

        ProjectTemplate template2 = new ProjectTemplate();
        template2.setId(2);
        template2.setName("Webshop");

        when(projectTemplateRepository.getAllTemplates())
                .thenReturn(List.of(template1, template2));

        List<ProjectTemplate> result = projectTemplateService.getAllTemplates();

        assertEquals(2, result.size());
        verify(projectTemplateRepository).getAllTemplates();
    }

    // -------------------- getTemplateById --------------------

    @Test
    void getTemplateById_ReturnsTemplateWithTasksAndSubTasks() {
        ProjectTemplate template = new ProjectTemplate();
        template.setId(1);
        template.setName("Betalingsløsning");

        TaskTemplate task = new TaskTemplate();
        task.setId(1);
        task.setName("Frontend");

        SubTaskTemplate subTask = new SubTaskTemplate();
        subTask.setId(1);
        subTask.setName("Design");

        when(projectTemplateRepository.getTemplateById(1)).thenReturn(template);
        when(taskTemplateRepository.getTaskTemplatesByProjectTemplateId(1)).thenReturn(List.of(task));
        when(subTaskTemplateRepository.getSubTaskTemplatesByTaskTemplateId(1)).thenReturn(List.of(subTask));

        ProjectTemplate result = projectTemplateService.getTemplateById(1);

        assertEquals("Betalingsløsning", result.getName());
        assertEquals(1, result.getTaskTemplates().size());
        assertEquals(1, result.getTaskTemplates().get(0).getSubTaskTemplates().size());
        verify(projectTemplateRepository).getTemplateById(1);
        verify(taskTemplateRepository).getTaskTemplatesByProjectTemplateId(1);
        verify(subTaskTemplateRepository).getSubTaskTemplatesByTaskTemplateId(1);
    }

    // -------------------- saveAsTemplate --------------------

    @Test
    void saveAsTemplate_SavesProjectTasksAndSubTasks() {
        Project project = new Project();
        project.setName("Project 1");
        project.setDescription("Beskrivelse");
        project.setHourlyRate(new BigDecimal("500.00"));

        Task task = new Task();
        task.setId(1);
        task.setName("Task 1");
        task.setDescription("Task beskrivelse");

        SubTask subTask = new SubTask();
        subTask.setName("SubTask 1");
        subTask.setDescription("SubTask beskrivelse");
        subTask.setDuration(new BigDecimal("5"));

        when(projectTemplateRepository.createTemplate(any(ProjectTemplate.class))).thenReturn(1);
        when(taskRepository.getTaskByProjectId(anyInt())).thenReturn(List.of(task));
        when(subTaskRepository.getSubTaskByTaskId(anyInt())).thenReturn(List.of(subTask));
        when(taskTemplateRepository.createTaskTemplate(any(TaskTemplate.class))).thenReturn(1);

        projectTemplateService.saveAsTemplate(1, project);

        verify(projectTemplateRepository).createTemplate(any(ProjectTemplate.class));
        verify(taskRepository).getTaskByProjectId(1);
        verify(taskTemplateRepository).createTaskTemplate(any(TaskTemplate.class));
        verify(subTaskRepository).getSubTaskByTaskId(1);
        verify(subTaskTemplateRepository).createSubTaskTemplate(any(SubTaskTemplate.class));
    }

    @Test
    void saveAsTemplate_NoTasks_OnlySavesProject() {
        Project project = new Project();
        project.setName("Project 1");
        project.setDescription("Beskrivelse");
        project.setHourlyRate(new BigDecimal("500.00"));

        when(projectTemplateRepository.createTemplate(any(ProjectTemplate.class))).thenReturn(1);
        when(taskRepository.getTaskByProjectId(anyInt())).thenReturn(List.of());

        projectTemplateService.saveAsTemplate(1, project);

        verify(projectTemplateRepository).createTemplate(any(ProjectTemplate.class));
        verify(taskTemplateRepository, never()).createTaskTemplate(any(TaskTemplate.class));
        verify(subTaskTemplateRepository, never()).createSubTaskTemplate(any(SubTaskTemplate.class));
    }

    // -------------------- updateTemplate --------------------

    @Test
    void updateTemplate_ReturnsTrue() {
        ProjectTemplate template = new ProjectTemplate();
        template.setName("Opdateret");

        when(projectTemplateRepository.updateTemplate(1, template)).thenReturn(true);

        boolean result = projectTemplateService.updateTemplate(1, template);

        assertTrue(result);
        verify(projectTemplateRepository).updateTemplate(1, template);
    }

    // -------------------- deleteTemplate --------------------

    @Test
    void deleteTemplate_DeletesInCorrectOrder() {
        TaskTemplate task1 = new TaskTemplate();
        task1.setId(1);

        TaskTemplate task2 = new TaskTemplate();
        task2.setId(2);

        when(taskTemplateRepository.getTaskTemplatesByProjectTemplateId(1))
                .thenReturn(List.of(task1, task2));
        when(projectTemplateRepository.deleteTemplate(1)).thenReturn(true);

        boolean result = projectTemplateService.deleteTemplate(1);

        assertTrue(result);
        // Verificer at subtasks slettes før tasks, og tasks før project
        verify(subTaskTemplateRepository).deleteByTaskTemplateId(1);
        verify(subTaskTemplateRepository).deleteByTaskTemplateId(2);
        verify(taskTemplateRepository).deleteByProjectTemplateId(1);
        verify(projectTemplateRepository).deleteTemplate(1);
    }
}