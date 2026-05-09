package com.example.betasolutions.service;

import com.example.betasolutions.model.Skill;
import com.example.betasolutions.repository.SkillRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkillService {
    private final SkillRepository skillRepository;

    public SkillService(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    public List<Skill> getAllSkill() {
        return skillRepository.getAllSkill();
    }

    public Skill getSkillById(int id) {
        return skillRepository.getSkillById(id);
    }

    public void createSkill() {

    }

    public void updateSkill() {

    }

    public void deleteSkill() {

    }
}
