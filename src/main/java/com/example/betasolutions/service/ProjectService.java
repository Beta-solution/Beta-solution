package com.example.betasolutions.service;

import com.example.betasolutions.repository.ProjectRepository;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository){
        this.projectRepository=projectRepository;
    }

    public void getAllProjects(){

    }

    public void getProjectById(){

    }

    public void createProject(){

    }

    public void updateProject(){

    }

    public void deleteProject(){

    }

    public void getProjectByStatus(){

    }
}

