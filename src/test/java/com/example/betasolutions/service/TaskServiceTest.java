package com.example.betasolutions.service;

import com.example.betasolutions.enums.Status;
import com.example.betasolutions.model.Profile;
import com.example.betasolutions.model.SubTask;
import com.example.betasolutions.model.Task;
import com.example.betasolutions.repository.SubTaskRepository;
import com.example.betasolutions.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private SubTaskRepository subTaskRepository;

    @Mock
    private SubTaskService subTaskService;

    @Mock
    private CalculationService calculationService;

    @Mock
    private ProfileService profileService;

    @InjectMocks
    private TaskService taskService;

    // -------------------- getAllTask --------------------

    @Test
    void getAllTask_ReturnsList() {
        Task task = new Task();
        task.setId(1);
        task.setName("Task 1");

        when(taskRepository.getAllTask()).thenReturn(List.of(task));

        List<Task> result = taskService.getAllTask();

        assertEquals(1, result.size());
        verify(taskRepository).getAllTask();
    }

    // -------------------- getTaskById --------------------

    @Test
    void getTaskById_ReturnsTask() {
        Task task = new Task();
        task.setId(1);
        task.setName("Task 1");

        when(taskRepository.getTaskById(1)).thenReturn(task);

        Task result = taskService.getTaskById(1);

        assertEquals("Task 1", result.getName());
        verify(taskRepository).getTaskById(1);
    }

    // -------------------- getTaskByProjectId --------------------

    @Test
    void getTaskByProjectId_ReturnsList() {
        Task task = new Task();
        task.setId(1);

        when(taskRepository.getTaskByProjectId(1)).thenReturn(List.of(task));

        List<Task> result = taskService.getTaskByProjectId(1);

        assertEquals(1, result.size());
        verify(taskRepository).getTaskByProjectId(1);
    }

    // -------------------- createTask --------------------

    @Test
    void createTask_CallsRepository() {
        Task task = new Task();
        task.setName("New Task");

        taskService.createTask(task, 1);

        verify(taskRepository).createTask(task, 1);
    }

    // -------------------- updateTask --------------------

    @Test
    void updateTask_ReturnsTrue() {
        Task task = new Task();

        when(taskRepository.updateTask(1, task)).thenReturn(true);

        boolean result = taskService.updateTask(1, task);

        assertTrue(result);
        verify(taskRepository).updateTask(1, task);
    }

    // -------------------- deleteTask --------------------

    @Test
    void deleteTask_ReturnsTrue() {
        when(taskRepository.deleteTask(1)).thenReturn(true);

        boolean result = taskService.deleteTask(1);

        assertTrue(result);
        verify(taskRepository).deleteTask(1);
    }

    // -------------------- getTaskDuration --------------------

    @Test
    void getTaskDuration_ReturnsSumOfSubTaskDurations() {
        SubTask subTask1 = new SubTask(1, "ST1", "desc", BigDecimal.valueOf(30), Status.IN_PROGRESS, LocalDate.now(), LocalDate.now(), 1);
        SubTask subTask2 = new SubTask(2, "ST2", "desc", BigDecimal.valueOf(45), Status.DONE, LocalDate.now(), LocalDate.now(), 1);

        when(subTaskService.getSubTaskByTaskId(1)).thenReturn(List.of(subTask1, subTask2));
        when(calculationService.calculateTaskDuration(List.of(subTask1, subTask2))).thenReturn(BigDecimal.valueOf(75));

        BigDecimal result = taskService.getTaskDuration(1);

        assertEquals(BigDecimal.valueOf(75), result);
    }

    // -------------------- getTasksWithDuration --------------------

    @Test
    void getTasksWithDuration_SetsDurationOnEachTask() {
        Task task = new Task();
        task.setId(1);

        SubTask subTask = new SubTask(1, "ST1", "desc", BigDecimal.valueOf(20), Status.TODO, LocalDate.now(), LocalDate.now(), 1);

        when(taskRepository.getTaskByProjectId(1)).thenReturn(List.of(task));
        when(subTaskService.getSubTaskByTaskId(1)).thenReturn(List.of(subTask));
        when(calculationService.calculateTaskDuration(List.of(subTask))).thenReturn(BigDecimal.valueOf(20));

        List<Task> result = taskService.getTasksWithDuration(1);

        assertEquals(1, result.size());
        assertEquals(BigDecimal.valueOf(20), result.getFirst().getTotalDuration());
    }

    // -------------------- getProfilesByTaskId --------------------

    @Test
    void getProfilesByTaskId_ReturnsUniqueProfiles() {
        SubTask subTask1 = new SubTask(1, "ST1", "desc", BigDecimal.valueOf(10), Status.TODO, LocalDate.now(), LocalDate.now(), 1);
        SubTask subTask2 = new SubTask(2, "ST2", "desc", BigDecimal.valueOf(10), Status.TODO, LocalDate.now(), LocalDate.now(), 1);

        Profile profile = new Profile();
        profile.setId(1);
        profile.setName("Alice");

        when(subTaskService.getSubTaskByTaskId(1)).thenReturn(List.of(subTask1, subTask2));
        when(profileService.getProfilesBySubTaskId(1)).thenReturn(List.of(profile));
        when(profileService.getProfilesBySubTaskId(2)).thenReturn(List.of(profile));

        List<Profile> result = taskService.getProfilesByTaskId(1);

        // Samme profil skal kun optræde én gang
        assertEquals(1, result.size());
        assertEquals("Alice", result.getFirst().getName());
    }
}
