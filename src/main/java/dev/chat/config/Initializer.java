package dev.chat.config;

import dev.chat.entity.Chat;
import dev.chat.entity.Profile;
import dev.chat.entity.User;
import dev.chat.enums.Role;
import dev.chat.repository.ChatRepository;
import dev.chat.repository.ProfileRepository;
import dev.chat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Component
public class Initializer {

    private final ProfileRepository profileRepository;
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;

    @Autowired
    public Initializer(ProfileRepository profileRepository, ChatRepository chatRepository, UserRepository userRepository) {
        this.profileRepository = profileRepository;
        this.chatRepository = chatRepository;
        this.userRepository = userRepository;
    }

    @Transactional
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
            users.add(userRepository.save(user));
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
            profiles.add(profileRepository.save(profile));
        }

        return profiles;
    }

    private void createChats(List<Profile> profiles) {
        for (Profile profile : profiles) {
            for (int i = 1; i <= 2; i++) {
                Chat chat = new Chat();
                chat.setChatName("Chat " + i + " for " + profile.getFullName());

                // Инициализируем список участников
                chat.setParticipants(new ArrayList<>());

                // Добавляем участника (профиль) в чат
                chat.getParticipants().add(profile.getUser());

                chatRepository.save(chat);
            }
        }
    }
}
