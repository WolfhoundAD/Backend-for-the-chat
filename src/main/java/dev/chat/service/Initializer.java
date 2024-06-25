package dev.chat.service;

import dev.chat.dto.ChatDto;
import dev.chat.dto.ProfileDTO;
import dev.chat.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import dev.chat.service.EmptyMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class Initializer {

    private final UserService userService;
    private final ProfileService profileService;
    private final ChatService chatService;

    @Autowired
    public Initializer(UserService userService, ProfileService profileService, ChatService chatService) {
        this.userService = userService;
        this.profileService = profileService;
        this.chatService = chatService;
    }

    @PostConstruct
    public void initialize() {
        // Create users
        List<UserDTO> userDTOList = createUsers();

        // Create profiles for each user
        List<ProfileDTO> profileDTOList = createProfiles(userDTOList);

        // Create a chat with participants from users
        //createChat(profileDTOList);
    }

    private List<UserDTO> createUsers() {
        List<UserDTO> userDTOList = new ArrayList<>();

        for (int i = 1; i <= 3; i++) {
            UserDTO userDTO = new UserDTO();
            userDTO.setUsername("user" + i);
            userDTO.setPassword("password" + i);
            userDTO.setRole("ADMIN");
            userDTOList.add(userService.createUser(userDTO));
        }


        return userDTOList;

    }

    private List<ProfileDTO> createProfiles(List<UserDTO> userDTOList) {
        List<ProfileDTO> profileDTOList = new ArrayList<>();

        for (UserDTO userDTO : userDTOList) {
            ProfileDTO profileDTO = new ProfileDTO();
            profileDTO.setUserID(userDTO.getUserID());
            profileDTO.setFullName("Full Name " + userDTO.getUsername());
            profileDTO.setPhotoUrl("profile_photo_" + userDTO.getUserID() + ".jpg");

            // Создание пустого MultipartFile
            MultipartFile emptyFile = new EmptyMultipartFile();

            try {
                profileDTOList.add(profileService.createProfile(profileDTO, emptyFile));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return profileDTOList;
    }

    /*private void createChat(List<ProfileDTO> profileDTOList) {
        List<Long> participantIds = new ArrayList<>();
        for (ProfileDTO profileDTO : profileDTOList) {
            participantIds.add(profileDTO.getUserID());
        }
        ChatDto chatDto = chatService.createChat("Group Chat", participantIds);
    }*/
}
