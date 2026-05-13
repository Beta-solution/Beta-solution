package com.example.betasolutions.repository.rowmapper;

import com.example.betasolutions.model.Skill;
import org.springframework.jdbc.core.RowMapper;


import java.sql.ResultSet;
import java.sql.SQLException;

public class SkillRowMapper implements RowMapper<Skill> {

    @Override
    public Skill mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Skill(
                rs.getInt("id"),
                rs.getString("name")
        );
    }
}
