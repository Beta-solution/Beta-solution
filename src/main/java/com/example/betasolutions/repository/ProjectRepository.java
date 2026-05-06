package com.example.betasolutions.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ProjectRepository {
    private final JdbcTemplate jdbcTemplate;

    public ProjectRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate=jdbcTemplate;
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
