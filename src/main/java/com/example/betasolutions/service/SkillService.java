package com.example.betasolutions.service;

import com.example.betasolutions.model.Skill;
import com.example.betasolutions.repository.SkillRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkillService {
    private final SkillRepository skillRepository;
    private final JdbcTemplate jdbcTemplate;

    public SkillService(SkillRepository skillRepository, JdbcTemplate jdbcTemplate) {
        this.skillRepository = skillRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Skill> getAllSkill() {
        return skillRepository.getAllSkill();
    }

    public Skill getSkillById(int id) {
        return skillRepository.getSkillById(id);
    }

    public void createSkill(Skill skill) {
        skillRepository.createSkill(skill);
    }

    public boolean updateSkill(int id, Skill skill) {
        return skillRepository.updateSkill(id, skill);
    }

    public boolean deleteSkill(int id) {
        return skillRepository.deleteSkill(id);
    }
}
