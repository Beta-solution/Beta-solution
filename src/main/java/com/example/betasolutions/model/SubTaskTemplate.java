package com.example.betasolutions.model;

import java.math.BigDecimal;

public class SubTaskTemplate {
    private int id;
    private String name;
    private String description;
    private BigDecimal duration;
    private int taskTemplateId;

    public SubTaskTemplate() {}

    public SubTaskTemplate(int id, String name, String description,
                           BigDecimal duration, int taskTemplateId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.duration = duration;
        this.taskTemplateId = taskTemplateId;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public BigDecimal getDuration() { return duration; }
    public void setDuration(BigDecimal duration) { this.duration = duration; }

    public int getTaskTemplateId() { return taskTemplateId; }
    public void setTaskTemplateId(int taskTemplateId) { this.taskTemplateId = taskTemplateId; }
}