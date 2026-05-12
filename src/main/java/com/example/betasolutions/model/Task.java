package com.example.betasolutions.model;


import com.example.betasolutions.enums.Status;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class Task {
    private int id;
    private String name;
    private String description;
    private BigDecimal totalDuration;
    private List<Profile> profiles;
    private Skill skill;
    private Status status;
    private LocalDate startDate;
    private LocalDate endDate;
    private Project project;

    public Task(int id, String name, String description, Status status,
                LocalDate startDate, LocalDate endDate, int projectId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Task(int id, String name, String description, List<Profile> profiles,
                Skill skill, Status status,
                LocalDate startDate, LocalDate endDate,
                Project project) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.profiles = profiles;
        this.skill = skill;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
        this.project = project;
    }

    public Task() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Profile> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<Profile> profiles) {
        this.profiles = profiles;
    }

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public BigDecimal getTotalDuration() {return totalDuration;}

    public void setTotalDuration(BigDecimal totalDuration) {this.totalDuration = totalDuration;}

}