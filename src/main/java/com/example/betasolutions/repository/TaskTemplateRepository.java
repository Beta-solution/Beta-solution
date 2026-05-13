package com.example.betasolutions.repository;

import com.example.betasolutions.model.TaskTemplate;
import com.example.betasolutions.repository.rowmapper.TaskTemplateRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TaskTemplateRepository {
    private final JdbcTemplate jdbcTemplate;

    public TaskTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<TaskTemplate> getTaskTemplatesByProjectTemplateId(int projectTemplateId) {
        return jdbcTemplate.query(
                "SELECT * FROM Task_Templates WHERE project_template_id = ?",
                new TaskTemplateRowMapper(), projectTemplateId);
    }

    public int createTaskTemplate(TaskTemplate template) {
        String sql = """
            INSERT INTO Task_Templates (name, description, duration, project_template_id)
            VALUES (?, ?, ?, ?)
            """;
        jdbcTemplate.update(sql,
                template.getName(),
                template.getDescription(),
                template.getProjectTemplateId());

        return jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
    }

    public boolean deleteByProjectTemplateId(int projectTemplateId) {
        return jdbcTemplate.update(
                "DELETE FROM Task_Templates WHERE project_template_id = ?",
                projectTemplateId) > 0;
    }
}