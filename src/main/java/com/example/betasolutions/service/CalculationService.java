package com.example.betasolutions.service;

import com.example.betasolutions.model.SubTask;
import com.example.betasolutions.model.Task;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CalculationService {

    public int calculateTaskDuration(List<SubTask> subTasks) {
        int total = 0;

        for (SubTask st : subTasks) {
            total += st.getDuration();
        }

        return total;
    }

    public int calculateProjectDuration(List<Task> tasks, List<SubTask> allSubTasks) {
        int total = 0;

        for (Task task : tasks) {

            int taskTotal = calculateTaskDuration(
                    allSubTasks.stream()
                            .filter(st -> st.getTask().getId() == task.getId())
                            .toList()
            );

            total += taskTotal;
        }

        return total;
    }

    public BigDecimal durationToHours(int minutes) {
        double hours = minutes / 60.0;
        return BigDecimal.valueOf(hours);
    }

    public BigDecimal calculateEstimatedPrice(int totalMinutes, BigDecimal hourlyRate) {
        return durationToHours(totalMinutes)
                .multiply(hourlyRate);
    }
}