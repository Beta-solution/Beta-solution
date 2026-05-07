package com.example.betasolutions.repository;

import com.example.betasolutions.model.SubTask;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SubTaskRepository {
    private final JdbcTemplate jdbcTemplate;

    public SubTaskRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate=jdbcTemplate;
    }

    public List<SubTask> getAllSubTask(){
        String sql = "SELECT * FROM Sub_Tasks";
        return jdbcTemplate.query(sql, new SubTaskRowMapper());
    }

    public SubTask getSubTaskById(int id){
        String sql = "SELECT * FROM Sub_Task WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new SubTaskRowMapper());
    }

    public List<SubTask> getSubTaskByTaskId(int taskId){
        String sql = "SELECT * FROM Sub_Task WHERE id = ?";
        return jdbcTemplate.query(sql, new SubTaskRowMapper(), taskId);
    }

    public void createSubTask(){

    }

    public void updateSubTask(){

    }

    public void deleteSubTask(){

    }
}
