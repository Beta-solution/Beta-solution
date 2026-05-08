package com.example.betasolutions.controller;

import com.example.betasolutions.model.Profile;
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
        Profile currentUser = (Profile) httpSession.getAttribute("currentUser");
        if (currentUser == null) return "redirect:/login";

        model.addAttribute("projects", projectService.getAllProjects());
        model.addAttribute("currentUser", currentUser);
        return "projects/index";
    } //abfa

    @GetMapping("/projects/{id}")
    public String getProjectById(@PathVariable int id, Model model, HttpSession httpSession){
        Profile currentUser = (Profile) httpSession.getAttribute("currentUser");
        if (currentUser == null) return "redirect:/login";

        model.addAttribute("project", projectService.getProjectById(id));
        model.addAttribute("currentUser", currentUser);
        return "projects/detail";
    } //abfa

    @GetMapping("/projects/create")
    public String showCreateForm(Model model, HttpSession httpSession){
        Profile currentUser = (Profile) httpSession.getAttribute("currentUser");
        if (currentUser == null) return "redirect:/login";

        model.addAttribute("project", new Project());
        return "projects/create";
    } //abfa

    @PostMapping("/projects/create")
    public String createProject(@ModelAttribute Project project, HttpSession httpSession){
        Profile currentUser = (Profile) httpSession.getAttribute("currentUser");
        if (currentUser == null) return "redirect:/login";

        projectService.createProject(project);
        return "redirect:/projects";
    } //abfa

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
