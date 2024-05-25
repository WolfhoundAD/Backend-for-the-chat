package dev.chat.service;

import dev.chat.dto.ChatDto;
import dev.chat.entity.Chat;
import dev.chat.entity.User;
import dev.chat.handler.ChatNotFoundException;
import dev.chat.handler.UserNotFoundException;
import dev.chat.mapper.ChatMapper;
import dev.chat.repository.ChatRepository;
import dev.chat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatService {

    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final ChatMapper chatMapper;

    @Autowired
    public ChatService(ChatRepository chatRepository, UserRepository userRepository, ChatMapper chatMapper) {
        this.chatRepository = chatRepository;
        this.userRepository = userRepository;
        this.chatMapper = chatMapper;
    }

    public ChatDto createChat(String chatName, List<Long> participantIds) {
        List<User> participants = userRepository.findAllById(participantIds);
        Chat chat = Chat.builder()
                .chatName(chatName)
                .participants(participants)
                .build();
        Chat savedChat = chatRepository.save(chat);
        return chatMapper.chatToChatDTO(savedChat);
    }
    //todo добавить пагинацию
    public List<ChatDto> getAllChatsForUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));
        List<Chat> chats = chatRepository.findAllByParticipantsContaining(user);
        return chats.stream().map(chatMapper::chatToChatDTO).collect(Collectors.toList());
    }

    public ChatDto getChatById(Long chatId) {
        Chat chat = chatRepository.findById(chatId).orElseThrow(() -> new ChatNotFoundException("Chat not found with ID: " + chatId));
        return chatMapper.chatToChatDTO(chat);
    }

    public ChatDto renameChat(Long chatId, String newChatName) {
        Chat chat = chatRepository.findById(chatId).orElseThrow(() -> new ChatNotFoundException("Chat not found with ID: " + chatId));
        chat.setChatName(newChatName);
        Chat savedChat = chatRepository.save(chat);
        return chatMapper.chatToChatDTO(savedChat);
    }

    public void deleteChat(Long chatId) {
        if (!chatRepository.existsById(chatId)) {
            throw new ChatNotFoundException("Chat not found with ID: " + chatId);
        }
        chatRepository.deleteById(chatId);
    }
}