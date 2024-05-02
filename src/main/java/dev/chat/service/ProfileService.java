package dev.chat.service;

import dev.chat.dto.ProfileDTO;
import dev.chat.entity.Profile;
import dev.chat.entity.User;
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


    public List<Profile> getAllProfilesForUser(Long userId) {
        return profileRepository.findByUserId(userId);
    }
    public ProfileDTO createProfile(ProfileDTO profileDTO) {
        Profile profile = convertToProfile(profileDTO);
        profile = profileRepository.save(profile);
        return convertToProfileDTO(profile);
    }

    public void deleteProfile(Long profileId) {
        profileRepository.deleteById(profileId);
    }

    private Profile convertToProfile(ProfileDTO profileDTO) {
        Profile profile = new Profile();
        profile.setUser(new User()); // используем конструктор без аргументов
        profile.getUser().setId(profileDTO.getUserID()); // устанавливаем id
        profile.setFullName(profileDTO.getFullName());
        profile.setPhoto(profileDTO.getPhoto());
        return profile;
    }


    private ProfileDTO convertToProfileDTO(Profile profile) {
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setProfileID(profile.getProfileId());
        profileDTO.setUserID(profile.getUser().getId());
        profileDTO.setFullName(profile.getFullName());
        profileDTO.setPhoto(profile.getPhoto());
        return profileDTO;
    }
}
