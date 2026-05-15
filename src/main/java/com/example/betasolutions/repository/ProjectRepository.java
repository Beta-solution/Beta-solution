package com.example.betasolutions.repository;

import com.example.betasolutions.enums.Status;
import com.example.betasolutions.exception.ProjectNotFoundException;
import com.example.betasolutions.model.Project;
import com.example.betasolutions.repository.rowmapper.ProjectRowMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public class ProjectRepository {
    private final JdbcTemplate jdbcTemplate;

    public ProjectRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate=jdbcTemplate;
    }

    public List<Project> getAllProjects(){
        String sql = "SELECT * FROM Projects";
        return jdbcTemplate.query(sql, new ProjectRowMapper());
    }

    public Project getProjectById(int id){
        String sql = "SELECT * FROM Projects WHERE id = ?";
            try {
                return jdbcTemplate.queryForObject(sql, new ProjectRowMapper(), id);
            } catch (EmptyResultDataAccessException e) {
                throw new ProjectNotFoundException("Project with id " + id + " not found");
            }
        }

    public void createProject(Project project){
        String sql = """
    INSERT INTO Projects (name, description, hourlyRate,
    startDate, endDate, estimatedDeadline, finalPrice, status)
    VALUES (?, ?, ?, ?, ?, ?, ?, ?)
    """;
        jdbcTemplate.update(sql,
                project.getName(),
                project.getDescription(),
                project.getHourlyRate(),
                project.getStartDate() != null ? Date.valueOf(project.getStartDate()) : null,
                project.getEndDate() != null ? Date.valueOf(project.getEndDate()) : null,
                project.getEstimatedDeadline() != null ? Date.valueOf(project.getEstimatedDeadline()) : null,
                project.getFinalPrice(),
                project.getStatus() != null ? project.getStatus().name().toLowerCase() : "todo");

    }

    public boolean updateProject(int id, Project project) {
        String sql = """
                UPDATE Projects SET name = ?, description = ?, hourlyRate = ?,
                startDate = ?, endDate = ?, estimatedDeadline = ?, finalPrice = ?, status = ? WHERE id = ?
                """;
        int rows = jdbcTemplate.update(sql,
                project.getName(),
                project.getDescription(),
                project.getHourlyRate(),
                project.getStartDate() != null ? Date.valueOf(project.getStartDate()) : null,
                project.getEndDate() != null ? Date.valueOf(project.getEndDate()) : null,
                project.getEstimatedDeadline() != null ? Date.valueOf(project.getEstimatedDeadline()) : null,
                project.getFinalPrice(),
                project.getStatus() != null ? project.getStatus().name() : null,
                id);
        if (rows == 0) throw new ProjectNotFoundException("Project with id " + id + " not found");
        return rows > 0;
    }

    public boolean deleteProject(int id){
        jdbcTemplate.update("DELETE FROM Sub_Tasks_Skills WHERE sub_task_id IN (SELECT id FROM Sub_Tasks WHERE task_id IN (SELECT id FROM Tasks WHERE project_id = ?))", id);
        jdbcTemplate.update("DELETE FROM Sub_Tasks WHERE task_id IN (SELECT id FROM Tasks WHERE project_id = ?)", id);
        jdbcTemplate.update("DELETE FROM Tasks_Skills WHERE task_id IN (SELECT id FROM Tasks WHERE project_id = ?)", id);
        jdbcTemplate.update("DELETE FROM Tasks WHERE project_id = ?", id);
        jdbcTemplate.update("DELETE FROM Project_Skills WHERE project_id = ?", id);
        jdbcTemplate.update("DELETE FROM Profiles_Projects WHERE project_id = ?", id);
        return jdbcTemplate.update("DELETE FROM Projects WHERE id = ?", id) > 0;
    }

    public List<Project> getProjectByStatus(Status status){
        String sql = "SELECT * FROM Projects WHERE status = ?";
        return jdbcTemplate.query(sql, new ProjectRowMapper(), status.name());
    }

    public boolean addProfileToProject(int profileId, int projectId) {
        String sql = "INSERT INTO Profiles_Projects (profile_id, project_id) VALUES (?, ?)";
        return jdbcTemplate.update(sql,
                profileId,
                projectId) > 0;
    }

    public boolean removeProfileFromProject(int profileId, int projectId) {
        String sql = "DELETE FROM Profiles_Projects WHERE profile_id = ? AND project_id = ?";
        return jdbcTemplate.update(sql, profileId, projectId) > 0;
    }
}
