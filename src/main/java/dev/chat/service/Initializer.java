package dev.chat.service;

import dev.chat.dto.ProfileDTO;
import dev.chat.dto.UserDTO;
import dev.chat.entity.Chat;
import dev.chat.entity.Profile;
import dev.chat.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
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
        createChat(profileDTOList);
    }

    private List<UserDTO> createUsers() {
        List<UserDTO> userDTOList = new ArrayList<>();

        // Create and save users
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

        // Create and save profiles for each user
        for (UserDTO userDTO : userDTOList) {
            ProfileDTO profileDTO = new ProfileDTO();
            profileDTO.setUserID(userDTO.getUserID());
            profileDTO.setFullName("Full Name " + userDTO.getUsername());
            profileDTO.setPhoto("profile_photo_" + userDTO.getUserID() + ".jpg");
            profileDTOList.add(profileService.createProfile(profileDTO));
        }

        return profileDTOList;
    }

    private void createChat(List<ProfileDTO> profileDTOList) {
        List<Long> participantIds = new ArrayList<>();
        for (ProfileDTO profileDTO : profileDTOList) {
            participantIds.add(profileDTO.getUserID());
        }
        Chat chat = chatService.createChat("Group Chat", participantIds);
    }
}
