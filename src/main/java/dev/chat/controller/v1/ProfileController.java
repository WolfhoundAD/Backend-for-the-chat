package dev.chat.controller.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.chat.dto.ProfileDTO;
import dev.chat.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/profiles")
public class ProfileController {

    private final ProfileService profileService;

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ProfileDTO createProfile(
            @RequestParam("photo") MultipartFile photo,
            @RequestParam("profileDTO") String profileDTOJson) throws IOException {

        // Десериализуйте JSON-объект в ProfileDTO
        ObjectMapper objectMapper = new ObjectMapper();
        ProfileDTO profileDTO = objectMapper.readValue(profileDTOJson, ProfileDTO.class);

        return profileService.createProfile(profileDTO, photo);
    }

    @DeleteMapping("/{profileId}")
    public void deleteProfile(@PathVariable Long profileId) {
        profileService.deleteProfile(profileId);
    }
}