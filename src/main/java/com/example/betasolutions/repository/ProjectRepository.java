package com.example.betasolutions.repository;

import com.example.betasolutions.enums.Status;
import com.example.betasolutions.model.Project;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProjectRepository {
    private final JdbcTemplate jdbcTemplate;

    public ProjectRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate=jdbcTemplate;
    }

    public List<Project> getAllProjects(){
        String sql = "SELECT * FROM Projects";
        return jdbcTemplate.query(sql, new ProjectRowMapper());
    } //abfa

    public Project getProjectById(int id){
        String sql = "SELECT * FROM Projects WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new ProjectRowMapper());
    } //abfa

    public void createProject(){

    }

    public void updateProject(){

    }

    public void deleteProject(){

    }

    public List<Project> getProjectByStatus(Status status){
        String sql = "SELECT * FROM Projects WHERE status = ?";
        return jdbcTemplate.query(sql, new Object[]{status}, new ProjectRowMapper());
    } //abfa
}
