package com.example.betasolutions.controller;

import com.example.betasolutions.model.Project;
import com.example.betasolutions.service.ProjectService;
import com.example.betasolutions.service.SkillService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProjectController {
    private final ProjectService projectService;
    private final SkillService skillService; //Skal bruges til at sætte skills på et project

    public ProjectController(ProjectService projectService, SkillService skillService) {
        this.projectService = projectService;
        this.skillService = skillService;
    }

    @GetMapping("/projects")
    public String getAllProjects(Model model, HttpSession httpSession){

    }

    @GetMapping("/projects/{id}")
    public String getProjectById(@PathVariable int id, Model model, HttpSession httpSession){

    }

    @GetMapping("/projects/create")
    public String showCreateForm(Model model, HttpSession httpSession){

    }

    @PostMapping("/projects/create")
    public String createProject(@ModelAttribute Project project, HttpSession httpSession){

    }

    @GetMapping("/projects/{id}/edit")
    public String showEditForm(@PathVariable int id, Model model, HttpSession httpSession){

    }

    @PostMapping("/projects/{id}/edit")
    public String updateProject(@PathVariable int id, @ModelAttribute Project project, HttpSession httpSession){

    }

    @PostMapping("/projects/{id}/delete")
    public String deleteProject(@PathVariable int id, HttpSession httpSession){

    }
//:)
}
