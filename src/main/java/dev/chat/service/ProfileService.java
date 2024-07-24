package dev.chat.service;

import dev.chat.dto.ProfileDTO;
import dev.chat.entity.Profile;
import dev.chat.mapper.ProfileMapper;
import dev.chat.repository.ProfileRepository;
import dev.chat.util.MinioUrlGenerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    private final ProfileMapper profileMapper;

    public ProfileService(ProfileRepository profileRepository, MinioService minioService, ProfileMapper profileMapper) {
        this.profileRepository = profileRepository;
        this.minioService = minioService;
        this.profileMapper = profileMapper;
    }

    public Profile getProfileById(Long profileId) {
        return profileRepository.findById(profileId).orElse(null);
    }

    public List<Profile> getAllProfilesForUser(Long userId) {
        return profileRepository.findProfilesByUserId(userId);
    }

    //todo транзакция на сохранение файла и работу с бд - протестировать
    @Transactional
    public ProfileDTO createProfile(ProfileDTO profileDTO, MultipartFile photoFile) throws IOException {
        Profile profile = profileMapper.profileDTOToProfile(profileDTO);

        if (photoFile != null && !photoFile.isEmpty()) {
            String photoFileName = UUID.randomUUID().toString() + StringUtils.cleanPath(photoFile.getOriginalFilename());
            try (InputStream photoInputStream = photoFile.getInputStream()) {
                minioService.uploadFile(photoInputStream, photoFileName, photoFile.getContentType());
                profile.setPhotoUrl(photoFileName);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Failed to upload photo to MinIO");
            }
        }

        profile = profileRepository.save(profile);
        ProfileDTO savedProfileDTO = profileMapper.profileToProfileDTO(profile);

        // Генерация полного URL для photoUrl
        String fullPhotoUrl = MinioUrlGenerator.generateMinioUrl(savedProfileDTO.getPhotoUrl());
        savedProfileDTO.setPhotoUrl(fullPhotoUrl);

        return savedProfileDTO;
    }

    public ProfileDTO createProfileWithoutPhoto(ProfileDTO profileDTO) {
        Profile profile = profileMapper.profileDTOToProfile(profileDTO);
        profile = profileRepository.save(profile);
        return profileMapper.profileToProfileDTO(profile);
    }

    public ProfileDTO getProfileByUserId(Long userId) {
        List<Profile> profiles = profileRepository.findProfilesByUserId(userId);
        if (profiles.isEmpty()) {
            return null;
        }
        Profile profile = profiles.get(0); // Предполагается, что у пользователя один профиль
        return profileMapper.profileToProfileDTO(profile);
    }

    public void deleteProfile(Long profileId) {
        profileRepository.deleteById(profileId);
    }
}
