package com.example.betasolutions.repository;

import com.example.betasolutions.model.ProjectTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = {"/schema.sql", "/data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class ProjectTemplateRepositoryTest {

    @Autowired
    ProjectTemplateRepository projectTemplateRepository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    // -------------------- getAllTemplates --------------------

    @Test
    void getAllTemplates_ReturnsList() {
        List<ProjectTemplate> result = projectTemplateRepository.getAllTemplates();
        assertEquals(2, result.size());
    }

    // -------------------- getTemplateById --------------------

    @Test
    void getTemplateById_TemplateExists_ReturnsTemplate() {
        ProjectTemplate result = projectTemplateRepository.getTemplateById(1);
        assertEquals("Betalingsløsning", result.getName());
        assertEquals(LocalDate.of(2026, 3, 31), result.getEstimatedDeadlineDays());;
    }

    @Test
    void getTemplateById_TemplateNotFound_ThrowsException() {
        assertThrows(RuntimeException.class, () -> projectTemplateRepository.getTemplateById(999));
    }

    // -------------------- createTemplate --------------------

    @Test
    void createTemplate_ValidTemplate_ReturnsId() {
        ProjectTemplate template = new ProjectTemplate();
        template.setName("Ny skabelon");
        template.setDescription("Beskrivelse");
        template.setHourlyRate(new BigDecimal("750.00"));
        template.setEstimatedDeadlineDays(LocalDate.of(2026, 6, 1));

        int id = projectTemplateRepository.createTemplate(template);

        assertTrue(id > 0);
        assertEquals(3, projectTemplateRepository.getAllTemplates().size());
    }

    @Test
    void createTemplate_TemplateIsSaved_CanBeRetrieved() {
        ProjectTemplate template = new ProjectTemplate();
        template.setName("Ny skabelon");
        template.setDescription("Beskrivelse");
        template.setHourlyRate(new BigDecimal("750.00"));
        template.setEstimatedDeadlineDays(LocalDate.of(2026, 6, 1));

        int id = projectTemplateRepository.createTemplate(template);
        ProjectTemplate saved = projectTemplateRepository.getTemplateById(id);

        assertEquals("Ny skabelon", saved.getName());
        assertEquals(LocalDate.of(2026, 6, 1), saved.getEstimatedDeadlineDays());
    }

    // -------------------- updateTemplate --------------------

    @Test
    void updateTemplate_ValidTemplate_ReturnsTrue() {
        ProjectTemplate updated = new ProjectTemplate();
        updated.setName("Opdateret skabelon");
        updated.setDescription("Ny beskrivelse");
        updated.setHourlyRate(new BigDecimal("800.00"));
        updated.setEstimatedDeadlineDays(LocalDate.of(2026, 6, 1));

        boolean result = projectTemplateRepository.updateTemplate(1, updated);

        assertTrue(result);
        assertEquals("Opdateret skabelon", projectTemplateRepository.getTemplateById(1).getName());
    }

    @Test
    void updateTemplate_TemplateNotFound_ReturnsFalse() {
        ProjectTemplate updated = new ProjectTemplate();
        updated.setName("Opdateret skabelon");
        updated.setDescription("Ny beskrivelse");
        updated.setHourlyRate(new BigDecimal("800.00"));
        updated.setEstimatedDeadlineDays(LocalDate.of(2026, 6, 1));

        boolean result = projectTemplateRepository.updateTemplate(999, updated);

        assertFalse(result);
    }

    // -------------------- deleteTemplate --------------------

    @Test
    void deleteTemplate_TemplateExists_ReturnsTrue() {
        jdbcTemplate.update("DELETE FROM Sub_Task_Templates");
        jdbcTemplate.update("DELETE FROM Task_Templates");

        boolean result = projectTemplateRepository.deleteTemplate(1);

        assertTrue(result);
        assertThrows(RuntimeException.class, () -> projectTemplateRepository.getTemplateById(1));
    }

    @Test
    void deleteTemplate_TemplateNotFound_ReturnsFalse() {
        boolean result = projectTemplateRepository.deleteTemplate(999);

        assertFalse(result);
    }
}