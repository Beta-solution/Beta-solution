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

    public void createTask(Task task, int projectId){
        String sql = """
                INSERT  INTO tasks (name, description, duration, status,
                startDate, endDate, project_id) VALUES (?, ?, ?, ?, ?, ?)
                """;
        jdbcTemplate.update(sql,
                task.getName(),
                task.getDescription(),
                task.getDuration(),
                task.getStatus(),
                task.getStartDate(),
                task.getEndDate(),
        projectId);
    } //abfa

    public void updateTask(){

    }

    public boolean deleteTask(int id){
        String sql = "DELETE FROM Tasks WHERE id = ?";
        return jdbcTemplate.update(sql, id) > 0;
    } //abfa
}
