package com.example.betasolutions.repository;

import com.example.betasolutions.enums.Status;
import com.example.betasolutions.model.Project;
import com.example.betasolutions.model.Task;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TaskRowMapper implements RowMapper<Task> {

    @Override
    public Task mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Task(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getInt("duration"),
                Status.fromDb(rs.getString("status")),
                rs.getDate("startDate").toLocalDate(),
                rs.getDate("endDate").toLocalDate(),
                rs.getInt("project_id")
        );
    }
}