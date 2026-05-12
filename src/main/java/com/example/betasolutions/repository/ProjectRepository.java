package com.example.betasolutions.repository;

import com.example.betasolutions.enums.Status;
import com.example.betasolutions.model.Profile;
import com.example.betasolutions.model.Project;
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
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new ProjectRowMapper());
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
                Date.valueOf(project.getStartDate()),
                Date.valueOf(project.getEndDate()),
                Date.valueOf(project.getEstimatedDeadline()),
                project.getFinalPrice(),
                project.getStatus().name().toLowerCase());

    }

    public boolean updateProject(int id, Project project) {
        String sql = """
                UPDATE Projects SET name = ?, description = ?, hourlyRate = ?,
                startDate = ?, endDate = ?, estimatedDeadline = ?, finalPrice = ?, status = ? WHERE id = ?
                """;
        jdbcTemplate.update(sql,
                project.getName(),
                project.getDescription(),
                project.getHourlyRate(),
                Date.valueOf(project.getStartDate()),
                Date.valueOf(project.getEndDate()),
                project.getEstimatedDeadline(),
                project.getFinalPrice(),
                project.getStatus(),
                id);

        return jdbcTemplate.update(sql, project.getName(), id) > 0;
    }

    public boolean deleteProject(int id){
        String sql = "DELETE FROM projects WHERE id = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }

    public List<Project> getProjectByStatus(Status status){
        String sql = "SELECT * FROM Projects WHERE status = ?";
        return jdbcTemplate.query(sql, new Object[]{status}, new ProjectRowMapper());
    }

    public boolean addProfileToProject(int profileId, int projectId) {
        String sql = "INSERT INTO Profiles_Projects (profile_id, project_id) VALUES (?, ?)";
        return jdbcTemplate.update(sql,
                profileId,
                projectId) > 0;
    }

    public List<Profile> getProfilesByProjectId(int projectId) {
        String sql = """
        SELECT p.*
        FROM Profiles p
        JOIN Profiles_Projects pp ON p.id = pp.profile_id
        WHERE pp.project_id = ?
    """;

        return jdbcTemplate.query(sql, new ProfileRowMapper(), projectId);
    }
}
