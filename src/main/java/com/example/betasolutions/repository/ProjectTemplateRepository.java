package com.example.betasolutions.repository;

import com.example.betasolutions.model.ProjectTemplate;
import com.example.betasolutions.repository.rowmapper.ProjectTemplateRowMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProjectTemplateRepository {
    private final JdbcTemplate jdbcTemplate;

    public ProjectTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ProjectTemplate> getAllTemplates() {
        return jdbcTemplate.query("SELECT * FROM Project_Templates", new ProjectTemplateRowMapper());
    }

    public ProjectTemplate getTemplateById(int id) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM Project_Templates WHERE id = ?",
                    new ProjectTemplateRowMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            throw new RuntimeException("Template med id " + id + " blev ikke fundet");
        }
    }

    public int createTemplate(ProjectTemplate template) {
        String sql = """
                INSERT INTO Project_Templates (name, description, hourlyRate, estimatedDeadlineDays)
                VALUES (?, ?, ?, ?)
                """;
        jdbcTemplate.update(sql,
                template.getName(),
                template.getDescription(),
                template.getHourlyRate(),
                template.getEstimatedDeadlineDays());

        return jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
    }


    public boolean deleteTemplate(int id) {
        return jdbcTemplate.update("DELETE FROM Project_Templates WHERE id = ?", id) > 0;
    }

    public boolean updateTemplate(int id, ProjectTemplate template) {
        String sql = """
                UPDATE Project_Templates SET name = ?, description = ?,hourlyRate = ?, 
                estimatedDeadlineDays = ? WHERE id = ?
                """;
        return jdbcTemplate.update(sql,
                template.getName(),
                template.getDescription(),
                template.getHourlyRate(),
                template.getEstimatedDeadlineDays(),
                id) > 0;
    }
}