package dev.chat.service;

import dev.chat.dto.MessageDTO;
import dev.chat.entity.Message;
import dev.chat.entity.Profile;
import dev.chat.mapper.MessageMapper;
import dev.chat.repository.MessageRepository;
import dev.chat.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            throw new RuntimeException("Sender not found");
        }

        Message message = messageMapper.messageDTOToMessage(messageDTO);
        message.setSender(senderOptional.get());

        Message savedMessage = messageRepository.save(message);
        return messageMapper.messageToMessageDTO(savedMessage);
    }

    public List<MessageDTO> getAllMessagesForChat(Long chatId) {
        List<Message> messages = messageRepository.findMessagesByChatId(chatId);
        return messages.stream().map(messageMapper::messageToMessageDTO).collect(Collectors.toList());
    }
}
