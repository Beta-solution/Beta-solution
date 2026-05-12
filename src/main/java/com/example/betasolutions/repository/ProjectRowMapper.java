package com.example.betasolutions.repository;

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
                rs.getDate("startDate").toLocalDate(),
                rs.getDate("endDate").toLocalDate(),
                rs.getDate("estimatedDeadline").toLocalDate(),
                rs.getBigDecimal("finalPrice"),
                Status.fromDb(rs.getString("status"))
        );
    }
}