package com.example.betasolutions.controller;

import com.example.betasolutions.enums.Role;
import com.example.betasolutions.model.Profile;
import com.example.betasolutions.model.Project;
import com.example.betasolutions.model.ProjectTemplate;
import com.example.betasolutions.service.ProjectService;
import com.example.betasolutions.service.ProjectTemplateService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProjectTemplateController {
    private final ProjectTemplateService projectTemplateService;
    private final ProjectService projectService;

    public ProjectTemplateController(ProjectTemplateService projectTemplateService,
                                     ProjectService projectService) {
        this.projectTemplateService = projectTemplateService;
        this.projectService = projectService;
    }

    @GetMapping("/templates")
    public String getAllTemplates(Model model, HttpSession httpSession) {
        if (!hasAccess(httpSession)) return "redirect:/unauthorized";

        model.addAttribute("templates", projectTemplateService.getAllTemplates());
        return "templates/index";
    }

    @GetMapping("/templates/{id}")
    public String getTemplateById(@PathVariable int id, Model model, HttpSession httpSession) {
        if (!hasAccess(httpSession)) return "redirect:/unauthorized";

        model.addAttribute("template", projectTemplateService.getTemplateById(id));
        return "templates/detail";
    }

    @PostMapping("/projects/{id}/save-as-template")
    public String saveAsTemplate(@PathVariable int id, HttpSession httpSession) {
        if (!hasTemplateAccess(httpSession)) return "redirect:/unauthorized";

        Project project = projectService.getProjectById(id);
        projectTemplateService.saveAsTemplate(id, project);

        return "redirect:/templates";
    }

    @GetMapping("/templates/{id}/edit")
    public String showEditForm(@PathVariable int id, Model model, HttpSession httpSession) {
        if (!hasTemplateAccess(httpSession)) return "redirect:/unauthorized";

        model.addAttribute("template", projectTemplateService.getTemplateById(id));
        return "templates/edit";
    }

    @PostMapping("/templates/{id}/edit")
    public String updateTemplate(@PathVariable int id, @ModelAttribute ProjectTemplate template,
                                 HttpSession httpSession) {
        if (!hasTemplateAccess(httpSession)) return "redirect:/unauthorized";

        projectTemplateService.updateTemplate(id, template);
        return "redirect:/templates";
    }

    @PostMapping("/templates/{id}/delete")
    public String deleteTemplate(@PathVariable int id, HttpSession httpSession) {
        if (!hasTemplateAccess(httpSession)) return "redirect:/unauthorized";

        projectTemplateService.deleteTemplate(id);
        return "redirect:/templates";
    }

    //----------------------Hjælpemetoder---------------------

    private boolean hasTemplateAccess(HttpSession httpSession) {
        Profile loggedIn = (Profile) httpSession.getAttribute("profile");
        return loggedIn != null && loggedIn.getRole() != Role.JUNIOR;
    }

    private boolean hasAccess(HttpSession httpSession) {
        Profile loggedIn = (Profile) httpSession.getAttribute("profile");
        return loggedIn != null;
    }
}