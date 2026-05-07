package com.example.betasolutions.model;


import com.example.betasolutions.enums.Status;

import java.time.LocalDate;
import java.util.List;

public class Project {
    private int id;
    private String name;
    private String description;
    private float totalPrice;
    private int totalDuration;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate estimatedDeadline;
    private Status status;
    private List<Skill> skills;
    private List<Profile> members;

    public Project(int id, String name, String description, float totalPrice,
                   int totalDuration, LocalDate startDate, LocalDate endDate,
                   LocalDate estimatedDeadline, Status status,
                   List<Skill> skills, List<Profile> members) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.totalPrice = totalPrice;
        this.totalDuration = totalDuration;
        this.startDate = startDate;
        this.endDate = endDate;
        this.estimatedDeadline = estimatedDeadline;
        this.status = status;
        this.skills = skills;
        this.members = members;
    }

    public Project(int id, String name, String description, float price, int totalDuration, LocalDate startDate,
                   LocalDate endDate, LocalDate estimatedDeadline, Status status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.totalPrice = totalPrice;
        this.totalDuration = totalDuration;
        this.startDate = startDate;
        this.endDate = endDate;
        this.estimatedDeadline = estimatedDeadline;
    }

    public Project() {

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

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getTotalDuration() {
        return totalDuration;
    }

    public void setTotalDuration(int totalDuration) {
        this.totalDuration = totalDuration;
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

    public LocalDate getEstimatedDeadline() {
        return estimatedDeadline;
    }

    public void setEstimatedDeadline(LocalDate estimatedDeadline) {
        this.estimatedDeadline = estimatedDeadline;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    public List<Profile> getMembers() {
        return members;
    }

    public void setMembers(List<Profile> members) {
        this.members = members;
    }
}
