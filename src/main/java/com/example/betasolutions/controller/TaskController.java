package com.example.betasolutions.controller;

import com.example.betasolutions.model.Profile;
import com.example.betasolutions.model.Task;
import com.example.betasolutions.service.ProfileService;
import com.example.betasolutions.service.SkillService;
import com.example.betasolutions.service.TaskService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TaskController {
    private final TaskService taskService;
    private final SkillService skillService;
    private final ProfileService profileService; //Denne skal bruges, hvis man vil tildele en person til en task

    public TaskController(TaskService taskService, SkillService skillService, ProfileService profileService) {
        this.taskService = taskService;
        this.skillService = skillService;
        this.profileService = profileService;
    }

    @GetMapping("/projects/{projectId}/tasks")
    public String getTasksByProject(@PathVariable int projectId, Model model, HttpSession httpSession){
        Profile currentUser = (Profile) httpSession.getAttribute("currentUser");
        if (currentUser == null) return "redirect:/login";

        model.addAttribute("tasks", taskService.getTaskByProjectId(projectId));
        model.addAttribute("projectId", projectId);
        model.addAttribute("currentUser", currentUser);
        return "tasks/index";
    } //abfa

    @GetMapping("/projects/{projectId}/tasks/create")
    public String showCreateForm(@PathVariable int projectId, Model model){

    }

    @PostMapping("/projects/{projectId}/tasks/create")
    public String createTask(@PathVariable int projectId, @ModelAttribute Task task){

    }

    @GetMapping("/projects/{projectId}/tasks/{id}/edit")
    public String showEditForm(@PathVariable int projectId, @PathVariable int id, Model model){

    }

    @PostMapping("/projects/{projectId}/tasks/{id}/edit")
    public String updateTask(@PathVariable int id, @ModelAttribute Task task){

    }

    @PostMapping("/projects/{projectId}/tasks/{id}/delete")
    public String deleteTask(@PathVariable int id){

    }
}
