package com.example.betasolutions.repository;

import com.example.betasolutions.exception.InvalidProfileException;
import com.example.betasolutions.exception.ProfileNotFoundException;
import com.example.betasolutions.model.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProfileRepository {
    private final JdbcTemplate jdbcTemplate;

    public ProfileRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate=jdbcTemplate;
    }

    public List<Profile> getAllProfiles(){
        String sql = "SELECT * FROM Profiles";

        return jdbcTemplate.query(sql, new ProfileRowMapper());
    }

    public Profile getProfileById(int id){
        String sql = "SELECT * FROM Profiles WHERE id = ?";

        try{
            return jdbcTemplate.queryForObject(sql, new ProfileRowMapper(), id);
        }catch (EmptyResultDataAccessException e){
            throw new ProfileNotFoundException("Profile with id " + id + " was not found");
        }
    }

    public Profile getProfileByUsername(String username, String password){
        String sql = "SELECT * FROM Profiles WHERE username = ? AND password = ?";

        try{
            return jdbcTemplate.queryForObject(sql, new ProfileRowMapper(), username, password);
        }catch (EmptyResultDataAccessException e){
            throw new ProfileNotFoundException("Invalid username or password");
        }
    }

    public boolean createProfile(Profile profile){
        validateProfile(profile);

        String sql = """
        INSERT INTO Profiles (name, role, username, password, email) VALUES (?, ?, ?, ?, ?)
        """;

        int rows = jdbcTemplate.update(sql,
                profile.getName(),
                profile.getRole() != null ? profile.getRole().name() : "JUNIOR",
                profile.getUsername(),
                profile.getPassword(),
                profile.getEmail()
        );
        return rows > 0;
    }

    public boolean updateProfile(Profile profile, int profileId){

        validateProfile(profile);

        String sql = "UPDATE Profiles SET name = ?, role = ?, username = ?, password = ?, email = ? WHERE id = ?";

        int rows = jdbcTemplate.update(sql,
                profile.getName(),
                profile.getRole().name(),
                profile.getUsername(),
                profile.getPassword(),
                profile.getEmail(),
                profileId
        );

        if (rows == 0){
            throw new ProfileNotFoundException("Profile with id " + profileId + " was not found");
        }

        return true;
    }

    public boolean deleteProfile(int profileId){
        getProfileById(profileId);

        jdbcTemplate.update("DELETE FROM Profiles_Skills WHERE profile_id = ?", profileId);
        jdbcTemplate.update("DELETE FROM Profiles_Projects WHERE profile_id = ?", profileId);
        jdbcTemplate.update("DELETE FROM Profiles_Tasks WHERE profile_id = ?", profileId);
        jdbcTemplate.update("DELETE FROM Profiles_Sub_Tasks WHERE profile_id = ?", profileId);

        jdbcTemplate.update("DELETE FROM Profiles WHERE id = ?", profileId);
        return true;
    }

    //Hjælpemetoder

    private void validateProfile(Profile profile) {
        if (profile.getName() == null || profile.getName().isBlank()) {
            throw new InvalidProfileException("Name cannot be empty");
        }
        if (profile.getUsername() == null || profile.getUsername().isBlank()) {
            throw new InvalidProfileException("Username cannot be empty");
        }
        if (profile.getPassword() == null || profile.getPassword().isBlank()) {
            throw new InvalidProfileException("Password cannot be empty");
        }
        if (profile.getEmail() == null || profile.getEmail().isBlank()) {
            throw new InvalidProfileException("Email cannot be empty");
        }
        if (profile.getRole() == null) {
            throw new InvalidProfileException("Role cannot be null");
        }
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

    public List<Profile> getProfilesNotInProject(int projectId) {
        String sql = """
        SELECT *
        FROM Profiles
        WHERE id NOT IN (
            SELECT profile_id
            FROM Profiles_Projects
            WHERE project_id = ?
        )
    """;

        return jdbcTemplate.query(sql, new ProfileRowMapper(), projectId);
    }

    public List<Profile> getProfilesBySubTaskId(int subTaskId) {
        String sql = """
        SELECT p.*
        FROM Profiles p
        JOIN Profiles_Sub_Tasks pst ON p.id = pst.profile_id
        WHERE pst.sub_task_id = ?
    """;

        return jdbcTemplate.query(sql, new ProfileRowMapper(), subTaskId);
    }

    public List<Profile> getProfilesNotInSubTask(int subTaskId) {
        String sql = """
        SELECT *
        FROM Profiles
        WHERE id NOT IN (
            SELECT profile_id
            FROM Profiles_Sub_Tasks
            WHERE sub_task_id = ?
        )
    """;

        return jdbcTemplate.query(sql, new ProfileRowMapper(), subTaskId);
    }
}
