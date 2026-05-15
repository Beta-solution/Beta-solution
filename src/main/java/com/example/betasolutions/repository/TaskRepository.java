package com.example.betasolutions.repository;

import com.example.betasolutions.exception.TaskNotFoundException;
import com.example.betasolutions.model.Task;
import com.example.betasolutions.repository.rowmapper.TaskRowMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public class TaskRepository {
    private final JdbcTemplate jdbcTemplate;

    public TaskRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Task> getAllTask() {
        String sql = "SELECT * FROM Tasks";
        return jdbcTemplate.query(sql, new TaskRowMapper());
    }

    public Task getTaskById(int id) {
        String sql = "SELECT * FROM Tasks WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new TaskRowMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            throw new TaskNotFoundException("Task with id " + id + " not found");
        }
    }

    public List<Task> getTaskByProjectId(int projectId) {
        String sql = "SELECT * FROM Tasks WHERE project_Id = ?";
        return jdbcTemplate.query(sql, new TaskRowMapper(), projectId);
    }

    public void createTask(Task task, int projectId) {
        String sql = """
                INSERT  INTO tasks (name, description, status,
                startDate, endDate, project_id) VALUES (?, ?, ?, ?, ?, ?)
                """;
        jdbcTemplate.update(sql,
                task.getName(),
                task.getDescription(),
                task.getStatus() != null ? task.getStatus().name() : null,
                task.getStartDate() != null ? Date.valueOf(task.getStartDate()) : null,
                task.getEndDate() != null ? Date.valueOf(task.getEndDate()) : null,
                projectId);
    }

    public boolean updateTask(int id, Task task) {
        String sql = """
                UPDATE Tasks SET name = ?, description = ?, status = ?,
                startDate = ?, endDate = ? WHERE id = ?
                """;

        return jdbcTemplate.update(sql,
                task.getName(),
                task.getDescription(),
                task.getStatus() != null ? task.getStatus().name() : null,
                task.getStartDate() != null ? Date.valueOf(task.getStartDate()) : null,
                task.getEndDate() != null ? Date.valueOf(task.getEndDate()) : null,
                id) > 0;
    }

    public boolean deleteTask(int id){
        jdbcTemplate.update("DELETE FROM Sub_Tasks_Skills WHERE sub_task_id IN (SELECT id FROM Sub_Tasks WHERE task_id = ?)", id);
        jdbcTemplate.update("DELETE FROM Sub_Tasks WHERE task_id = ?", id);
        jdbcTemplate.update("DELETE FROM Tasks_Skills WHERE task_id = ?", id);
        return jdbcTemplate.update("DELETE FROM Tasks WHERE id = ?", id) > 0;
    }
}
