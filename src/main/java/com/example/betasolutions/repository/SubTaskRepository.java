package com.example.betasolutions.repository;

import com.example.betasolutions.model.SubTask;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class SubTaskRepository {
    private final JdbcTemplate jdbcTemplate;

    public SubTaskRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate=jdbcTemplate;
    }

    public void getAllSubTask(){

    }

    public void getSubTaskById(){

    }

    public void getSubTaskByTaskId(){

    }

    public void createSubTask(SubTask subTask, int taskId){
        String sql = """
                INSERT INTO Sub_Tasks (name, description, status,
                startDate, endDate, task_id) VALUES (?, ?, ?, ?, ?, ?)
                """;

        jdbcTemplate.update(sql,
                subTask.getName(),
                subTask.getDescription(),
                subTask.getDuration(),
                subTask.getStatus(),
                subTask.getStartDate(),
                subTask.getEndDate(),
                taskId
                );
    }
    public void updateSubTask(){

    }

    public void deleteSubTask(){

    }
}
