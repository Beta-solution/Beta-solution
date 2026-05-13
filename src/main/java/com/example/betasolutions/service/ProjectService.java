package com.example.betasolutions.service;

import com.example.betasolutions.enums.Status;
import com.example.betasolutions.model.Profile;
import com.example.betasolutions.model.Project;
import com.example.betasolutions.model.SubTask;
import com.example.betasolutions.model.Task;
import com.example.betasolutions.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final TaskService taskService;
    private final CalculationService calculationService;

    public ProjectService(ProjectRepository projectRepository,
                          TaskService taskService,
                          CalculationService calculationService) {
        this.projectRepository = projectRepository;
        this.taskService = taskService;
        this.calculationService = calculationService;
    }

    public List<Project> getAllProjects() {
        return projectRepository.getAllProjects();
    }

    public Project getProjectById(int id) {
        return projectRepository.getProjectById(id);
    }

    public void createProject(Project project) {
        projectRepository.createProject(project);
    }

    public boolean updateProject(int id, Project project) {
        return projectRepository.updateProject(id, project);
    }

    public boolean deleteProject(int id) {
        return projectRepository.deleteProject(id);
    }

    public List<Project> getProjectByStatus(Status status) {
        return projectRepository.getProjectByStatus(status);
    }

    public boolean addProfileToProject(int profileId, int projectId) {
        return projectRepository.addProfileToProject(profileId, projectId);
    }

    public boolean removeProfileFromProject(int profileId, int projectId) {
        return projectRepository.removeProfileFromProject(profileId, projectId);
    }

    public BigDecimal getProjectDuration(int projectId) {

        List<Task> tasks = taskService.getTaskByProjectId(projectId);

        BigDecimal total = BigDecimal.ZERO;

        for (Task task : tasks) {

            total = total.add(
                    taskService.getTaskDuration(task.getId())
            );
        }

        return total;
    }

    public BigDecimal getEstimatedPrice(int projectId) {
        Project project = getProjectById(projectId);

        BigDecimal hours = getProjectDuration(projectId);

        return calculationService.calculateEstimatedPrice(
                hours,
                project.getHourlyRate()
        );
    }
}