package com.example.betasolutions.controller;

import com.example.betasolutions.enums.Role;
import com.example.betasolutions.model.Profile;
import com.example.betasolutions.service.ProfileService;
import com.example.betasolutions.service.SkillService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProfileController {
    private final ProfileService profileService;
    private final SkillService skillService; // Skal bruges til at sætte skills for en profil

    public ProfileController(ProfileService profileService, SkillService skillService) {
        this.profileService = profileService;
        this.skillService = skillService;
    }

    @GetMapping("/profiles")
    public String getAllProfiles(Model model, HttpSession httpSession){
        if (!hasProfileAccess(httpSession)) {
            return "redirect:/unauthorized";
        }

        model.addAttribute("profiles", profileService.getAllProfiles());
        return "profiles/index";
    }

    @GetMapping("/profiles/{id}")
    public String getProfileById(@PathVariable int id, Model model, HttpSession httpSession){
        if (!hasProfileAccess(httpSession)) {
            return "redirect:/unauthorized";
        }

        model.addAttribute("profile", profileService.getProfileById(id));
        return "profiles/profile";
    }

    @GetMapping("/profiles/create")
    public String showCreateForm(Model model, HttpSession httpSession){
        if (!hasProfileAccess(httpSession)) {
            return "redirect:/unauthorized";
        }
        model.addAttribute("profileForm", new Profile());
        model.addAttribute("isOwner", isOwner(httpSession));
        return "profiles/form";
    }

    @PostMapping("/profiles/create")
    public String createProfile(@ModelAttribute Profile profile, HttpSession httpSession){
        if (!hasProfileAccess(httpSession)) {
            return "redirect:/unauthorized";
        }

        if(profile.getRole() == Role.SENIOR && !isOwner(httpSession)){
            return "redirect:/unauthorized";
        }

        profileService.createProfile(profile);
        return "redirect:/profiles";
    }

    @GetMapping("/profiles/{id}/edit")
    public String showEditForm(@PathVariable int id, Model model, HttpSession httpSession){
        if (!hasProfileAccess(httpSession)) {
            return "redirect:/unauthorized";
        }
        model.addAttribute("profileEdit", profileService.getProfileById(id));
        return "profiles/edit";
    }

    @PostMapping("/profiles/{id}/edit")
    public String updateProfile(@PathVariable int id, @ModelAttribute Profile profile, HttpSession httpSession){
        if (!hasProfileAccess(httpSession)) {
            return "redirect:/unauthorized";
        }
        profileService.updateProfile(profile, id);
        return "redirect:/profiles";
    }

    @PostMapping("/profiles/{id}/delete")
    public String deleteProfile(@PathVariable int id, HttpSession httpSession){
        if (!hasProfileAccess(httpSession)) {
            return "redirect:/unauthorized";
        }

        Profile profileToDelete = profileService.getProfileById(id);

        if(profileToDelete.getRole() == Role.SENIOR && !isOwner(httpSession)){
            return "redirect:/unauthorized";
        }

        profileService.deleteProfile(id);
        return "redirect:/profiles";
    }

    //Hjælpemetoder

    private boolean hasProfileAccess(HttpSession httpSession) {
        Profile loggedIn = (Profile) httpSession.getAttribute("profile");
        return loggedIn != null && loggedIn.getRole() != Role.JUNIOR;
    }

    private Profile getLoggedIn(HttpSession httpSession) {
        return (Profile) httpSession.getAttribute("profile");
    }

    private boolean isOwner(HttpSession httpSession) {
        Profile loggedIn = getLoggedIn(httpSession);
        return loggedIn != null && loggedIn.getRole() == Role.OWNER;
    }
}
