package com.example.betasolutions.repository.rowmapper;

import com.example.betasolutions.model.SubTaskTemplate;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SubTaskTemplateRowMapper implements RowMapper<SubTaskTemplate> {
    @Override
    public SubTaskTemplate mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new SubTaskTemplate(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getBigDecimal("duration"),
                rs.getInt("task_template_id")
        );
    }
}