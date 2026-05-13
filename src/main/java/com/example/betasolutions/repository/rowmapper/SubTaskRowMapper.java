package com.example.betasolutions.repository.rowmapper;

import com.example.betasolutions.enums.Status;
import com.example.betasolutions.model.SubTask;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SubTaskRowMapper implements RowMapper<SubTask> {

    @Override
    public SubTask mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new SubTask(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getBigDecimal("duration"),
                Status.fromDb(rs.getString("status")),
                rs.getDate("startDate").toLocalDate(),
                rs.getDate("endDate").toLocalDate(),
                rs.getInt("task_id")
        );
    }
}