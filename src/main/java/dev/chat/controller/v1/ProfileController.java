package dev.chat.controller.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.chat.dto.ProfileDTO;
import dev.chat.service.ProfileService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/profiles")
@AllArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ProfileDTO createProfile(
            @RequestParam("photo") MultipartFile photo,
            @RequestParam("profileDTO") String profileDTOJson) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        ProfileDTO profileDTO = objectMapper.readValue(profileDTOJson, ProfileDTO.class);

        return profileService.createProfile(profileDTO, photo);
    }

    @DeleteMapping("/{profileId}")
    public void deleteProfile(@PathVariable Long profileId) {
        profileService.deleteProfile(profileId);
    }
}
