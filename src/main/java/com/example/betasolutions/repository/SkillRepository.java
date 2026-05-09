package com.example.betasolutions.repository;

import com.example.betasolutions.model.Skill;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SkillRepository {
    private final JdbcTemplate jdbcTemplate;

    public SkillRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Skill> getAllSkill() {
        String sql = "SELECT * FROM skill";
        return  jdbcTemplate.query(sql, new SkillRowMapper());
    }

    public Skill getSkillById(int id) {
    String sql = "SELECT * FROM skill WHERE id = ?";
    return jdbcTemplate.queryForObject(sql, new SkillRowMapper(), id);
    }

    public void createSkill() {

    }

    public void updateSkill() {

    }

    public void deleteSkill() {

    }


}
