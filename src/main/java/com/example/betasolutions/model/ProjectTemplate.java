package com.example.betasolutions.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class ProjectTemplate {
    private int id;
    private String name;
    private String description;
    private BigDecimal hourlyRate;
    private LocalDate estimatedDeadlineDays;
    private List<TaskTemplate> taskTemplates;

    public ProjectTemplate() {}

    public ProjectTemplate(int id, String name, String description,
                           BigDecimal hourlyRate, LocalDate estimatedDeadlineDays) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.hourlyRate = hourlyRate;
        this.estimatedDeadlineDays = estimatedDeadlineDays;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public BigDecimal getHourlyRate() { return hourlyRate; }
    public void setHourlyRate(BigDecimal hourlyRate) { this.hourlyRate = hourlyRate; }

    public LocalDate getEstimatedDeadlineDays() { return estimatedDeadlineDays; }
    public void setEstimatedDeadlineDays(LocalDate date) { this.estimatedDeadlineDays = date; }

    public List<TaskTemplate> getTaskTemplates() { return taskTemplates; }
    public void setTaskTemplates(List<TaskTemplate> taskTemplates) { this.taskTemplates = taskTemplates; }
}