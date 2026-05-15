package com.example.betasolutions.service;

import com.example.betasolutions.enums.Status;
import com.example.betasolutions.model.SubTask;
import com.example.betasolutions.repository.SubTaskRepository;
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
class SubTaskServiceTest {

    @Mock
    private SubTaskRepository subTaskRepository;

    @InjectMocks
    private SubTaskService subTaskService;

    // -------------------- getAllSubTask --------------------

    @Test
    void getAllSubTask_ReturnsList() {
        SubTask subTask = new SubTask(1, "SubTask 1", "desc", BigDecimal.valueOf(5), Status.TODO, LocalDate.now(), LocalDate.now(), 1);

        when(subTaskRepository.getAllSubTask()).thenReturn(List.of(subTask));

        List<SubTask> result = subTaskService.getAllSubTask();

        assertEquals(1, result.size());
        verify(subTaskRepository).getAllSubTask();
    }

    // -------------------- getSubTaskById --------------------

    @Test
    void getSubTaskById_ReturnsSubTask() {
        SubTask subTask = new SubTask(1, "SubTask 1", "desc", BigDecimal.valueOf(5), Status.TODO, LocalDate.now(), LocalDate.now(), 1);

        when(subTaskRepository.getSubTaskById(1)).thenReturn(subTask);

        SubTask result = subTaskService.getSubTaskById(1);

        assertEquals("SubTask 1", result.getName());
        verify(subTaskRepository).getSubTaskById(1);
    }

    // -------------------- getSubTaskByTaskId --------------------

    @Test
    void getSubTaskByTaskId_ReturnsList() {
        SubTask subTask = new SubTask(1, "SubTask 1", "desc", BigDecimal.valueOf(5), Status.TODO, LocalDate.now(), LocalDate.now(), 1);

        when(subTaskRepository.getSubTaskByTaskId(1)).thenReturn(List.of(subTask));

        List<SubTask> result = subTaskService.getSubTaskByTaskId(1);

        assertEquals(1, result.size());
        verify(subTaskRepository).getSubTaskByTaskId(1);
    }

    // -------------------- createSubTask --------------------

    @Test
    void createSubTask_CallsRepository() {
        SubTask subTask = new SubTask();
        subTask.setName("New SubTask");

        subTaskService.createSubTask(subTask, 1);

        verify(subTaskRepository).createSubTask(subTask, 1);
    }

    // -------------------- updateSubTask --------------------

    @Test
    void updateSubTask_ReturnsTrue() {
        SubTask subTask = new SubTask();

        when(subTaskRepository.updateSubTask(1, subTask)).thenReturn(true);

        boolean result = subTaskService.updateSubTask(1, subTask);

        assertTrue(result);
        verify(subTaskRepository).updateSubTask(1, subTask);
    }

    // -------------------- deleteSubTask --------------------

    @Test
    void deleteSubTask_ReturnsTrue() {
        when(subTaskRepository.deleteSubTask(1)).thenReturn(true);

        boolean result = subTaskService.deleteSubTask(1);

        assertTrue(result);
        verify(subTaskRepository).deleteSubTask(1);
    }

    // -------------------- addProfileToSubTask --------------------

    @Test
    void addProfileToSubTask_ReturnsTrue() {
        when(subTaskRepository.addProfileToSubTask(1, 1)).thenReturn(true);

        boolean result = subTaskService.addProfileToSubTask(1, 1);

        assertTrue(result);
        verify(subTaskRepository).addProfileToSubTask(1, 1);
    }

    // -------------------- removeProfileFromSubTask --------------------

    @Test
    void removeProfileFromSubTask_ReturnsTrue() {
        when(subTaskRepository.removeProfileFromSubTask(1, 1)).thenReturn(true);

        boolean result = subTaskService.removeProfileFromSubTask(1, 1);

        assertTrue(result);
        verify(subTaskRepository).removeProfileFromSubTask(1, 1);
    }
}
