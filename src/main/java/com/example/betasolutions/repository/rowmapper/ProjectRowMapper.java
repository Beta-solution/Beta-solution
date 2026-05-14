package com.example.betasolutions.repository.rowmapper;

import com.example.betasolutions.enums.Status;
import com.example.betasolutions.model.Project;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProjectRowMapper implements RowMapper<Project> {

    @Override
    public Project mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Project(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getBigDecimal("hourlyRate"),
                rs.getDate("startDate") != null ?
                        rs.getDate("startDate").toLocalDate() : null,
                rs.getDate("endDate") != null ?
                        rs.getDate("endDate").toLocalDate() : null,
                rs.getDate("estimatedDeadline") != null ?
                        rs.getDate("estimatedDeadline").toLocalDate() : null,
                rs.getBigDecimal("finalPrice"),
                Status.fromDb(rs.getString("status"))
        );
    }
}