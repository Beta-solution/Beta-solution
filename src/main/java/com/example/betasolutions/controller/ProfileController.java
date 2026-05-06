package com.example.betasolutions.controller;

import com.example.betasolutions.model.Profile;
import com.example.betasolutions.service.ProfileService;
import com.example.betasolutions.service.SkillService;
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
    public String getAllProfiles(Model model){

    }

    @GetMapping("/profiles/{id}")
    public String getProfileById(@PathVariable int id, Model model){

    }

    @GetMapping("/profiles/create")
    public String showCreateForm(Model model){

    }

    @PostMapping("/profiles/create")
    public String createProfile(@ModelAttribute Profile profile){

    }

    @GetMapping("/profiles/{id}/edit")
    public String showEditForm(@PathVariable int id, Model model){

    }

    @PostMapping("/profiles/{id}/edit")
    public String updateProfile(@PathVariable int id, @ModelAttribute Profile profile){

    }

    @PostMapping("/profiles/{id}/delete")
    public String deleteProfile(@PathVariable int id){

    }
}
