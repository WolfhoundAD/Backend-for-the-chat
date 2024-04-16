package dev.chat.service;

import dev.chat.entity.Chat;
import dev.chat.entity.ChatParticipant;
import dev.chat.entity.Profile;
import dev.chat.repository.ChatParticipantRepository;
import dev.chat.repository.ChatRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChatService {
    private final ChatRepository chatRepository;
    private final ProfileService profileService;
    private final ChatParticipantRepository chatParticipantRepository;

    public ChatService(ChatRepository chatRepository, ProfileService profileService, ChatParticipantRepository chatParticipantRepository) {
        this.chatRepository = chatRepository;
        this.profileService = profileService;
        this.chatParticipantRepository = chatParticipantRepository;
    }

    public Chat createChat(String chatName, List<Long> participantIds) {
        Chat chat = new Chat();
        chat.setChatName(chatName);
        Chat savedChat = chatRepository.save(chat);

        // Создание записей ChatParticipant для каждого участника чата
        for (Long profileId : participantIds) {
            Profile participant = profileService.getProfileById(profileId);
            ChatParticipant chatParticipant = new ChatParticipant();
            chatParticipant.setChatId(savedChat);
            chatParticipant.setProfileId(participant);
            chatParticipantRepository.save(chatParticipant);
        }

        return savedChat;
    }

    public List<Chat> getAllChatsForUser(Long userId) {
        List<Chat> chats = new ArrayList<>();
        List<Profile> profiles = profileService.getAllProfilesForUser(userId);
       // for (Profile profile : profiles) {
         //   List<Chat> userChats = chatRepository.findByParticipants(profile);
           // chats.addAll(userChats);
       // }
        return chats;
    }

    public void deleteChat(Long chatId) {
        chatRepository.deleteById(chatId);
    }
}
