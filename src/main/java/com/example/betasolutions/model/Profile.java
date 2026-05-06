package com.example.betasolutions.model;

import com.example.betasolutions.enums.Role;

import java.util.List;

public class Profile {
    private int id;
    private String name;
    private Role role;
    private List<Skill> skills;
    private String username;
    private String password;
    private String email;

    public Profile() {}

    public Profile(int id, String name, Role role, List<Skill> skills,
                   String username, String password, String email) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.skills = skills;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    public List<Skill> getSkills() { return skills; }
    public void setSkills(List<Skill> skills) { this.skills = skills; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
