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

    public void createSkill() {

    }

    public boolean updateSkill(int id, Skill skill) {
        String sql = "UPDATE skill SET name=? WHERE id=?";
        return jdbcTemplate.update(sql, skill.getName(), id) > 0;
    }

    public void deleteSkill() {

    }


}
