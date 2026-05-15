package com.example.betasolutions.repository.rowmapper;

import com.example.betasolutions.model.TaskTemplate;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TaskTemplateRowMapper implements RowMapper<TaskTemplate> {
    @Override
    public TaskTemplate mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new TaskTemplate(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getInt("project_template_id")
        );
    }
}