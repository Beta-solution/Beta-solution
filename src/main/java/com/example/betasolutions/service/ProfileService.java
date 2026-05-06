package com.example.betasolutions.service;

import com.example.betasolutions.model.Profile;
import com.example.betasolutions.repository.ProfileRepository;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {
    private final ProfileRepository profileRepository;

    public ProfileService (ProfileRepository profileRepository){
        this.profileRepository=profileRepository;
    }

    public void getAllProfiles(){

    }

    public void getProfileById(){

    }

    public void getProfileByUsername(){

    }

    public void createProfile(){

    }

    public void updateProfile(){

    }

    public void deleteProfile(){

    }

    public Profile login(String username, String password) {
        return profileRepository.getByUsername(username, password);
    }
}
