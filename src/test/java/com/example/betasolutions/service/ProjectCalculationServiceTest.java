package com.example.betasolutions.service;

import com.example.betasolutions.enums.Status;
import com.example.betasolutions.model.Project;
import com.example.betasolutions.model.SubTask;
import com.example.betasolutions.model.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = {"/schema.sql", "/data.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/cleanup.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class ProjectCalculationServiceTest {

    @Autowired
    private ProjectCalculationService projectCalculationService;

    @Test
    void shouldCalculateTaskDuration() {

        Task task = new Task();
        task.setId(1);

        SubTask subTask1 = new SubTask(
                1,
                "SubTask 1",
                "Description",
                null,
                30,
                null,
                Status.IN_PROGRESS,
                LocalDate.now(),
                LocalDate.now(),
                task
        );

        SubTask subTask2 = new SubTask(
                2,
                "SubTask 2",
                "Description",
                null,
                45,
                null,
                Status.DONE,
                LocalDate.now(),
                LocalDate.now(),
                task
        );

        int result = projectCalculationService.calculateTaskDuration(
                List.of(subTask1, subTask2)
        );

        assertEquals(75, result);
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
                null,
                20,
                null,
                Status.IN_PROGRESS,
                LocalDate.now(),
                LocalDate.now(),
                task1
        );

        SubTask subTask2 = new SubTask(
                2,
                "SubTask 2",
                "Description",
                null,
                40,
                null,
                Status.DONE,
                LocalDate.now(),
                LocalDate.now(),
                task1
        );

        SubTask subTask3 = new SubTask(
                3,
                "SubTask 3",
                "Description",
                null,
                15,
                null,
                Status.TODO,
                LocalDate.now(),
                LocalDate.now(),
                task2
        );

        int result = projectCalculationService.calculateProjectDuration(
                List.of(task1, task2),
                List.of(subTask1, subTask2, subTask3)
        );

        assertEquals(75, result);
    }

    @Test
    void shouldConvertMinutesToHours() {

        BigDecimal result = projectCalculationService.durationToHours(90);

        assertEquals(BigDecimal.valueOf(1.5), result);
    }

    @Test
    void shouldCalculateEstimatedPrice() {

        BigDecimal result = projectCalculationService.calculateEstimatedPrice(
                120,
                BigDecimal.valueOf(500)
        );

        assertEquals(BigDecimal.valueOf(1000.0), result);
    }
}