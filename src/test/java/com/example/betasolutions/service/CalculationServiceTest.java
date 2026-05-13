package com.example.betasolutions.service;

import com.example.betasolutions.enums.Status;
import com.example.betasolutions.model.Project;
import com.example.betasolutions.model.SubTask;
import com.example.betasolutions.model.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class CalculationServiceTest {

    @Autowired
    private CalculationService calculationService;

    @Test
    void shouldCalculateTaskDuration() {

        Task task = new Task();
        task.setId(1);

        SubTask subTask1 = new SubTask(
                1,
                "SubTask 1",
                "Description",
                BigDecimal.valueOf(30),
                Status.IN_PROGRESS,
                LocalDate.now(),
                LocalDate.now(),
                task.getId()
        );

        SubTask subTask2 = new SubTask(
                2,
                "SubTask 2",
                "Description",
                BigDecimal.valueOf(45),
                Status.DONE,
                LocalDate.now(),
                LocalDate.now(),
                task.getId()
        );

        BigDecimal result = calculationService.calculateTaskDuration(
                List.of(subTask1, subTask2)
        );

        assertEquals(BigDecimal.valueOf(75), result);
    }

    @Test
    void shouldCalculateProjectDuration() {

        Project project = new Project();
        project.setId(1);

        Task task1 = new Task();
        task1.setId(1);
        task1.setProject(project);

        Task task2 = new Task();
        task2.setId(2);
        task2.setProject(project);

        SubTask subTask1 = new SubTask(
                1,
                "SubTask 1",
                "Description",
                BigDecimal.valueOf(20),
                Status.IN_PROGRESS,
                LocalDate.now(),
                LocalDate.now(),
                task1.getId()
        );

        SubTask subTask2 = new SubTask(
                2,
                "SubTask 2",
                "Description",
                BigDecimal.valueOf(40),
                Status.DONE,
                LocalDate.now(),
                LocalDate.now(),
                task1.getId()
        );

        SubTask subTask3 = new SubTask(
                3,
                "SubTask 3",
                "Description",
                BigDecimal.valueOf(15),
                Status.TODO,
                LocalDate.now(),
                LocalDate.now(),
                task2.getId()
        );

        BigDecimal result = calculationService.calculateProjectDuration(
                List.of(task1, task2),
                List.of(subTask1, subTask2, subTask3)
        );

        assertEquals(BigDecimal.valueOf(75), result);
    }

    @Test
    void shouldCalculateEstimatedPrice() {

        BigDecimal result = calculationService.calculateEstimatedPrice(
                BigDecimal.valueOf(120),
                BigDecimal.valueOf(500)
        );

        assertEquals(BigDecimal.valueOf(60000), result);
    }
}