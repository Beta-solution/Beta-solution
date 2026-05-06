package com.example.betasolutions.repository;

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

    public void updateSkill() {

    }

    public void deleteSkill() {

    }


}
