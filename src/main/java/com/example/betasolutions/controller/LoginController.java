package com.example.betasolutions.controller;

import com.example.betasolutions.exception.ProfileNotFoundException;
import com.example.betasolutions.model.Profile;
import com.example.betasolutions.service.ProfileService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
    private final ProfileService profileService;

    public LoginController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session) {

        try {
            Profile profile = profileService.getProfileByUsername(username, password);
                session.setAttribute("profile", profile);
                session.setMaxInactiveInterval(600); // 600 sekunder = 10 minutter
                return "redirect:/projects";
            } catch (ProfileNotFoundException e) {
                return "redirect:/login?error";
            }
        }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
