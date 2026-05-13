package com.example.betasolutions.repository.rowmapper;

import com.example.betasolutions.model.ProjectTemplate;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProjectTemplateRowMapper implements RowMapper<ProjectTemplate> {
    @Override
    public ProjectTemplate mapRow(ResultSet rs, int rowNum) throws SQLException{
        return new ProjectTemplate(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getBigDecimal("hourlyRate"),
                rs.getDate("estimatedDeadlineDays").toLocalDate()
        );
    }
}
