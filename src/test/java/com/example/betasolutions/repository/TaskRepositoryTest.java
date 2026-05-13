package com.example.betasolutions.repository;

import com.example.betasolutions.enums.Status;
import com.example.betasolutions.exception.TaskNotFoundException;
import com.example.betasolutions.model.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = {"/schema.sql", "/data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    // -------------------- getAllTask --------------------

    @Test
    void getAllTask_ReturnsList() {
        List<Task> result = taskRepository.getAllTask();

        assertEquals(3, result.size());
    }

    @Test
    void getAllTask_ContainsExpectedNames() {
        List<Task> result = taskRepository.getAllTask();

        List<String> names = result.stream().map(Task::getName).toList();
        assertTrue(names.contains("Task 1"));
        assertTrue(names.contains("Task 2"));
        assertTrue(names.contains("Task 3"));
    }

    // -------------------- getTaskById --------------------

    @Test
    void getTaskById_TaskExists_ReturnsTask() {
        Task result = taskRepository.getTaskById(1);

        assertEquals("Task 1", result.getName());
        assertEquals("First task", result.getDescription());
        assertEquals(Status.IN_PROGRESS, result.getStatus());
    }

    @Test
    void getTaskById_TaskNotFound_ThrowsException() {
        assertThrows(TaskNotFoundException.class,
                () -> taskRepository.getTaskById(999));
    }

    // -------------------- getTaskByProjectId --------------------

    @Test
    void getTaskByProjectId_ProjectWithTwoTasks_ReturnsBothTasks() {
        List<Task> result = taskRepository.getTaskByProjectId(1);

        assertEquals(2, result.size());
    }

    @Test
    void getTaskByProjectId_ProjectWithOneTask_ReturnsOneTask() {
        List<Task> result = taskRepository.getTaskByProjectId(2);

        assertEquals(1, result.size());
        assertEquals("Task 3", result.get(0).getName());
    }

    @Test
    void getTaskByProjectId_ProjectWithNoTasks_ReturnsEmptyList() {
        List<Task> result = taskRepository.getTaskByProjectId(999);

        assertTrue(result.isEmpty());
    }

    // -------------------- createTask --------------------

    @Test
    void createTask_ValidTask_IsPersistedAndRetrievable() {
        Task newTask = new Task();
        newTask.setName("Task 4");
        newTask.setDescription("Fourth task");
        newTask.setStatus(Status.TODO);
        newTask.setStartDate(LocalDate.of(2026, 5, 1));
        newTask.setEndDate(LocalDate.of(2026, 5, 15));

        taskRepository.createTask(newTask, 1);

        List<Task> tasksForProject1 = taskRepository.getTaskByProjectId(1);
        assertEquals(3, tasksForProject1.size());

        Task saved = tasksForProject1.stream()
                .filter(t -> t.getName().equals("Task 4"))
                .findFirst()
                .orElseThrow();

        assertEquals("Fourth task", saved.getDescription());
        assertEquals(Status.TODO, saved.getStatus());
    }

    @Test
    void createTask_CanBeRetrievedById() {
        Task newTask = new Task();
        newTask.setName("Task 4");
        newTask.setDescription("Fourth task");
        newTask.setStatus(Status.TODO);
        newTask.setStartDate(LocalDate.of(2026, 5, 1));
        newTask.setEndDate(LocalDate.of(2026, 5, 15));

        taskRepository.createTask(newTask, 1);

        // ID 4 is next after the three seeded tasks
        Task result = taskRepository.getTaskById(4);
        assertEquals("Task 4", result.getName());
    }

    // -------------------- updateTask --------------------

    @Test
    void updateTask_ValidUpdate_ChangesArePersisted() {
        Task updated = new Task();
        updated.setName("Task 1 Updated");
        updated.setDescription("Updated description");
        updated.setStatus(Status.DONE);
        updated.setStartDate(LocalDate.of(2026, 1, 1));
        updated.setEndDate(LocalDate.of(2026, 1, 20));

        boolean result = taskRepository.updateTask(1, updated);

        assertTrue(result);
        Task fetched = taskRepository.getTaskById(1);
        assertEquals("Task 1 Updated", fetched.getName());
        assertEquals("Updated description", fetched.getDescription());
        assertEquals(Status.DONE, fetched.getStatus());
    }

    @Test
    void updateTask_TaskNotFound_ReturnsFalse() {
        Task updated = new Task();
        updated.setName("Ghost Task");
        updated.setDescription("Does not exist");
        updated.setStatus(Status.TODO);
        updated.setStartDate(LocalDate.of(2026, 1, 1));
        updated.setEndDate(LocalDate.of(2026, 1, 10));

        boolean result = taskRepository.updateTask(999, updated);

        assertFalse(result);
    }

    // -------------------- deleteTask --------------------

    @Test
    void deleteTask_TaskExists_ReturnsTrue() {
        boolean result = taskRepository.deleteTask(3);

        assertTrue(result);
    }

    @Test
    void deleteTask_TaskExists_CanNoLongerBeRetrieved() {
        taskRepository.deleteTask(3);

        assertThrows(TaskNotFoundException.class,
                () -> taskRepository.getTaskById(3));
    }

    @Test
    void deleteTask_TaskExists_ReducesCount() {
        taskRepository.deleteTask(3);

        List<Task> remaining = taskRepository.getAllTask();
        assertEquals(2, remaining.size());
    }

    @Test
    void deleteTask_TaskNotFound_ReturnsFalse() {
        boolean result = taskRepository.deleteTask(999);

        assertFalse(result);
    }
}