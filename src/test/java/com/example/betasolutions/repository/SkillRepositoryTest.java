package com.example.betasolutions.repository;

import com.example.betasolutions.model.Skill;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = {"/schema.sql", "/data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class SkillRepositoryTest {

    @Autowired
    SkillRepository skillRepository;

    // -------------------- getAllSkill --------------------

    @Test
    void getAllSkill_ReturnsList() {
        List<Skill> result = skillRepository.getAllSkill();
        assertEquals(3, result.size());
    }

    // -------------------- getSkillById --------------------

    @Test
    void getSkillById_SkillExists_ReturnsSkill() {
        Skill result = skillRepository.getSkillById(1);
        assertEquals("Java", result.getName());
    }

    @Test
    void getSkillById_SkillNotFound_ThrowsException() {
        assertThrows(Exception.class, () -> skillRepository.getSkillById(999));
    }

    // -------------------- createSkill --------------------

    @Test
    void createSkill_ValidSkill_IncreasesCount() {
        Skill skill = new Skill();
        skill.setName("Python");

        skillRepository.createSkill(skill);

        assertEquals(4, skillRepository.getAllSkill().size());
    }

    // -------------------- updateSkill --------------------

    @Test
    void updateSkill_ValidSkill_ReturnsTrue() {
        Skill updated = new Skill();
        updated.setName("Kotlin");

        boolean result = skillRepository.updateSkill(1, updated);

        assertTrue(result);
        assertEquals("Kotlin", skillRepository.getSkillById(1).getName());
    }

    @Test
    void updateSkill_SkillNotFound_ReturnsFalse() {
        Skill updated = new Skill();
        updated.setName("Kotlin");

        boolean result = skillRepository.updateSkill(999, updated);

        assertFalse(result);
    }

    // -------------------- deleteSkill --------------------

    @Test
    void deleteSkill_SkillExists_ReturnsTrue() {
        boolean result = skillRepository.deleteSkill(1);
        assertTrue(result);
    }

    @Test
    void deleteSkill_SkillNotFound_ReturnsFalse() {
        boolean result = skillRepository.deleteSkill(999);
        assertFalse(result);
    }
}
