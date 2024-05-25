package dev.chat.service;

import dev.chat.dto.ProfileDTO;
import dev.chat.entity.Profile;
import dev.chat.entity.User;
import dev.chat.repository.ProfileRepository;
import org.springframework.stereotype.Service;
import dev.chat.service.MinioService;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final MinioService minioService;

    public ProfileService(ProfileRepository profileRepository, MinioService minioService) {
        this.profileRepository = profileRepository;
        this.minioService = minioService;
    }

    public Profile getProfileById(Long profileId) {
        return profileRepository.findById(profileId).orElse(null);
    }


    public List<Profile> getAllProfilesForUser(Long userId) {
        return profileRepository.findByUserId(userId);
    }


    //todo транзакция на сохранение файла и работу с бд
    public ProfileDTO createProfile(ProfileDTO profileDTO, MultipartFile photoFile) throws IOException {
        Profile profile = convertToProfile(profileDTO);

        if (photoFile != null && !photoFile.isEmpty()) {
            String photoFileName = UUID.randomUUID().toString() + StringUtils.cleanPath(photoFile.getOriginalFilename());
            try (InputStream photoInputStream = photoFile.getInputStream()) {
                minioService.uploadFile(photoInputStream, photoFileName, photoFile.getContentType());
                profile.setPhotoUrl(photoFileName); // Сохраняется путь к фотографии в базе данных
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Failed to upload photo to MinIO");
            }
        }

        profile = profileRepository.save(profile);
        return convertToProfileDTO(profile);
    }
    public void deleteProfile(Long profileId) {
        profileRepository.deleteById(profileId);
    }

    private Profile convertToProfile(ProfileDTO profileDTO) {
        Profile profile = new Profile();
        profile.setUser(new User());
        profile.getUser().setId(profileDTO.getUserID());
        profile.setFullName(profileDTO.getFullName());
        profile.setPhotoUrl(profileDTO.getPhotoUrl());
        return profile;
    }


    private ProfileDTO convertToProfileDTO(Profile profile) {
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setProfileID(profile.getProfileId());
        profileDTO.setUserID(profile.getUser().getId());
        profileDTO.setFullName(profile.getFullName());
        profileDTO.setPhotoUrl(profile.getPhotoUrl());
        return profileDTO;
    }
}
