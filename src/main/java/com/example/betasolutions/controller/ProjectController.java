package com.example.betasolutions.controller;

import com.example.betasolutions.enums.Role;
import com.example.betasolutions.model.Profile;
import com.example.betasolutions.model.Project;
import com.example.betasolutions.service.CalculationService;
import com.example.betasolutions.service.ProfileService;
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
    private final ProfileService profileService;

    public ProjectController(ProjectService projectService, SkillService skillService, ProfileService profileService) {
        this.projectService = projectService;
        this.skillService = skillService;
        this.profileService = profileService;
    }

    @GetMapping("/projects")
    public String getAllProjects(Model model, HttpSession httpSession){
        if (!hasProfileAccess(httpSession)) {
            return "redirect:/unauthorized";
        }

        model.addAttribute("projects", projectService.getAllProjects());
        model.addAttribute("currentUser", httpSession.getAttribute("currentUser"));
        return "projects/index";
    }

    @GetMapping("/projects/{id}")
    public String getProjectById(@PathVariable int id, Model model, HttpSession httpSession){
        if (!hasProfileAccess(httpSession)) {
            return "redirect:/unauthorized";
        }

        model.addAttribute("project", projectService.getProjectById(id));
        model.addAttribute("totalDuration", projectService.getProjectDuration(id));
        model.addAttribute("estimatedPrice", projectService.getEstimatedPrice(id));
        model.addAttribute("currentUser", httpSession.getAttribute("currentUser"));
        return "projects/detail";
    }

    @GetMapping("/projects/create")
    public String showCreateForm(Model model, HttpSession httpSession){
        if (!hasProfileAccess(httpSession)) {
            return "redirect:/unauthorized";
        }

        model.addAttribute("project", new Project());
        return "projects/create";
    }

    @PostMapping("/projects/create")
    public String createProject(@ModelAttribute Project project, HttpSession httpSession){
        if (!hasProfileAccess(httpSession)) {
            return "redirect:/unauthorized";
        }

        projectService.createProject(project);
        return "redirect:/projects";
    }

    @GetMapping("/projects/{id}/edit")
    public String showEditForm(@PathVariable int id, Model model, HttpSession httpSession){
        if (!hasProfileAccess(httpSession)) {
            return "redirect:/unauthorized";
        }

        model.addAttribute("project", projectService.getProjectById(id));
        return "projects/edit";
    }

    @PostMapping("/projects/{id}/edit")
    public String updateProject(@PathVariable int id, @ModelAttribute Project project, HttpSession httpSession){
        if (!hasProfileAccess(httpSession)) {
            return "redirect:/unauthorized";
        }

        projectService.updateProject(id, project);
        return "redirect:/projects";
    }

    @PostMapping("/projects/{id}/delete")
    public String deleteProject(@PathVariable int id, HttpSession httpSession){
        if (!hasProfileAccess(httpSession)) {
            return "redirect:/unauthorized";
        }

        projectService.deleteProject(id);
        return "redirect:/projects";
    }

    @GetMapping("/projects/{projectId}/members")
    public String showAssignProfiles(@PathVariable int projectId,
                                     Model model,
                                     HttpSession httpSession) {

        if (!hasProfileAccess(httpSession)) {
            return "redirect:/unauthorized";
        }

        Project project = projectService.getProjectById(projectId);

        model.addAttribute("project", project);
        model.addAttribute("profiles", profileService.getAllProfiles());
        model.addAttribute("projectProfiles", projectService.getProfilesByProjectId(projectId));

        return "projects/members";
    }

    @PostMapping("/projects/{projectId}/members/{profileId}")
    public String addProfileToProject(@PathVariable int projectId,
                                      @PathVariable int profileId,
                                      HttpSession httpSession) {

        if (!hasProfileAccess(httpSession)) {
            return "redirect:/unauthorized";
        }

        projectService.addProfileToProject(projectId, profileId);

        return "redirect:/projects/" + projectId + "/members";
    }

    private boolean hasProfileAccess(HttpSession httpSession) {
        Profile loggedIn = (Profile) httpSession.getAttribute("profile");
        return loggedIn != null && loggedIn.getRole() != Role.JUNIOR;
    }
}
