package com.example.betasolutions.model;

import java.math.BigDecimal;
import java.util.List;

public class TaskTemplate {
    private int id;
    private String name;
    private String description;
    private int projectTemplateId;
    private List<SubTaskTemplate> subTaskTemplates;

    public TaskTemplate() {}

    public TaskTemplate(int id, String name, String description, int projectTemplateId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.projectTemplateId = projectTemplateId;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getProjectTemplateId() { return projectTemplateId; }
    public void setProjectTemplateId(int projectTemplateId) { this.projectTemplateId = projectTemplateId; }

    public List<SubTaskTemplate> getSubTaskTemplates() { return subTaskTemplates; }
    public void setSubTaskTemplates(List<SubTaskTemplate> subTaskTemplates) { this.subTaskTemplates = subTaskTemplates; }
}