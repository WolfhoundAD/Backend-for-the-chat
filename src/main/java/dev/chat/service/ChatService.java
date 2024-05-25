package dev.chat.service;

import dev.chat.dto.ChatDto;
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
import java.util.stream.Collectors;

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
    public ChatDto createChat(String chatName, List<Long> participantIds) {
        List<User> participants = userRepository.findAllById(participantIds);
        Chat chat = Chat.builder()
                .chatName(chatName)
                .participants(participants)
                .build();
        Chat savedChat = chatRepository.save(chat);
        return convertToChatDto(savedChat);
    }

    // Получить все чаты для пользователя
    //todo add pagination
    public List<ChatDto> getAllChatsForUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + userId + " not found"));
        List<Chat> chats = chatRepository.findAllByParticipantsContains(user);
        return chats.stream().map(this::convertToChatDto).collect(Collectors.toList());
    }

    // Переименовать чат
    public ChatDto renameChat(Long chatId, String newChatName) {
        //add controller advice , handle
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new ChatNotFoundException("Chat with ID " + chatId + " not found"));
        chat.setChatName(newChatName);
        Chat savedChat = chatRepository.save(chat);
        return convertToChatDto(savedChat);
    }

    // Удалить чат
    public void deleteChat(Long chatId) {
        chatRepository.deleteById(chatId);
    }

    // Преобразовать сущность Chat в DTO
    private ChatDto convertToChatDto(Chat chat) {
        //todo mapstruct
        ChatDto chatDto = new ChatDto();
        chatDto.setChatID(chat.getChatId());
        chatDto.setChatName(chat.getChatName());
        return chatDto;
    }
}
