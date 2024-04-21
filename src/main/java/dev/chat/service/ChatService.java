package dev.chat.service;

import dev.chat.entity.Chat;
import dev.chat.entity.User;
import dev.chat.repository.ChatRepository;
import dev.chat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChatService {

    private final ChatRepository chatRepository;
    private final UserRepository userRepository;

    @Autowired
    public ChatService(ChatRepository chatRepository, UserRepository userRepository) {
        this.chatRepository = chatRepository;
        this.userRepository = userRepository;
    }

    // Создать чат
    public Chat createChat(String chatName, List<Long> participantIds) {
        List<User> participants = userRepository.findAllById(participantIds);
        Chat chat = Chat.builder()
                .chatName(chatName)
                .participants(participants)
                .build();
        return chatRepository.save(chat);
    }

    // Получить все чаты для пользователя
    public List<Chat> getAllChatsForUser(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            return chatRepository.findAllByParticipantsContains(userOptional.get());
        }
        return null;
    }

    // Переименовать чат
    public Chat renameChat(Long chatId, String newChatName) {
        Optional<Chat> chatOptional = chatRepository.findById(chatId);
        if (chatOptional.isPresent()) {
            Chat chat = chatOptional.get();
            chat.setChatName(newChatName);
            return chatRepository.save(chat);
        }
        return null;
    }

    // Удалить чат
    public void deleteChat(Long chatId) {
        chatRepository.deleteById(chatId);
    }
}
