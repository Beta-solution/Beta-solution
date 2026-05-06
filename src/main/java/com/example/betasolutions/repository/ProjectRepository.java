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

    public void getAllProjects(){

    }

    public void getProjectById(){

    }

    public void createProject(){

    }

    public void updateProject(int id, Project project) {
        String sql = """
                UPDATE Projects SET name = ?, description = ?, price = ?, totalduration = ?,
                startDate = ?, endDate = ?, estimatedDeadline = ?, status = ? WHERE id = ?
                """;
        jdbcTemplate.update(sql,
                project.getName(),
                project.getDescription(),
                project.getTotalPrice(),
                project.getTotalDuration(),
                project.getStartDate(),
                project.getEndDate(),
                project.getEstimatedDeadline(),
                project.getStatus(),
                id); //abfa
    }

    public void deleteProject(){

    }

    public void getProjectByStatus(){

    }
}
