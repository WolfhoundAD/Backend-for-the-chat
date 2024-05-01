package dev.chat.service;

import dev.chat.entity.Profile;
import dev.chat.repository.ProfileRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;

    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public Profile getProfileById(Long profileId) {
        return profileRepository.findById(profileId).orElse(null);
    }

    public Profile createProfile(Profile profile) {
        // Дополнительные проверки, логика и т.д. могут быть добавлены здесь
        return profileRepository.save(profile);
    }
    public List<Profile> getAllProfilesForUser(Long userId) {
        return profileRepository.findByUserId(userId);
    }

}
