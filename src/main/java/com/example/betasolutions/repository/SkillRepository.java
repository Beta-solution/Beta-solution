package com.example.betasolutions.repository;

import com.example.betasolutions.model.Skill;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class SkillRepository {
    private final JdbcTemplate jdbcTemplate;

    public SkillRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void getAllSkill() {

    }

    public void getSkillById() {

    }

    public void createSkill(Skill skill) {
        String sql = "INSERT INTO Skills (name) VALUES (?)";
        jdbcTemplate.update(sql, skill.getName());
    }

    public void updateSkill() {

    }

    public void deleteSkill() {

    }


}
