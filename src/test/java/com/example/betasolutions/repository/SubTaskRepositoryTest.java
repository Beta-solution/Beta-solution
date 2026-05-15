package com.example.betasolutions.repository;

import com.example.betasolutions.enums.Status;
import com.example.betasolutions.model.SubTask;
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
public class SubTaskRepositoryTest {

    @Autowired
    SubTaskRepository subTaskRepository;

    // -------------------- getAllSubTask --------------------

    @Test
    void getAllSubTask_ReturnsList() {
        List<SubTask> result = subTaskRepository.getAllSubTask();
        assertEquals(3, result.size());
    }

    // -------------------- getSubTaskById --------------------

    @Test
    void getSubTaskById_SubTaskExists_ReturnsSubTask() {
        SubTask result = subTaskRepository.getSubTaskById(1);
        assertEquals("SubTask 1", result.getName());
        assertEquals(Status.IN_PROGRESS, result.getStatus());
    }

    @Test
    void getSubTaskById_SubTaskNotFound_ThrowsException() {
        assertThrows(Exception.class, () -> subTaskRepository.getSubTaskById(999));
    }

    // -------------------- getSubTaskByTaskId --------------------

    @Test
    void getSubTaskByTaskId_TaskExists_ReturnsSubTasks() {
        List<SubTask> result = subTaskRepository.getSubTaskByTaskId(1);
        assertEquals(2, result.size());
    }

    @Test
    void getSubTaskByTaskId_NoSubTasks_ReturnsEmptyList() {
        List<SubTask> result = subTaskRepository.getSubTaskByTaskId(2);
        assertTrue(result.isEmpty());
    }

    // -------------------- createSubTask --------------------

    @Test
    void createSubTask_ValidSubTask_IncreasesCount() {
        SubTask subTask = new SubTask();
        subTask.setName("New SubTask");
        subTask.setDescription("New description");
        subTask.setDuration(BigDecimal.valueOf(6.00));
        subTask.setStatus(Status.TODO);
        subTask.setStartDate(LocalDate.of(2026, 4, 1));
        subTask.setEndDate(LocalDate.of(2026, 4, 5));

        subTaskRepository.createSubTask(subTask, 2);

        assertEquals(4, subTaskRepository.getAllSubTask().size());
    }

    // -------------------- updateSubTask --------------------

    @Test
    void updateSubTask_ValidSubTask_ReturnsTrue() {
        SubTask updated = new SubTask();
        updated.setName("Updated SubTask");
        updated.setDescription("Updated description");
        updated.setDuration(BigDecimal.valueOf(8.00));
        updated.setStatus(Status.DONE);
        updated.setStartDate(LocalDate.of(2026, 1, 1));
        updated.setEndDate(LocalDate.of(2026, 1, 3));

        boolean result = subTaskRepository.updateSubTask(1, updated);

        assertTrue(result);
        assertEquals("Updated SubTask", subTaskRepository.getSubTaskById(1).getName());
    }

    // -------------------- deleteSubTask --------------------

    @Test
    void deleteSubTask_SubTaskExists_ReturnsTrue() {
        boolean result = subTaskRepository.deleteSubTask(1);
        assertTrue(result);
    }

    @Test
    void deleteSubTask_SubTaskNotFound_ReturnsFalse() {
        boolean result = subTaskRepository.deleteSubTask(999);
        assertFalse(result);
    }

    // -------------------- addProfileToSubTask / removeProfileFromSubTask --------------------

    @Test
    void addProfileToSubTask_ValidIds_ReturnsTrue() {
        boolean result = subTaskRepository.addProfileToSubTask(2, 1);
        assertTrue(result);
    }

    @Test
    void removeProfileFromSubTask_ExistingRelation_ReturnsTrue() {
        boolean result = subTaskRepository.removeProfileFromSubTask(1, 1);
        assertTrue(result);
    }
}
