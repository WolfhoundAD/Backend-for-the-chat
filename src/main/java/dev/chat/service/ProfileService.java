package dev.chat.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import dev.chat.dto.ProfileDTO;
import dev.chat.entity.Profile;
import dev.chat.entity.User;
import dev.chat.repository.ProfileRepository;
import dev.chat.service.MinioService;
import dev.chat.mapper.ProfileMapper;

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

    //todo транзакция на сохранение файла и работу с бд
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
        return profileMapper.profileToProfileDTO(profile);
    }

    public void deleteProfile(Long profileId) {
        profileRepository.deleteById(profileId);
    }
}
