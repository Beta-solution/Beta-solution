package com.example.betasolutions.model;

import com.example.betasolutions.enums.Status;

import java.time.LocalDate;

public class SubTask {
    private int id;
    private String name;
    private String description;
    private Profile profile;
    private int duration;
    private Skill skill;
    private Status status;
    private LocalDate startDate;
    private LocalDate endDate;
    private Task task;

    public SubTask(int id, String name, String description, int duration,
                   Status status, LocalDate startDate, LocalDate endDate, int taskId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.duration = duration;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public SubTask(int id, String name, String description, Profile profile,
                   int duration, Skill skill, Status status,
                   LocalDate startDate, LocalDate endDate, Task task) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.profile = profile;
        this.duration = duration;
        this.skill = skill;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
        this.task = task;
    }

    public SubTask() {

    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Profile getProfile() { return profile; }
    public void setProfile(Profile profile) { this.profile = profile; }

    public int getDuration() { return duration; }
    public void setDuration(int duration) { this.duration = duration; }

    public Skill getSkill() { return skill; }
    public void setSkill(Skill skill) { this.skill = skill; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public Task getTask() { return task; }
    public void setTask(Task task) { this.task = task; }
}
