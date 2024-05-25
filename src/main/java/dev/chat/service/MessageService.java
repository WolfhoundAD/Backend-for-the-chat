package dev.chat.service;

import dev.chat.dto.MessageDTO;
import dev.chat.entity.Chat;
import dev.chat.entity.Profile;
import dev.chat.mapper.MessageMapper;
import dev.chat.repository.MessageRepository;
import dev.chat.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MessageService {


    private final MessageRepository messageRepository;
    private final ProfileRepository profileRepository;
    private final MessageMapper messageMapper;

    @Autowired
    public MessageService(MessageRepository messageRepository, ProfileRepository profileRepository, MessageMapper messageMapper) {
        this.messageRepository = messageRepository;
        this.profileRepository = profileRepository;
        this.messageMapper = messageMapper;
    }

    public MessageDTO createMessage(MessageDTO messageDTO) {
        Optional<Profile> senderOptional = profileRepository.findById(messageDTO.getSenderID());
        if (!senderOptional.isPresent()) {
            //todo exception handler
            return null;
        }

        Chat chat = Chat.builder().chatId(messageDTO.getChatID()).build();
        dev.chat.entity.Message message = dev.chat.entity.Message.builder()
                .chat(chat)
                //todo поменяй для сохранения нужен только id
                .sender(senderOptional.get())
                .content(messageDTO.getContent())
                .timestamp(LocalDate.now())
                .build();

        dev.chat.entity.Message savedMessage = messageRepository.save(message);
        return messageMapper.messageToMessageDTO(savedMessage);
    }

    // Получить все сообщения для заданного чата
    public List<MessageDTO> getAllMessagesForChat(Long chatId) {
        List<dev.chat.entity.Message> messages = messageRepository.findAllByChat_ChatIdOrderByTimestamp(chatId);
        return messages.stream().map(messageMapper::messageToMessageDTO).collect(Collectors.toList());
    }
}
