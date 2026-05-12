package com.example.betasolutions.model;


import com.example.betasolutions.enums.Status;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class Project {
    private int id;
    private String name;
    private String description;
    private BigDecimal hourlyRate;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate estimatedDeadline;
    private Status status;
    private BigDecimal finalPrice;
    private List<Skill> skills;
    private List<Profile> profiles;

    public Project(int id, String name, String description, BigDecimal hourlyRate,
                   LocalDate startDate, LocalDate endDate,
                   LocalDate estimatedDeadline, BigDecimal finalPrice, Status status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.hourlyRate = hourlyRate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.estimatedDeadline = estimatedDeadline;
        this.finalPrice = finalPrice;
        this.status = status;
    }

    public Project(int id, String name, String description, BigDecimal hourlyRate,
                   LocalDate startDate, LocalDate endDate,
                   LocalDate estimatedDeadline, BigDecimal finalPrice, Status status,
                   List<Skill> skills, List<Profile> profiles) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.hourlyRate = hourlyRate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.estimatedDeadline = estimatedDeadline;
        this.finalPrice = finalPrice;
        this.status = status;
        this.skills = skills;
        this.profiles = profiles;
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

    public BigDecimal getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(BigDecimal hourlyRate) {
        this.hourlyRate = hourlyRate;
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

    public List<Profile> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<Profile> profiles) {
        this.profiles = profiles;
    }

    public BigDecimal getFinalPrice() {return finalPrice;}

    public void setFinalPrice(BigDecimal finalPrice) {this.finalPrice = finalPrice;}
}
