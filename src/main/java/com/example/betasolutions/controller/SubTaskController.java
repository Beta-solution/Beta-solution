package com.example.betasolutions.controller;

import com.example.betasolutions.model.Profile;
import com.example.betasolutions.model.SubTask;
import com.example.betasolutions.service.ProfileService;
import com.example.betasolutions.service.SkillService;
import com.example.betasolutions.service.SubTaskService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SubTaskController {
    private final SubTaskService subTaskService;
    private final SkillService skillService;
    private final ProfileService profileService;

    public SubTaskController(SubTaskService subTaskService, SkillService skillService, ProfileService profileService) {
        this.subTaskService = subTaskService;
        this.skillService = skillService;
        this.profileService = profileService;
    }

    @GetMapping("/tasks/{taskId}/subtasks")
    public String getSubTasksByTask(@PathVariable int taskId, Model model, HttpSession httpSession){
        Profile currentUser = (Profile) httpSession.getAttribute("currentUser");
        if (currentUser == null) return "redirect:/login";

        model.addAttribute("subTasks", subTaskService.getSubTaskByTaskId(taskId));
        model.addAttribute("taskId", taskId);
        model.addAttribute("currentUser", currentUser);
        return "subtasks/index";
    }

    @GetMapping("/tasks/{taskId}/subtasks/create")
    public String showCreateForm(@PathVariable int taskId, Model model){

    }

    @PostMapping("/tasks/{taskId}/subtasks/create")
    public String createSubTask(@PathVariable int taskId, @ModelAttribute SubTask subTask){

    }

    @GetMapping("/tasks/{taskId}/subtasks/{id}/edit")
    public String showEditForm(@PathVariable int taskId, @PathVariable int id, Model model){

    }

    @PostMapping("/tasks/{taskId}/subtasks/{id}/edit")
    public String updateSubTask(@PathVariable int id, @ModelAttribute SubTask subTask){

    }

    @PostMapping("/tasks/{taskId}/subtasks/{id}/delete")
    public String deleteSubTask(@PathVariable int id){

    }
}
