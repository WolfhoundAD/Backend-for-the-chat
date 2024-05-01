package dev.chat.service;

import dev.chat.entity.Chat;
import dev.chat.entity.Profile;
import dev.chat.entity.User;
import dev.chat.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Component
public class Initializer {

    private final ProfileService profileService;
    private final ChatService chatService;
    private final UserService userService;

    @Autowired
    public Initializer(ProfileService profileService, ChatService chatService, UserService userService) {
        this.profileService = profileService;
        this.chatService = chatService;
        this.userService = userService;
    }

    @Transactional //УЗНАТЬ ДЛЯ ЧЕГО
    public void initialize() {
        // Создаем несколько пользователей
        List<User> users = createUsers();

        // Создаем профили для пользователей
        List<Profile> profiles = createProfiles(users);

        // Создаем несколько чатов для каждого профиля
        createChats(profiles);
    }

    private List<User> createUsers() {
        List<User> users = new ArrayList<>();

        for (int i = 1; i <= 3; i++) {
            User user = new User();
            user.setUsername("user" + i);
            // user.setRole(STUDENT);
            user.setPassword("password" + i);
            users.add(userService.createUser(user));
        }

        return users;
    }

    private List<Profile> createProfiles(List<User> users) {
        List<Profile> profiles = new ArrayList<>();

        for (User user : users) {
            Profile profile = new Profile();
            profile.setFullName("Full Name " + user.getUsername());
            profile.setPhoto("Photo " + user.getUsername());
            profile.setUser(user);
            profiles.add(profileService.createProfile(profile));
        }

        return profiles;
    }

    private void createChats(List<Profile> profiles) {
        for (Profile profile : profiles) {
            for (int i = 1; i <= 2; i++) {
                Chat chat = new Chat();
                chat.setChatName("Chat " + i + " for " + profile.getFullName());
                chat.setParticipants(new ArrayList<>());
                chat.getParticipants().add(profile.getUser());

                // Создаем список идентификаторов пользователей
                List<Long> participantIds = new ArrayList<>();
                for (User participant : chat.getParticipants()) {
                    participantIds.add(participant.getId());
                }

                chatService.createChat(chat.getChatName(), participantIds);
            }
        }
    }
}
