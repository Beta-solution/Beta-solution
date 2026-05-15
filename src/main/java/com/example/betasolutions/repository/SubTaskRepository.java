package com.example.betasolutions.repository;

import com.example.betasolutions.model.SubTask;
import com.example.betasolutions.repository.rowmapper.SubTaskRowMapper;
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
        String sql = "SELECT * FROM Sub_Tasks WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new SubTaskRowMapper(), id);

    }

    public List<SubTask> getSubTaskByTaskId(int taskId){
        String sql = "SELECT * FROM Sub_Tasks WHERE task_id = ?";
        return jdbcTemplate.query(sql, new SubTaskRowMapper(), taskId);
    }

    public boolean createSubTask(SubTask subTask, int taskId){
        String sql = """
                INSERT INTO Sub_Tasks (name, description, duration, status,
                startDate, endDate, task_id) VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        return jdbcTemplate.update(sql,
                subTask.getName(),
                subTask.getDescription(),
                subTask.getDuration(),
                subTask.getStatus() != null ? subTask.getStatus().name() : null,
                subTask.getStartDate() != null ? Date.valueOf(subTask.getStartDate()) : null,
                subTask.getEndDate() != null ? Date.valueOf(subTask.getEndDate()) : null,
                taskId
                )>0;
    }

    public boolean updateSubTask(int id, SubTask subTask){
        String sql = """
            Update Sub_Tasks SET name = ?, description = ?, duration = ?, status = ?,
            startDate = ?, endDate = ? WHERE id = ?
            """;

        return jdbcTemplate.update(sql,
                subTask.getName(),
                subTask.getDescription(),
                subTask.getDuration(),
                subTask.getStatus() != null ? subTask.getStatus().name() : null,
                subTask.getStartDate() != null ? Date.valueOf(subTask.getStartDate()) : null,
                subTask.getEndDate() != null ? Date.valueOf(subTask.getEndDate()) : null,
                id) > 0;
    }

    public boolean deleteSubTask(int id){
        String sql = "DELETE FROM Sub_Tasks WHERE id = ?";
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
