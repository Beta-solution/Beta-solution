package com.example.betasolutions.repository;

import com.example.betasolutions.model.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ProfileRepository {
    private final JdbcTemplate jdbcTemplate;

    public ProfileRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate=jdbcTemplate;
    }

    public void getAllProfiles(){

    }

    public void getProfileById(){

    }

    public void getProfileByUsername(){

    }

    public void createProfile(){

    }

    public void updateProfile(){

    }

    public void deleteProfile(){

    }

    public Profile getByUsername(String username, String password) {
        String sql = "SELECT * FROM Profiles WHERE username = ? AND password = ?";
        return jdbcTemplate.queryForObject(sql, new ProfileRowMapper(), username, password);
    }
}
