package com.example.betasolutions.service;

import com.example.betasolutions.model.Profile;
import com.example.betasolutions.repository.ProfileRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileService {
    private final ProfileRepository profileRepository;

    public ProfileService (ProfileRepository profileRepository){
        this.profileRepository=profileRepository;
    }

    public List<Profile> getAllProfiles(){
        return profileRepository.getAllProfiles();
    }

    public Profile getProfileById(int id){
        return profileRepository.getProfileById(id);
    }

    public Profile getProfileByUsername(String username, String password){
        return profileRepository.getProfileByUsername(username, password);
    }

    public boolean createProfile(Profile profile){
        return profileRepository.createProfile(profile);
    }

    public boolean updateProfile(Profile profile, int profileId){
        return profileRepository.updateProfile(profile, profileId);
    }

    public boolean deleteProfile(int profileId){
        return profileRepository.deleteProfile(profileId);
    }

}
