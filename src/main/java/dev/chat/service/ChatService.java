package dev.chat.service;

import dev.chat.entity.Chat;
import dev.chat.entity.User;
import dev.chat.handler.ChatNotFoundException;
import dev.chat.handler.UserNotFoundException;
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
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + userId + " not found"));
        return chatRepository.findAllByParticipantsContains(user);
    }

    // Переименовать чат
    public Chat renameChat(Long chatId, String newChatName) {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new ChatNotFoundException("Chat with ID " + chatId + " not found"));
        chat.setChatName(newChatName);
        return chatRepository.save(chat);
    }

    // Удалить чат
    public void deleteChat(Long chatId) {
        chatRepository.deleteById(chatId);
    }
}
