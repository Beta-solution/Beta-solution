package com.example.betasolutions.repository;

import com.example.betasolutions.model.Task;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TaskRepository {
    private final JdbcTemplate jdbcTemplate;

    public TaskRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate=jdbcTemplate;
    }

    public List<Task> getAllTask(){
        String sql = "SELECT * FROM Tasks";
        return jdbcTemplate.query(sql, new TaskRowMapper());
    }//abfa

    public Task getTaskById(int id){
        String sql = "SELECT * FROM Tasks WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new TaskRowMapper());
    }//abfa

    public List<Task> getTaskByProjectId(int projectId){
        String sql = "SELECT * FROM Tasks WHERE project_Id = ?";
        return jdbcTemplate.query(sql, new TaskRowMapper(), projectId);
    }//abfa

    public void createTask(){

    }

    public void updateTask(){

    }

    public void deleteTask(){

    }
}
