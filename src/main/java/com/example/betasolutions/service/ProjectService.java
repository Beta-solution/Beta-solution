package com.example.betasolutions.service;

import com.example.betasolutions.enums.Status;
import com.example.betasolutions.model.Project;
import com.example.betasolutions.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository){
        this.projectRepository=projectRepository;
    }

    public List<Project> getAllProjects() {
        return projectRepository.getAllProjects();
    }

    public Project getProjectById(int id){
        return projectRepository.getProjectById(id);
    }

    public void createProject(){

    }

    public void updateProject(){

    }

    public void deleteProject(){

    }

    public List<Project> getProjectByStatus(Status status){
        return projectRepository.getProjectByStatus(status);
    }
}

