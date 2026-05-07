package com.example.betasolutions.repository;

import com.example.betasolutions.model.Task;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TaskRepository {
    private final JdbcTemplate jdbcTemplate;

    public TaskRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate=jdbcTemplate;
    }

    public void getAllTask(){

    }

    public void getTaskById(){

    }

    public void getTaskByProjectId(){

    }

    public void createTask(){

    }

    public void updateTask(int id, Task task){
        String sql = """
                UPDATE Tasks SET name = ?, description = ?, duration = ?, status = ?,
                startDate = ?, endDate = ? WHERE id = ?
                """;

        jdbcTemplate.update(sql,
                task.getName(),
                task.getDescription(),
                task.getDuration(),
                task.getStatus(),
                task.getStartDate(),
                task.getEndDate(),
                id);
    } //abfa

    public void deleteTask(){

    }
}
