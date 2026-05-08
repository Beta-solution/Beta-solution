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

    public void createSubTask(){

    }

    public boolean updateSubTask(int id, SubTask subTask){
        String sql = """
                Update Sub_Tasks SET name = ?, dscription = +, duration = ?, status = ?,
                startDate = ?, endDate = ? WHERE id = ?
                """;

        jdbcTemplate.update(sql,
                subTask.getName(),
                subTask.getDescription(),
                subTask.getDuration(),
                subTask.getStatus(),
                subTask.getStartDate(),
                subTask.getEndDate(),
                id);

        return jdbcTemplate.update(sql, subTask.getName(), id) > 0;
    }

    public void deleteSubTask(){

    }
}
