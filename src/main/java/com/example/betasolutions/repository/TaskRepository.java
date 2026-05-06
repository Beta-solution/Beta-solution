package com.example.betasolutions.repository;

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

    public void updateTask(){

    }

    public void deleteTask(){

    }
}
