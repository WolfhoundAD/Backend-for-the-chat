package dev.chat.service;

import dev.chat.entity.User;
import dev.chat.repository.ChatRepository;
import dev.chat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        // Дополнительные проверки, логика и т.д. могут быть добавлены здесь
        return userRepository.save(user);
    }

}
