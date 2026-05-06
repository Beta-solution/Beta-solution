package com.example.betasolutions.repository;

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

    public void createSubTask(){

    }

    public void updateSubTask(){

    }

    public void deleteSubTask(){

    }
}
