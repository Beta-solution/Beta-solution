package com.example.betasolutions.repository;

import com.example.betasolutions.model.SubTaskTemplate;
import com.example.betasolutions.repository.rowmapper.SubTaskTemplateRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class SubTaskTemplateRepository {
    private final JdbcTemplate jdbcTemplate;

    public SubTaskTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<SubTaskTemplate> getSubTaskTemplatesByTaskTemplateId(int taskTemplateId) {
        return jdbcTemplate.query(
                "SELECT * FROM Sub_Task_Templates WHERE task_template_id = ?",
                new SubTaskTemplateRowMapper(), taskTemplateId);
    }

    public void createSubTaskTemplate(SubTaskTemplate template) {
        String sql = """
                INSERT INTO Sub_Task_Templates (name, description, duration, task_template_id)
                VALUES (?, ?, ?, ?)
                """;
        jdbcTemplate.update(sql,
                template.getName(),
                template.getDescription(),
                template.getDuration(),
                template.getTaskTemplateId());
    }

    public void deleteByTaskTemplateId(int taskTemplateId) {
        jdbcTemplate.update(
                "DELETE FROM Sub_Task_Templates WHERE task_template_id = ?",
                taskTemplateId);
    }
}