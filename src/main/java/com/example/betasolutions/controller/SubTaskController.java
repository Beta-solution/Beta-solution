package com.example.betasolutions.controller;

import com.example.betasolutions.enums.Role;
import com.example.betasolutions.model.Profile;
import com.example.betasolutions.model.SubTask;
import com.example.betasolutions.service.ProfileService;
import com.example.betasolutions.service.ProjectService;
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
    private final ProjectService projectService;

    public SubTaskController(SubTaskService subTaskService, SkillService skillService, ProfileService profileService, ProjectService projectService) {
        this.subTaskService = subTaskService;
        this.skillService = skillService;
        this.profileService = profileService;
        this.projectService = projectService;
    }

    @GetMapping("/tasks/{taskId}/subtasks")
    public String getSubTasksByTask(@PathVariable int taskId,
                                    Model model,
                                    HttpSession httpSession) {

        if (!hasProfileAccess(httpSession)) {
            return "redirect:/unauthorized";
        }

        model.addAttribute("subTasks", subTaskService.getSubTaskByTaskId(taskId));
        model.addAttribute("taskId", taskId);
        model.addAttribute("currentUser", httpSession.getAttribute("currentUser"));

        return "subtasks/index";
    }

    @GetMapping("/tasks/{taskId}/subtasks/create")
    public String showCreateForm(@PathVariable int taskId,
                                 Model model,
                                 HttpSession httpSession) {

        if (!hasProfileAccess(httpSession)) {
            return "redirect:/unauthorized";
        }

        model.addAttribute("subTask", new SubTask());
        model.addAttribute("taskId", taskId);

        return "subtasks/create";
    }

    @PostMapping("/tasks/{taskId}/subtasks/create")
    public String createSubTask(@PathVariable int taskId,
                                @ModelAttribute SubTask subTask,
                                HttpSession httpSession) {

        if (!hasProfileAccess(httpSession)) {
            return "redirect:/unauthorized";
        }

        subTaskService.createSubTask(subTask, taskId);

        return "redirect:/tasks/" + taskId + "/subtasks";
    }

    @GetMapping("/tasks/{taskId}/subtasks/{id}/edit")
    public String showEditForm(@PathVariable int taskId,
                               @PathVariable int id,
                               Model model,
                               HttpSession httpSession) {

        if (!hasProfileAccess(httpSession)) {
            return "redirect:/unauthorized";
        }

        model.addAttribute("subTask", subTaskService.getSubTaskById(id));
        model.addAttribute("taskId", taskId);

        return "subtasks/edit";
    }

    @PostMapping("/tasks/{taskId}/subtasks/{id}/edit")
    public String updateSubTask(@PathVariable int taskId,
                                @PathVariable int id,
                                @ModelAttribute SubTask subTask,
                                HttpSession httpSession) {

        if (!hasProfileAccess(httpSession)) {
            return "redirect:/unauthorized";
        }

        subTaskService.updateSubTask(id, subTask);

        return "redirect:/tasks/" + taskId + "/subtasks";
    }

    @PostMapping("/tasks/{taskId}/subtasks/{id}/delete")
    public String deleteSubTask(@PathVariable int taskId,
                                @PathVariable int id,
                                HttpSession httpSession) {

        if (!hasProfileAccess(httpSession)) {
            return "redirect:/unauthorized";
        }

        subTaskService.deleteSubTask(id);

        return "redirect:/tasks/" + taskId + "/subtasks";
    }

    @GetMapping("/subtasks/{subTaskId}/members")
    public String showAssignProfiles(@PathVariable int subTaskId,
                                     Model model,
                                     HttpSession httpSession) {

        if (!hasProfileAccess(httpSession)) {
            return "redirect:/unauthorized";
        }

        model.addAttribute("subTask", subTaskService.getSubTaskById(subTaskId));
        model.addAttribute("profiles", profileService.getAvailableProfilesForSubTask(subTaskId));
        model.addAttribute("assignedProfiles", profileService.getProfilesBySubTaskId(subTaskId));

        return "subtasks/members";
    }

    @PostMapping("/subtasks/{subTaskId}/members/{profileId}")
    public String addProfileToSubTask(@PathVariable int subTaskId,
                                      @PathVariable int profileId,
                                      HttpSession httpSession) {

        if (!hasProfileAccess(httpSession)) {
            return "redirect:/unauthorized";
        }

        subTaskService.addProfileToSubTask(profileId, subTaskId);

        return "redirect:/subtasks/" + subTaskId + "/members";
    }

    @PostMapping("/subtasks/{subTaskId}/members/{profileId}/remove")
    public String removeProfileFromSubTask(@PathVariable int subTaskId,
                                           @PathVariable int profileId,
                                           HttpSession httpSession) {

        if (!hasProfileAccess(httpSession)) {
            return "redirect:/unauthorized";
        }

        subTaskService.removeProfileFromSubTask(profileId, subTaskId);

        return "redirect:/subtasks/" + subTaskId + "/members";
    }

    private boolean hasProfileAccess(HttpSession httpSession) {
        Profile loggedIn = (Profile) httpSession.getAttribute("profile");
        return loggedIn != null && loggedIn.getRole() != Role.JUNIOR;
    }
    }
