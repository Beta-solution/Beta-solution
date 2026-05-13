package com.example.betasolutions.service;

import com.example.betasolutions.model.Skill;
import com.example.betasolutions.repository.SkillRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SkillServiceTest {

    @Mock
    private SkillRepository skillRepository;

    @InjectMocks
    private SkillService skillService;

    // -------------------- getAllSkill --------------------

    @Test
    void getAllSkill_ReturnsList() {
        Skill skill = new Skill();
        skill.setId(1);
        skill.setName("Java");

        when(skillRepository.getAllSkill()).thenReturn(List.of(skill));

        List<Skill> result = skillService.getAllSkill();

        assertEquals(1, result.size());
        assertEquals("Java", result.getFirst().getName());
        verify(skillRepository).getAllSkill();
    }

    // -------------------- getSkillById --------------------

    @Test
    void getSkillById_ReturnsSkill() {
        Skill skill = new Skill();
        skill.setId(1);
        skill.setName("Java");

        when(skillRepository.getSkillById(1)).thenReturn(skill);

        Skill result = skillService.getSkillById(1);

        assertEquals("Java", result.getName());
        verify(skillRepository).getSkillById(1);
    }

    // -------------------- createSkill --------------------

    @Test
    void createSkill_CallsRepository() {
        Skill skill = new Skill();
        skill.setName("Python");

        skillService.createSkill(skill);

        verify(skillRepository).createSkill(skill);
    }

    // -------------------- updateSkill --------------------

    @Test
    void updateSkill_ReturnsTrue() {
        Skill skill = new Skill();
        skill.setName("Kotlin");

        when(skillRepository.updateSkill(1, skill)).thenReturn(true);

        boolean result = skillService.updateSkill(1, skill);

        assertTrue(result);
        verify(skillRepository).updateSkill(1, skill);
    }

    @Test
    void updateSkill_SkillNotFound_ReturnsFalse() {
        Skill skill = new Skill();
        skill.setName("Kotlin");

        when(skillRepository.updateSkill(999, skill)).thenReturn(false);

        boolean result = skillService.updateSkill(999, skill);

        assertFalse(result);
        verify(skillRepository).updateSkill(999, skill);
    }

    // -------------------- deleteSkill --------------------

    @Test
    void deleteSkill_ReturnsTrue() {
        when(skillRepository.deleteSkill(1)).thenReturn(true);

        boolean result = skillService.deleteSkill(1);

        assertTrue(result);
        verify(skillRepository).deleteSkill(1);
    }

    @Test
    void deleteSkill_SkillNotFound_ReturnsFalse() {
        when(skillRepository.deleteSkill(999)).thenReturn(false);

        boolean result = skillService.deleteSkill(999);

        assertFalse(result);
        verify(skillRepository).deleteSkill(999);
    }
}
