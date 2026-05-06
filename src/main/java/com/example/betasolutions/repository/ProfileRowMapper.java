package com.example.betasolutions.repository;

import com.example.betasolutions.enums.Role;
import com.example.betasolutions.model.Profile;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfileRowMapper implements RowMapper<Profile> {

    @Override
    public Profile mapRow(ResultSet rs, int rowNum) throws SQLException {
        Profile profile = new Profile();
        profile.setId(rs.getInt("id"));
        profile.setName(rs.getString("name"));
        profile.setUsername(rs.getString("username"));
        profile.setPassword(rs.getString("password"));
        profile.setEmail(rs.getString("email"));
        profile.setRole(Role.fromDb(rs.getString("role")));
        return profile;
    }
}
