package com.example.betasolutions.service;

import com.example.betasolutions.model.Skill;
import com.example.betasolutions.repository.SkillRepository;
import org.springframework.stereotype.Service;

@Service
public class SkillService {
    private final SkillRepository skillRepository;

    public SkillService(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    public void getAllSkill() {

    }

    public void getSkillById() {

    }

    public void createSkill() {

    }

    public boolean updateSkill(int id, Skill skill) {
        return skillRepository.updateSkill(id, skill);
    }

    public void deleteSkill() {

    }
}
