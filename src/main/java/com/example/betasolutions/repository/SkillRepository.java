package com.example.betasolutions.repository;

import com.example.betasolutions.model.Skill;
import com.example.betasolutions.repository.rowmapper.SkillRowMapper;
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
        String sql = "SELECT * FROM Skills";
        return  jdbcTemplate.query(sql, new SkillRowMapper());
    }

    public Skill getSkillById(int id) {
    String sql = "SELECT * FROM Skills WHERE id = ?";
    return jdbcTemplate.queryForObject(sql, new SkillRowMapper(), id);
    }

    public void createSkill(Skill skill) {
        String sql = "INSERT INTO Skills (name) VALUES (?)";
        jdbcTemplate.update(sql, skill.getName());
    }

    public boolean updateSkill(int id, Skill skill) {
        String sql = "UPDATE Skills SET name=? WHERE id=?";
        return jdbcTemplate.update(sql, skill.getName(), id) > 0;
    }

    public boolean deleteSkill(int id) {
        String sql = "DELETE FROM Skills WHERE id = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }


}
