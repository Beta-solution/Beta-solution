package com.example.betasolutions.repository;

import com.example.betasolutions.model.Project;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ProjectRepository {
    private final JdbcTemplate jdbcTemplate;

    public ProjectRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate=jdbcTemplate;
    }

    public void getAllProjects() {

    }

    public void getProjectById(){

    }

    public void createProject(Project project){
        String sql = """
    INSERT INTO Projects (name, description, price, totalDuration,
    startDate, endDate, estimatedDeadline, status)
    VALUES (?, ?, ?, ?, ?, ?, ?, ?)
    """;
        jdbcTemplate.update(sql,
                project.getName(),
                project.getDescription(),
                project.getTotalPrice(),
                project.getTotalDuration(),
                project.getStartDate(),
                project.getEndDate(),
                project.getEstimatedDeadline(),
                project.getStatus().name().toLowerCase());
        //abfa
    }

    public void updateProject(){

    }

    public void deleteProject(){

    }

    public void getProjectByStatus(){

    }
}
