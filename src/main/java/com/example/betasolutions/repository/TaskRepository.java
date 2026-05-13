package com.example.betasolutions.repository;

import com.example.betasolutions.model.Task;
import com.example.betasolutions.repository.rowmapper.TaskRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
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
    }
    public Task getTaskById(int id){
        String sql = "SELECT * FROM Tasks WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new TaskRowMapper());
    }

    public List<Task> getTaskByProjectId(int projectId){
        String sql = "SELECT * FROM Tasks WHERE project_Id = ?";
        return jdbcTemplate.query(sql, new TaskRowMapper(), projectId);
    }

    public void createTask(Task task, int projectId){
        String sql = """
                INSERT  INTO tasks (name, description, status,
                startDate, endDate, project_id) VALUES (?, ?, ?, ?, ?, ?)
                """;
        jdbcTemplate.update(sql,
                task.getName(),
                task.getDescription(),
                task.getStatus(),
                Date.valueOf(task.getStartDate()),
                Date.valueOf(task.getEndDate()),
        projectId);
    }

    public boolean updateTask(int id, Task task){
        String sql = """
                UPDATE Tasks SET name = ?, description = ?, status = ?,
                startDate = ?, endDate = ? WHERE id = ?
                """;

        jdbcTemplate.update(sql,
                task.getName(),
                task.getDescription(),
                task.getStatus(),
                Date.valueOf(task.getStartDate()),
                Date.valueOf(task.getEndDate()),
                id);

        return jdbcTemplate.update(sql, task.getName(), id) > 0;
    }

    public boolean deleteTask(int id){
        String sql = "DELETE FROM Tasks WHERE id = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }
}
