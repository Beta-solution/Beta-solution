package com.example.betasolutions.service;

import com.example.betasolutions.model.*;
import com.example.betasolutions.repository.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class ProjectTemplateService {
    private final ProjectTemplateRepository projectTemplateRepository;
    private final TaskTemplateRepository taskTemplateRepository;
    private final SubTaskTemplateRepository subTaskTemplateRepository;
    private final TaskRepository taskRepository;
    private final SubTaskRepository subTaskRepository;

    public ProjectTemplateService(ProjectTemplateRepository projectTemplateRepository,
                                  TaskTemplateRepository taskTemplateRepository,
                                  SubTaskTemplateRepository subTaskTemplateRepository,
                                  TaskRepository taskRepository,
                                  SubTaskRepository subTaskRepository) {
        this.projectTemplateRepository = projectTemplateRepository;
        this.taskTemplateRepository = taskTemplateRepository;
        this.subTaskTemplateRepository = subTaskTemplateRepository;
        this.taskRepository = taskRepository;
        this.subTaskRepository = subTaskRepository;
    }

    public List<ProjectTemplate> getAllTemplates(){
        return projectTemplateRepository.getAllTemplates();
    }

    public ProjectTemplate getTemplateById(int id){
        ProjectTemplate template = projectTemplateRepository.getTemplateById(id);
        List<TaskTemplate> tasks = taskTemplateRepository.getTaskTemplatesByProjectTemplateId(id);

        for (TaskTemplate task : tasks){
            task.setSubTaskTemplates(
                    subTaskTemplateRepository.getSubTaskTemplatesByTaskTemplateId(task.getId())
            );
        }
        template.setTaskTemplates(tasks);
        return template;
    }

    public void saveAsTemplate(int projectId, Project project) {
        ProjectTemplate projectTemplate = new ProjectTemplate();
        projectTemplate.setName(project.getName());
        projectTemplate.setDescription(project.getDescription());
        projectTemplate.setHourlyRate(project.getHourlyRate());
        projectTemplate.setEstimatedDeadlineDays(project.getEstimatedDeadline());

        int projectTemplateId = projectTemplateRepository.createTemplate(projectTemplate);

        List<Task> tasks = taskRepository.getTaskByProjectId(projectId);

        for (Task task : tasks) {
            TaskTemplate taskTemplate = new TaskTemplate();
            taskTemplate.setName(task.getName());
            taskTemplate.setDescription(task.getDescription());
            taskTemplate.setProjectTemplateId(projectTemplateId);

            int taskTemplateId = taskTemplateRepository.createTaskTemplate(taskTemplate);

            List<SubTask> subTasks = subTaskRepository.getSubTaskByTaskId(task.getId());

            for (SubTask subTask : subTasks) {
                SubTaskTemplate subTaskTemplate = new SubTaskTemplate();
                subTaskTemplate.setName(subTask.getName());
                subTaskTemplate.setDescription(subTask.getDescription());
                subTaskTemplate.setDuration(subTask.getDuration());
                subTaskTemplate.setTaskTemplateId(taskTemplateId);

                subTaskTemplateRepository.createSubTaskTemplate(subTaskTemplate);
            }
        }
    }

    public boolean updateTemplate(int id, ProjectTemplate template) {
        return projectTemplateRepository.updateTemplate(id, template);
    }

    public boolean deleteTemplate(int id) {
        List<TaskTemplate> tasks = taskTemplateRepository.getTaskTemplatesByProjectTemplateId(id);
        for (TaskTemplate task : tasks) {
            subTaskTemplateRepository.deleteByTaskTemplateId(task.getId());
        }
        taskTemplateRepository.deleteByProjectTemplateId(id);
        return projectTemplateRepository.deleteTemplate(id);
    }
}
