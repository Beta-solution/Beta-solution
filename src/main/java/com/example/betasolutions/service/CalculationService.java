package com.example.betasolutions.service;

import com.example.betasolutions.model.SubTask;
import com.example.betasolutions.model.Task;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CalculationService {

    public BigDecimal calculateTaskDuration(List<SubTask> subTasks) {
        BigDecimal total = BigDecimal.valueOf(0);

        for (SubTask st : subTasks) {
            total = total.add(st.getDuration());
        }

        return total;
    }

    public BigDecimal calculateProjectDuration(List<Task> tasks, List<SubTask> allSubTasks) {
        BigDecimal total = BigDecimal.valueOf(0);

        for (Task task : tasks) {

            BigDecimal taskTotal = calculateTaskDuration(
                    allSubTasks.stream()
                            .filter(st -> st.getTaskId() == task.getId())
                            .toList()
            );

            total = total.add(taskTotal);
        }

        return total;
    }


    public BigDecimal calculateEstimatedPrice(BigDecimal totalHours, BigDecimal hourlyRate) {
        return totalHours.multiply(hourlyRate);
    }
}