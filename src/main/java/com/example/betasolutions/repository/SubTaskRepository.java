package com.example.betasolutions.repository;

import com.example.betasolutions.model.Profile;
import com.example.betasolutions.model.SubTask;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
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

    public void createSubTask(SubTask subTask, int taskId){
        String sql = """
                INSERT INTO Sub_Tasks (name, description, duration, status,
                startDate, endDate, task_id) VALUES (?, ?, ?, ?, ?, ?)
                """;

        jdbcTemplate.update(sql,
                subTask.getName(),
                subTask.getDescription(),
                subTask.getDuration(),
                subTask.getStatus(),
                Date.valueOf(subTask.getStartDate()),
                Date.valueOf(subTask.getEndDate()),
                taskId
                );
    }

    public boolean updateSubTask(int id, SubTask subTask){
        String sql = """
                Update Sub_Tasks SET name = ?, description = ?, duration = ?, status = ?,
                startDate = ?, endDate = ? WHERE id = ?
                """;

        jdbcTemplate.update(sql,
                subTask.getName(),
                subTask.getDescription(),
                subTask.getDuration(),
                subTask.getStatus(),
                Date.valueOf(subTask.getStartDate()),
                Date.valueOf(subTask.getEndDate()),
                id);

        return jdbcTemplate.update(sql, subTask.getName(), id) > 0;
    }

    public boolean deleteSubTask(int id){
        String sql = "DELETE FROM subtask WHERE id = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }

    public boolean addProfileToSubTask(int profileId, int subTaskId) {
        String sql = "INSERT INTO Profiles_Sub_Tasks (profile_id, sub_task_id) VALUES (?, ?)";

        return jdbcTemplate.update(sql, profileId, subTaskId) > 0;
    }

    public boolean removeProfileFromSubTask(int profileId, int subTaskId) {
        String sql = "DELETE FROM Profiles_Sub_Tasks WHERE profile_id = ? AND sub_task_id = ?";
        return jdbcTemplate.update(sql, profileId, subTaskId) > 0;
    }
}
